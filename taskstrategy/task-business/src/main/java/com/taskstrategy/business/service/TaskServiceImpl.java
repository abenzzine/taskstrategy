package com.taskstrategy.business.service;

import com.taskstrategy.business.api.TaskService;
import com.taskstrategy.business.api.exception.TaskServiceException;
import com.taskstrategy.business.api.exception.TaskServiceValidationException;
import com.taskstrategy.commons.domain.*;
import com.taskstrategy.data.api.ChartDao;
import com.taskstrategy.data.api.TaskDao;
import com.taskstrategy.data.api.exception.DataIntegrityException;
import com.taskstrategy.data.api.exception.TaskUpdateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.validation.groups.Default;
import java.util.List;
import java.util.Set;

/**
 * The TaskServiceImpl class provides a convenient local implementation
 * of the TaskService
 */
@Service
public class TaskServiceImpl implements TaskService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TaskServiceImpl.class);
    @Autowired
    private TaskDao taskDao;
    @Autowired
    private ChartDao chartDao;
    @Autowired
    private Validator validator;

    private <T extends BaseDomain> void checkForValidationViolations(T itemToCheck) throws TaskServiceValidationException {
        Set<ConstraintViolation<T>> violationSet = validator.validate(itemToCheck, Default.class);
        if (!violationSet.isEmpty()) {
            throw new TaskServiceValidationException(violationSet);
        }
    }

    /**
     * Creates a new service.
     *
     * @param task - the service to create
     * @return the created service
     */
    public Task createTask(Task task) throws TaskServiceException, TaskServiceValidationException {
        try {
            checkForValidationViolations(task);
            return taskDao.createTask(task);
        } catch (DataIntegrityException e) {
            LOGGER.error("Error processing the create task business function.", e);
            throw new TaskServiceException(e.getMessage());
        } catch (TaskUpdateException e) {
            LOGGER.error("Error processing the create task business function.", e);
            throw new TaskServiceException(e.getMessage());
        }
    }

    @Override
    public void createQuickTask(Task simpleTask) throws TaskServiceException {
        try {
            taskDao.createTask(simpleTask);
        } catch (DataIntegrityException e) {
            LOGGER.error("Error processing the create task business function.", e);
            throw new TaskServiceException(e.getMessage());
        } catch (TaskUpdateException e) {
            LOGGER.error("Error processing the create task business function.", e);
            throw new TaskServiceException(e.getMessage());
        }
    }

    public void createSubtasks(String taskId, List<Task> subtasks) throws DataIntegrityException, TaskUpdateException, TaskServiceException, TaskServiceValidationException {
        Task parentTask = taskDao.getTaskById(taskId);

        for (Task task : subtasks) {
            task.setParentTaskId(taskId);
            task.setUserId(parentTask.getUserId());
            task.setPriority(parentTask.getPriority());

            try {
                taskDao.createTask(task);
            } catch (Exception e) {
                LOGGER.error("Error creating a subtask.", e);
                e.printStackTrace();
                throw new TaskServiceException(e.getMessage());
            }
        }
    }


    /**
     * Updates an existing service.
     *
     * @param task - the service to update
     * @return the updated service
     */
    public Task updateTask(Task task) throws TaskServiceException, TaskServiceValidationException {
        try {
            checkForValidationViolations(task);
            return taskDao.updateTask(task);
        } catch (DataIntegrityException e) {
            LOGGER.error("Error processing the create task business function.", e);
            throw new TaskServiceException(e.getMessage());
        } catch (TaskUpdateException e) {
            LOGGER.error("Error processing the create task business function.", e);
            throw new TaskServiceException(e.getMessage());
        }
    }

    /**
     * Deletes a service.
     *
     * @param taskId - the service to delete
     */
    public Task deleteTask(String taskId) throws TaskServiceException {
        try {
            return taskDao.deleteTask(taskId);
        } catch (TaskUpdateException e) {
            LOGGER.error("Error processing the create task business function.", e);
            throw new TaskServiceException(e.getMessage());
        }
    }

    /**
     * Deletes a service.
     *
     * @param taskId - the service to delete
     */
    public void completeTask(String taskId) throws TaskServiceException {
        try {
            taskDao.markTaskCompleted(taskId);
        } catch (TaskUpdateException e) {
            LOGGER.error("Error processing the complete task business function.", e);
            throw new TaskServiceException(e.getMessage());
        }
    }

    public void unCompleteTask(String taskId) throws TaskServiceException {
        try {
            taskDao.markTaskUnCompleted(taskId);
        } catch (TaskUpdateException e) {
            LOGGER.error("Error processing the uncomplete task business function.", e);
            throw new TaskServiceException(e.getMessage());
        }
    }

    @Override
    public List<TaskNotification> getFrequencies() {
        return taskDao.getFrequencies();
    }

    @Override
    public List<TaskPriority> getPriorities() {
        return taskDao.getPriorities();
    }

    @Override
    public List<Task> getTasks(String userId) {
        return taskDao.getTasks(userId);
    }


    @Override
    public ChartTaskData getTaskChartCounts(String userId) {
        return chartDao.getTaskChartInfo(userId);
    }


    @Override
    public Task getTask(String taskId) {
        return taskDao.getTaskById(taskId);
    }

    @Override
    public void deleteTasks(String userId) {
        taskDao.deleteTasks(userId);
    }

    @Override
    public List<Task> getTasksByTag(String currentUserId, String tag) {
        return taskDao.getTasksByTag(currentUserId, tag);
    }

    @Override
    public List<Task> getDoneTasksByUser(String currentUserId) {
        return taskDao.getDoneTasksByUser(currentUserId);
    }

    @Override
    public List<Task> getTasksDueToday(String currentUserId) {
        return taskDao.getTasksDueToday(currentUserId);
    }

    @Override
    public List<Task> getTasksDueThisWeek(String currentUserId) {
        return taskDao.getTasksDueThisWeek(currentUserId);
    }

    @Override
    public List<Task> getTasksDueThisMonth(String currentUserId) {
        return taskDao.getTasksDueThisMonth(currentUserId);
    }

    @Override
    public List<Task> getTaskSet(String id, int noOfTasksInSet) {
        return taskDao.getTaskSet(id, noOfTasksInSet);
    }

    @Override
    public List<Task> getPastDueTasks(String currentUserId) {
        return taskDao.getPastDueTasks(currentUserId);
    }
}
