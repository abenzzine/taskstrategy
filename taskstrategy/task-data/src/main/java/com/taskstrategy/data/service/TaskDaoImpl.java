package com.taskstrategy.data.service;

import com.taskstrategy.commons.domain.*;
import com.taskstrategy.data.api.TaskDao;
import com.taskstrategy.data.api.TaskReminderDao;
import com.taskstrategy.data.api.TaskTagDao;
import com.taskstrategy.data.api.exception.DataIntegrityException;
import com.taskstrategy.data.api.exception.TaskUpdateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * The TaskDaoImpl class provides a convenient local implementation
 * of the TaskDao
 */
@Repository
@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = {Exception.class, RuntimeException.class})
public class TaskDaoImpl implements TaskDao {


    private static final Logger LOGGER = LoggerFactory.getLogger(TaskDaoImpl.class);
    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;
    @Autowired
    private TaskTagDao taskTagDao;
    @Autowired
    private TaskReminderDao taskReminderDao;

    private static final String GET_TASK_BY_ID = "SELECT t.*, p.id as 'priorityId', p.name as 'priority' FROM Task t, TaskPriority p where t.priorityId = p.id and t.id = :id order by dueDate, name";
    private static final String GET_TASKS_BY_USER = "SELECT t.*, p.id as 'priorityId', p.name as 'priority' FROM Task t, TaskPriority p where t.priorityId = p.id and t.complete = 0 and t.userId = :userId order by dueDate, name";
    private static final String GET_TASKS_BY_USER_DUE_TODAY = "SELECT t.*, p.id as 'priorityId', p.name as 'priority' FROM Task t, TaskPriority p where t.priorityId = p.id and t.complete = 0 and t.userId = :userId AND (DATE(NOW()) = DATE(t.dueDate) OR DATE(t.dueDate) < DATE(NOW())) order by dueDate, name";
    private static final String GET_TASKS_BY_USER_DUE_THIS_WEEK = "SELECT t.*, p.id as 'priorityId', p.name as 'priority' FROM Task t, TaskPriority p where t.priorityId = p.id and t.complete = 0 and t.userId = :userId AND (WeekOfYear(Now()) = WeekOfYear(dueDate) OR DATE(t.dueDate) < DATE(NOW())) order by dueDate, name";
    private static final String GET_OPEN_TASKS_BY_USER = "SELECT t.*, p.id as 'priorityId', p.name as 'priority' FROM Task t, TaskPriority p where t.priorityId = p.id  and t.userId = :userId and t.complete = 0 order by createDate";
    private static final String GET_TASKS_BY_USER_AND_TAG = "SELECT t.*, p.id as 'priorityId', p.name as 'priority' FROM Task t, TaskPriority p, TaskTag tt where t.priorityId = p.id  and tt.taskId = t.id and tt.name = :tagName and t.userId = :userId and t.complete != '1' order by dueDate, name";
    private static final String DELETE_TASK = "DELETE FROM Task where id = :id";
    private static final String INSERT_TASK = "INSERT INTO Task(id,userId, parentTaskId, name, description, priorityId, dueDate, createDate,lastModifiedDate) " +
            "VALUES(:id, :userId, :parentTaskId, :name, :description, :priorityId, :dueDate, NOW(), NOW())";
    private static final String UPDATE_TASK = "UPDATE Task Set name = :name, description = :description, priorityId = :priorityId, passCount = :passCount, dueDate = :dueDate, lastModifiedDate = NOW() " +
            "WHERE id = :id";
    private static final String TASK_COMPLETED = "UPDATE Task Set complete = true , lastModifiedDate = NOW() where id = :id ";
    private static final String TASK_UNCOMPLETED = "UPDATE Task Set complete = false , LastModifiedDate = NOW() where id = :id";
    private static final String GET_PRIORITIES = "SELECT * FROM TaskPriority";
    private static final String GET_FREQUENCIES = "SELECT * FROM TaskNotification";
    private static final String DELETE_TASKS = "DELETE FROM Task where userId = :userId";
    private static final String GET_TASKS_BY_USER_DUE_THIS_MONTH = "SELECT t.*, p.id as 'priorityId', p.name as 'priority' FROM Task t, TaskPriority p where t.priorityId = p.id and t.complete = 0 and t.userId = :userId and (Month(NOW()) = Month(dueDate) OR DATE(t.dueDate) < DATE(NOW())) order by dueDate , t.name";
    private static final String GET_DONE_TASKS_BY_USER = "SELECT t.*, p.id as 'priorityId', p.name as 'priority' FROM Task t, TaskPriority p where t.complete = '1' and t.priorityId = p.id and t.userId = :userId order by dueDate, name";
    private static final String GET_PAST_DUE_TASKS = "SELECT t.*, p.id as 'priorityId', p.name as 'priority' FROM Task t, TaskPriority p where t.priorityId = p.id and t.complete = 0 and t.userId = :userId and DATE(t.dueDate) < DATE(NOW()) order by dueDate , t.name";
    private static final String GET_TASKS_WITH_REMINDERS = "SELECT DISTINCT t.*, p.id as 'priorityId', p.name as 'priority' FROM Task t, TaskPriority p, TaskReminder r where t.priorityId = p.id and t.id = r.taskId and t.complete = 0 and DATE(t.dueDate) > DATE(NOW())";

    /**
     * Creates a new service.
     *
     * @param task - the service to create
     * @return the created service
     */
    public Task createTask(Task task) throws DataIntegrityException, TaskUpdateException {
        if (task != null) {
            try {
                Map<String, Object> params = new HashMap<>();
                params.put("id", task.getId());
                params.put("userId", task.getUserId());
                params.put("parentTaskId", task.getParentTaskId());
                params.put("name", task.getName());
                params.put("description", task.getDescription());
                params.put("priorityId", task.getPriority().getId());
                params.put("passCount", task.getPassCount());
                params.put("dueDate", task.getDueDate());
                int count = jdbcTemplate.update(INSERT_TASK, params);
                if (count != 1) {
                    LOGGER.error("Failed to save the task, no rows created.");
                    throw new TaskUpdateException("Save failed");
                }
                List<Tag> taskTags = new ArrayList<>();
                for (Tag taskTag : task.getTaskTags()) {
                    taskTags.add(taskTagDao.createTag(task.getId(), taskTag));
                }
                Task insertedTask = getTaskById(task.getId());
                insertedTask.setTaskTags(taskTags);

                taskReminderDao.deleteReminders(task.getId());
                for (TaskReminder taskReminder : task.getReminders()) {
                    taskReminderDao.createReminder(task.getId(), taskReminder);
                }
                insertedTask.setReminders(task.getReminders());
                return insertedTask;
            } catch (DataIntegrityViolationException ex) {
                LOGGER.error("Unable to create task, there is an issue saving the data", ex);
                String messageReason = "unknown, please contact development";
                if (ex.getMessage().contains("FK_Task_Priority")) {
                    messageReason = "missing Priority";
                } else if (ex.getMessage().contains("FK_User_Id")) {
                    messageReason = "missing User";
                }
                throw new DataIntegrityException(messageReason);
            }
        } else {
            LOGGER.info("No task details were provided for creation.");
            throw new TaskUpdateException("Task details missing");
        }
    }

    /**
     * Updates an existing service.
     *
     * @param task - the service to update
     * @return the updated service
     */
    public Task updateTask(Task task) throws DataIntegrityException, TaskUpdateException {
        if (task != null) {
            try {
                Map<String, Object> params = new HashMap<>();
                params.put("id", task.getId());
                params.put("name", task.getName());
                params.put("description", task.getDescription());
                params.put("priorityId", task.getPriority().getId());
                params.put("passCount", task.getPassCount());
                params.put("dueDate", task.getDueDate());


                int count = jdbcTemplate.update(UPDATE_TASK, params);
                if (count != 1) {
                    LOGGER.error("Failed to update the task, no rows created.");
                    throw new TaskUpdateException("Save failed");
                }
                LOGGER.info("Deleting tags for task Id = " + task.getId());
                taskTagDao.deleteTags(task.getId());
                List<Tag> taskTags = new ArrayList<>();
                for (Tag taskTag : task.getTaskTags()) {
                    taskTags.add(taskTagDao.createTag(task.getId(), taskTag));
                }
                Task updateTask = getTaskById(task.getId());
                updateTask.setTaskTags(taskTags);
                taskReminderDao.deleteReminders(task.getId());
                for (TaskReminder taskReminder : task.getReminders()) {
                    taskReminderDao.createReminder(task.getId(), taskReminder);
                }
                updateTask.setReminders(task.getReminders());
                return updateTask;
            } catch (DataIntegrityViolationException ex) {
                LOGGER.error("Failed to update the task, there was an issue with the data.", ex);
                String messageReason = "unknown, please contact development";
                if (ex.getMessage().contains("FK_Task_Priority")) {
                    messageReason = "missing Priority";
                }
                throw new DataIntegrityException(messageReason);
            }
        } else {
            LOGGER.info("No task details were provided for update.");
            throw new TaskUpdateException("Task details missing");
        }
    }

    /**
     * Deletes a service.
     *
     * @param taskId - the service to delete
     */
    public Task deleteTask(String taskId) throws TaskUpdateException {
        Task task = getTaskById(taskId);
        Map<String, Object> params = new HashMap<>();
        params.put("id", taskId);
        LOGGER.info("Deleting tags for task Id = " + taskId);
        taskTagDao.deleteTags(taskId);
        taskReminderDao.deleteReminders(taskId);
        int count = jdbcTemplate.update(DELETE_TASK, params);
        if (count != 1) {
            LOGGER.error("Failed to delete the task, no task completed.");
            throw new TaskUpdateException("Unable to delete task");
        }
        return task;
    }

    /**
     * Mark's task completed
     *
     * @param taskId - the service to delete
     */
    public Task markTaskCompleted(String taskId) throws TaskUpdateException {
        Task task = getTaskById(taskId);
        Map<String, Object> params = new HashMap<>();
        params.put("id", taskId);
        task.setCompleted(true);
        LOGGER.info("Completing tags for task Id = " + taskId);
        int count = jdbcTemplate.update(TASK_COMPLETED, params);
        if (count != 1) {
            LOGGER.error("Failed to complete the task, no rows deleted.");
            throw new TaskUpdateException("Unable to complete task");
        }
        return task;
    }

    public Task markTaskUnCompleted(String taskId) throws TaskUpdateException {
        Task task = getTaskById(taskId);
        if (task != null) {
            Map<String, Object> params = new HashMap<>();
            params.put("id", taskId);
            task.setCompleted(false);
            LOGGER.info("UnCompleting tags for task Id = " + taskId);
            int count = jdbcTemplate.update(TASK_UNCOMPLETED, params);
            if (count != 1) {
                LOGGER.error("Failed to complete the task, no tasks uncompleted.");
                throw new TaskUpdateException("Unable to complete task");
            }
        }
        return task;
    }

    @Override
    public List<Task> getTasks(String userId) {
        Map<String, Object> params = new HashMap<>();
        params.put("userId", userId);
        List<Task> tasks = jdbcTemplate.query(GET_TASKS_BY_USER, params, new TaskRowMapper());
        for (Task task : tasks) {
            task.setTaskTags(taskTagDao.getTags(task.getId()));
            task.setReminders(taskReminderDao.getReminders(task.getId()));
        }
        Collections.sort(tasks, new Task());
        return tasks;
    }

    @Override
    public List<Task> getTasksWithReminders() {
        List<Task> tasks = jdbcTemplate.query(GET_TASKS_WITH_REMINDERS, new TaskRowMapper());
        for (Task task : tasks) {
            task.setReminders(taskReminderDao.getReminders(task.getId()));
        }
        return tasks;
    }


    @Override
    public List<Task> getTasksDueToday(String currentUserId) {
        Map<String, Object> params = new HashMap<>();
        params.put("userId", currentUserId);
        List<Task> tasks = jdbcTemplate.query(GET_TASKS_BY_USER_DUE_TODAY, params, new TaskRowMapper());
        for (Task task : tasks) {
            task.setTaskTags(taskTagDao.getTags(task.getId()));
            task.setReminders(taskReminderDao.getReminders(task.getId()));
        }
        Collections.sort(tasks, new Task());
        return tasks;
    }

    @Override
    public List<Task> getTasksDueThisWeek(String currentUserId) {
        Map<String, Object> params = new HashMap<>();
        params.put("userId", currentUserId);
        List<Task> tasks = jdbcTemplate.query(GET_TASKS_BY_USER_DUE_THIS_WEEK, params, new TaskRowMapper());
        for (Task task : tasks) {
            task.setTaskTags(taskTagDao.getTags(task.getId()));
            task.setReminders(taskReminderDao.getReminders(task.getId()));
        }
        Collections.sort(tasks, new Task());
        return tasks;
    }

    @Override
    public List<Task> getTasksDueThisMonth(String userId) {
        Map<String, Object> params = new HashMap<>();
        params.put("userId", userId);
        List<Task> tasks = jdbcTemplate.query(GET_TASKS_BY_USER_DUE_THIS_MONTH, params, new TaskRowMapper());
        for (Task task : tasks) {
            task.setTaskTags(taskTagDao.getTags(task.getId()));
            task.setReminders(taskReminderDao.getReminders(task.getId()));
        }
        Collections.sort(tasks, new Task());
        return tasks;
    }

    @Override
    public List<Task> getPastDueTasks(String currentUserId) {
        Map<String, Object> params = new HashMap<>();
        params.put("userId", currentUserId);
        List<Task> tasks = jdbcTemplate.query(GET_PAST_DUE_TASKS, params, new TaskRowMapper());
        for (Task task : tasks) {
            task.setTaskTags(taskTagDao.getTags(task.getId()));
            task.setReminders(taskReminderDao.getReminders(task.getId()));
        }
        Collections.sort(tasks, new Task());
        return tasks;
    }

    @Override
    public List<Task> getTaskSet(String userId, int noOfTasksInSet) {
        Map<String, Object> params = new HashMap<>();
        params.put("userId", userId);
        List<Task> tasks = jdbcTemplate.query(GET_OPEN_TASKS_BY_USER, params, new TaskRowMapper());
        for (Task task : tasks) {
            task.setTaskTags(taskTagDao.getTags(task.getId()));
            task.setReminders(taskReminderDao.getReminders(task.getId()));
        }
        Collections.shuffle(tasks);
        int noOfTasksToInclude = tasks.size() > noOfTasksInSet ? noOfTasksInSet : tasks.size();
        List<Task> tasksToInclude = tasks.subList(0, noOfTasksToInclude);
        return tasksToInclude;
    }

    @Override
    public Task getTaskById(String id) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        Task task = jdbcTemplate.query(GET_TASK_BY_ID, params, new TaskResultSetExtractor());
        if (task != null) {
            task.setTaskTags(taskTagDao.getTags(task.getId()));
            task.setReminders(taskReminderDao.getReminders(task.getId()));
        }
        return task;
    }

    @Override
    public List<Task> getTasksByTag(String userId, String tag) {
        Map<String, Object> params = new HashMap<>();
        params.put("userId", userId);
        params.put("tagName", tag);
        List<Task> tasks = jdbcTemplate.query(GET_TASKS_BY_USER_AND_TAG, params, new TaskRowMapper());

        for (Task task : tasks) {
            task.setTaskTags(taskTagDao.getTags(task.getId()));
            task.setReminders(taskReminderDao.getReminders(task.getId()));
        }
        Collections.sort(tasks, new Task());
        return tasks;

    }

    @Override
    public List<Task> getDoneTasksByUser(String userId) {
        Map<String, Object> params = new HashMap<>();
        params.put("userId", userId);

        List<Task> done_tasks = jdbcTemplate.query(GET_DONE_TASKS_BY_USER, params, new TaskRowMapper());

        for (Task task : done_tasks) {
            task.setTaskTags(taskTagDao.getTags(task.getId()));
            task.setReminders(taskReminderDao.getReminders(task.getId()));
        }
        Collections.sort(done_tasks, new Task());
        return done_tasks;

    }

    @Override
    public void deleteTasks(String userId) {
        Map<String, Object> params = new HashMap<>();
        params.put("userId", userId);
        jdbcTemplate.update(DELETE_TASKS, params);
    }

    public List<TaskPriority> getPriorities() {
        return jdbcTemplate.query(GET_PRIORITIES, new TaskPriorityRowMappper());
    }

    public List<TaskNotification> getFrequencies() {
        return jdbcTemplate.query(GET_FREQUENCIES, new TaskNotificationRowMappper());
    }

    protected static final class TaskResultSetExtractor implements ResultSetExtractor<Task> {

        @Override
        public Task extractData(ResultSet resultSet) throws SQLException, DataAccessException {
            Task task = null;
            if (resultSet.next()) {
                task = new Task();
                task.setId(resultSet.getString("id"));
                task.setName(resultSet.getString("name"));
                task.setDescription(resultSet.getString("description"));
                task.setUserId(resultSet.getString("userId"));
                task.setParentTaskId(resultSet.getString("parentTaskId"));
                task.setCompleted(resultSet.getBoolean("complete"));
                task.setDueDate(resultSet.getDate("dueDate"));
                task.setPassCount(resultSet.getInt("passCount"));
                task.setCreateDate(resultSet.getTimestamp("createDate"));
                task.setLastModifiedDate(resultSet.getTimestamp("lastModifiedDate"));
                String priorityId = resultSet.getString("priorityId");
                task.setPriority(TaskPriority.getTaskPriority(priorityId));
            }
            return task;
        }
    }

    protected static final class TaskRowMapper implements RowMapper<Task> {
        @Override
        public Task mapRow(ResultSet resultSet, int i) throws SQLException {
            Task task = new Task();
            task.setId(resultSet.getString("id"));
            task.setName(resultSet.getString("name"));
            task.setDescription(resultSet.getString("description"));
            task.setUserId(resultSet.getString("userId"));
            task.setParentTaskId(resultSet.getString("parentTaskId"));
            task.setCompleted(resultSet.getBoolean("complete"));
            task.setPassCount(resultSet.getInt("passCount"));
            task.setDueDate(resultSet.getDate("dueDate"));
            task.setCreateDate(resultSet.getTimestamp("createDate"));
            task.setLastModifiedDate(resultSet.getTimestamp("lastModifiedDate"));
            String priorityId = resultSet.getString("priorityId");
            task.setPriority(TaskPriority.getTaskPriority(priorityId));
            return task;
        }
    }

    protected static final class TaskPriorityRowMappper implements RowMapper<TaskPriority> {
        @Override
        public TaskPriority mapRow(ResultSet resultSet, int i) throws SQLException {
            String priorityId = resultSet.getString("id");
            TaskPriority priority = TaskPriority.getTaskPriority(priorityId);
            return priority;
        }
    }

    protected static final class TaskNotificationRowMappper implements RowMapper<TaskNotification> {
        @Override
        public TaskNotification mapRow(ResultSet resultSet, int i) throws SQLException {
            String notificationId = resultSet.getString("id");
            TaskNotification frequency = TaskNotification.getTaskNotification(notificationId);
            return frequency;
        }
    }

    public void setJdbcTemplate(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void setTaskTagDao(TaskTagDao taskTagDao) {
        this.taskTagDao = taskTagDao;
    }

    public void setTaskReminderDao(TaskReminderDao taskReminderDao) {
        this.taskReminderDao = taskReminderDao;
    }
}
