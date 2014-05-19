package com.taskstrategy.data.service;

import com.taskstrategy.commons.domain.Tag;
import com.taskstrategy.commons.domain.Task;
import com.taskstrategy.commons.domain.TaskPriority;
import com.taskstrategy.commons.domain.TaskSearchCriteria;
import com.taskstrategy.data.api.exception.DataIntegrityException;
import com.taskstrategy.data.api.exception.TaskUpdateException;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created with IntelliJ IDEA.
 * User: brian
 * Date: 10/14/13
 * Time: 8:43 PM
 * This class is responsible for testing the TaskDao
 */

public class TaskDaoTest {

    @Test
    public void testCreate() throws DataIntegrityException, TaskUpdateException {
        Task task = new Task();
        task.setName("MyTestTask");
        task.setCompleted(true);
        task.setDescription("Desc1");
        task.setDueDate(Calendar.getInstance().getTime());
        TaskPriority priority = TaskPriority.HIGH;
        task.setPriority(priority);
        List<Tag> taskTagList = new ArrayList<>();
        task.setTaskTags(taskTagList);

        NamedParameterJdbcTemplate template = Mockito.mock(NamedParameterJdbcTemplate.class);
        Mockito.when(template.update(Mockito.anyString(), Mockito.anyMap())).thenReturn(1);
        Mockito.when(template.query(Mockito.anyString(), Mockito.anyMap(), Mockito.any(ResultSetExtractor.class))).thenReturn(task);
        TaskDaoImpl taskDao = new TaskDaoImpl();

        NamedParameterJdbcTemplate templateTag = Mockito.mock(NamedParameterJdbcTemplate.class);
        TaskTagDaoImpl taskTagDao = new TaskTagDaoImpl();
        taskTagDao.setJdbcTemplate(templateTag);
        taskDao.setJdbcTemplate(template);
        taskDao.setTaskTagDao(taskTagDao);
        Task createdTask = taskDao.createTask(task);
        assertEquals("MyTestTask", createdTask.getName());
        assertEquals("Desc1", createdTask.getDescription());
        assertEquals("HIGH", createdTask.getPriority().getDescription());
        assertEquals(true, createdTask.isCompleted());
        assertEquals(0, createdTask.getTaskTags().size());
    }

    @Test
    public void testCreateNull() throws DataIntegrityException, TaskUpdateException {
        Task task = new Task();
        task.setName("MyTestTask");
        task.setDescription("Desc1");
        task.setDueDate(Calendar.getInstance().getTime());
        TaskPriority priority = TaskPriority.HIGH;
        task.setPriority(priority);
        List<Tag> taskTagList = new ArrayList<>();
        task.setTaskTags(taskTagList);

        NamedParameterJdbcTemplate template = Mockito.mock(NamedParameterJdbcTemplate.class);
        Mockito.when(template.update(Mockito.anyString(), Mockito.anyMap())).thenReturn(1);
        Mockito.when(template.query(Mockito.anyString(), Mockito.anyMap(), Mockito.any(ResultSetExtractor.class))).thenReturn(task);
        TaskDaoImpl taskDao = new TaskDaoImpl();

        NamedParameterJdbcTemplate templateTag = Mockito.mock(NamedParameterJdbcTemplate.class);
        TaskTagDaoImpl taskTagDao = new TaskTagDaoImpl();
        taskTagDao.setJdbcTemplate(templateTag);
        taskDao.setJdbcTemplate(template);
        taskDao.setTaskTagDao(taskTagDao);
        try {
            taskDao.createTask(null);
            fail();
        } catch (TaskUpdateException ex) {
            assertEquals("Unable to save the task due to an error. Reason: Task details missing.", ex.getMessage());
        } catch (Throwable t) {
            fail();
        }
    }

    @Test
    public void testCreateCountException() throws DataIntegrityException, TaskUpdateException {
        Task task = new Task();
        task.setName("MyTestTask");
        task.setDescription("Desc1");
        task.setDueDate(Calendar.getInstance().getTime());
        TaskPriority priority = TaskPriority.HIGH;
        task.setPriority(priority);
        List<Tag> taskTagList = new ArrayList<>();
        task.setTaskTags(taskTagList);

        NamedParameterJdbcTemplate template = Mockito.mock(NamedParameterJdbcTemplate.class);
        Mockito.when(template.update(Mockito.anyString(), Mockito.anyMap())).thenReturn(0);
        Mockito.when(template.query(Mockito.anyString(), Mockito.anyMap(), Mockito.any(ResultSetExtractor.class))).thenReturn(task);
        TaskDaoImpl taskDao = new TaskDaoImpl();

        NamedParameterJdbcTemplate templateTag = Mockito.mock(NamedParameterJdbcTemplate.class);
        TaskTagDaoImpl taskTagDao = new TaskTagDaoImpl();
        taskTagDao.setJdbcTemplate(templateTag);
        taskDao.setJdbcTemplate(template);
        taskDao.setTaskTagDao(taskTagDao);
        try {
            taskDao.createTask(task);
            fail();
        } catch (TaskUpdateException ex) {
            assertEquals("Unable to save the task due to an error. Reason: Save failed.", ex.getMessage());
        } catch (Throwable throwable) {
            fail();
        }

    }

    @Test
    public void testCreateWithTag() throws DataIntegrityException, TaskUpdateException {
        Task task = new Task();
        task.setName("MyTestTask");
        task.setDescription("Desc1");
        task.setDueDate(Calendar.getInstance().getTime());
        TaskPriority priority = TaskPriority.HIGH;
        task.setPriority(priority);
        List<Tag> taskTagList = new ArrayList<>();
        Tag taskTag = new Tag();
        taskTag.setName("Tag1");
        taskTag.setUserId("U1");
        taskTag.setFavorite(false);
        taskTagList.add(taskTag);
        task.setTaskTags(taskTagList);

        NamedParameterJdbcTemplate template = Mockito.mock(NamedParameterJdbcTemplate.class);
        Mockito.when(template.update(Mockito.anyString(), Mockito.anyMap())).thenReturn(1);
        Mockito.when(template.query(Mockito.anyString(), Mockito.anyMap(), Mockito.any(ResultSetExtractor.class))).thenReturn(task);
        TaskDaoImpl taskDao = new TaskDaoImpl();


        TaskTagDaoImpl taskTagDao = Mockito.mock(TaskTagDaoImpl.class);
        Mockito.when(taskTagDao.createTag(Mockito.anyString(), Mockito.any(Tag.class))).thenReturn(taskTag);
        taskDao.setJdbcTemplate(template);
        taskDao.setTaskTagDao(taskTagDao);
        Task createdTask = taskDao.createTask(task);
        assertEquals("MyTestTask", createdTask.getName());
        assertEquals("Desc1", createdTask.getDescription());
        assertEquals("HIGH", createdTask.getPriority().getDescription());
        assertEquals(1, createdTask.getTaskTags().size());
        assertEquals("Tag1", createdTask.getTaskTags().get(0).getName());
        assertEquals("U1", createdTask.getTaskTags().get(0).getUserId());
        assertEquals(false, createdTask.getTaskTags().get(0).isFavorite());
    }

    @Test
    public void testCreateWithPriorityException() throws DataIntegrityException, TaskUpdateException {
        Task task = new Task();
        task.setName("MyTestTask");
        task.setDescription("Desc1");
        task.setDueDate(Calendar.getInstance().getTime());
        TaskPriority priority = TaskPriority.HIGH;
        task.setPriority(priority);
        List<Tag> taskTagList = new ArrayList<>();
        Tag taskTag = new Tag();
        taskTag.setName("Tag1");
        taskTag.setUserId("U1");
        taskTag.setFavorite(false);
        taskTagList.add(taskTag);
        task.setTaskTags(taskTagList);

        NamedParameterJdbcTemplate template = Mockito.mock(NamedParameterJdbcTemplate.class);
        Mockito.when(template.update(Mockito.anyString(), Mockito.anyMap())).thenThrow(new DataIntegrityViolationException("FK_Task_Priority"));
        Mockito.when(template.query(Mockito.anyString(), Mockito.anyMap(), Mockito.any(ResultSetExtractor.class))).thenReturn(task);
        TaskDaoImpl taskDao = new TaskDaoImpl();


        TaskTagDaoImpl taskTagDao = Mockito.mock(TaskTagDaoImpl.class);
        Mockito.when(taskTagDao.createTag(Mockito.anyString(), Mockito.any(Tag.class))).thenReturn(taskTag);
        taskDao.setJdbcTemplate(template);
        taskDao.setTaskTagDao(taskTagDao);
        try {
            taskDao.createTask(task);
            fail();
        } catch (DataIntegrityException ex) {
            if (!ex.getMessage().equals("Unable to save the task due to a data integrity violation due to: missing Priority.")) {
                fail();
            }
        } catch (Throwable throwable) {
            fail();
        }
    }

    @Test
    public void testCreateWithUserException() throws DataIntegrityException, TaskUpdateException {
        Task task = new Task();
        task.setName("MyTestTask");
        task.setDescription("Desc1");
        task.setDueDate(Calendar.getInstance().getTime());
        TaskPriority priority = TaskPriority.HIGH;
        task.setPriority(priority);
        List<Tag> taskTagList = new ArrayList<>();
        Tag taskTag = new Tag();
        taskTag.setName("Tag1");
        taskTag.setUserId("U1");
        taskTag.setFavorite(false);
        taskTagList.add(taskTag);
        task.setTaskTags(taskTagList);

        NamedParameterJdbcTemplate template = Mockito.mock(NamedParameterJdbcTemplate.class);
        Mockito.when(template.update(Mockito.anyString(), Mockito.anyMap())).thenThrow(new DataIntegrityViolationException("FK_User_Id"));
        Mockito.when(template.query(Mockito.anyString(), Mockito.anyMap(), Mockito.any(ResultSetExtractor.class))).thenReturn(task);
        TaskDaoImpl taskDao = new TaskDaoImpl();


        TaskTagDaoImpl taskTagDao = Mockito.mock(TaskTagDaoImpl.class);
        Mockito.when(taskTagDao.createTag(Mockito.anyString(), Mockito.any(Tag.class))).thenReturn(taskTag);
        taskDao.setJdbcTemplate(template);
        taskDao.setTaskTagDao(taskTagDao);
        try {
            taskDao.createTask(task);
            fail();
        } catch (DataIntegrityException ex) {
            if (!ex.getMessage().equals("Unable to save the task due to a data integrity violation due to: missing User.")) {
                fail();
            }
        } catch (Throwable throwable) {
            fail();
        }
    }

    @Test
    public void testCreateWithGenericException() throws DataIntegrityException, TaskUpdateException {
        Task task = new Task();
        task.setName("MyTestTask");
        task.setDescription("Desc1");
        task.setDueDate(Calendar.getInstance().getTime());
        TaskPriority priority = TaskPriority.HIGH;
        task.setPriority(priority);
        List<Tag> taskTagList = new ArrayList<>();
        Tag taskTag = new Tag();
        taskTag.setName("Tag1");
        taskTag.setUserId("U1");
        taskTag.setFavorite(false);
        taskTagList.add(taskTag);
        task.setTaskTags(taskTagList);

        NamedParameterJdbcTemplate template = Mockito.mock(NamedParameterJdbcTemplate.class);
        Mockito.when(template.update(Mockito.anyString(), Mockito.anyMap())).thenThrow(new DataIntegrityViolationException("Other"));
        Mockito.when(template.query(Mockito.anyString(), Mockito.anyMap(), Mockito.any(ResultSetExtractor.class))).thenReturn(task);
        TaskDaoImpl taskDao = new TaskDaoImpl();


        TaskTagDaoImpl taskTagDao = Mockito.mock(TaskTagDaoImpl.class);
        Mockito.when(taskTagDao.createTag(Mockito.anyString(), Mockito.any(Tag.class))).thenReturn(taskTag);
        taskDao.setJdbcTemplate(template);
        taskDao.setTaskTagDao(taskTagDao);
        try {
            taskDao.createTask(task);
            fail();
        } catch (DataIntegrityException ex) {
            if (!ex.getMessage().equals("Unable to save the task due to a data integrity violation due to: unknown, please contact development.")) {
                fail();
            }
        } catch (Throwable throwable) {
            fail();
        }
    }

    @Test
    public void testUpdate() throws DataIntegrityException, TaskUpdateException {
        Task task = new Task();
        task.setName("MyTestTask");
        task.setDescription("Desc1");
        task.setDueDate(Calendar.getInstance().getTime());
        TaskPriority priority = TaskPriority.HIGH;
        task.setPriority(priority);
        List<Tag> taskTagList = new ArrayList<>();
        task.setTaskTags(taskTagList);

        NamedParameterJdbcTemplate template = Mockito.mock(NamedParameterJdbcTemplate.class);
        Mockito.when(template.update(Mockito.anyString(), Mockito.anyMap())).thenReturn(1);
        Mockito.when(template.query(Mockito.anyString(), Mockito.anyMap(), Mockito.any(ResultSetExtractor.class))).thenReturn(task);
        TaskDaoImpl taskDao = new TaskDaoImpl();

        NamedParameterJdbcTemplate templateTag = Mockito.mock(NamedParameterJdbcTemplate.class);
        TaskTagDaoImpl taskTagDao = new TaskTagDaoImpl();
        taskTagDao.setJdbcTemplate(templateTag);
        taskDao.setJdbcTemplate(template);
        taskDao.setTaskTagDao(taskTagDao);
        Task createdTask = taskDao.updateTask(task);
        assertEquals("MyTestTask", createdTask.getName());
        assertEquals("Desc1", createdTask.getDescription());
        assertEquals("HIGH", createdTask.getPriority().getDescription());
        assertEquals(0, createdTask.getTaskTags().size());
    }

    @Test
    public void testUpdateNull() throws DataIntegrityException, TaskUpdateException {
        Task task = new Task();
        task.setName("MyTestTask");
        task.setDescription("Desc1");
        task.setDueDate(Calendar.getInstance().getTime());
        TaskPriority priority = TaskPriority.HIGH;
        task.setPriority(priority);
        List<Tag> taskTagList = new ArrayList<>();
        task.setTaskTags(taskTagList);

        NamedParameterJdbcTemplate template = Mockito.mock(NamedParameterJdbcTemplate.class);
        Mockito.when(template.update(Mockito.anyString(), Mockito.anyMap())).thenReturn(1);
        Mockito.when(template.query(Mockito.anyString(), Mockito.anyMap(), Mockito.any(ResultSetExtractor.class))).thenReturn(task);
        TaskDaoImpl taskDao = new TaskDaoImpl();

        NamedParameterJdbcTemplate templateTag = Mockito.mock(NamedParameterJdbcTemplate.class);
        TaskTagDaoImpl taskTagDao = new TaskTagDaoImpl();
        taskTagDao.setJdbcTemplate(templateTag);
        taskDao.setJdbcTemplate(template);
        taskDao.setTaskTagDao(taskTagDao);
        try {
            taskDao.updateTask(null);
            fail();
        } catch (TaskUpdateException ex) {
            assertEquals("Unable to save the task due to an error. Reason: Task details missing.", ex.getMessage());
        } catch (Throwable t) {
            fail();
        }
    }

    @Test
    public void testUpdateCountException() throws DataIntegrityException, TaskUpdateException {
        Task task = new Task();
        task.setName("MyTestTask");
        task.setDescription("Desc1");
        task.setDueDate(Calendar.getInstance().getTime());
        TaskPriority priority = TaskPriority.HIGH;
        task.setPriority(priority);
        List<Tag> taskTagList = new ArrayList<>();
        task.setTaskTags(taskTagList);

        NamedParameterJdbcTemplate template = Mockito.mock(NamedParameterJdbcTemplate.class);
        Mockito.when(template.update(Mockito.anyString(), Mockito.anyMap())).thenReturn(0);
        Mockito.when(template.query(Mockito.anyString(), Mockito.anyMap(), Mockito.any(ResultSetExtractor.class))).thenReturn(task);
        TaskDaoImpl taskDao = new TaskDaoImpl();

        NamedParameterJdbcTemplate templateTag = Mockito.mock(NamedParameterJdbcTemplate.class);
        TaskTagDaoImpl taskTagDao = new TaskTagDaoImpl();
        taskTagDao.setJdbcTemplate(templateTag);
        taskDao.setJdbcTemplate(template);
        taskDao.setTaskTagDao(taskTagDao);
        try {
            taskDao.updateTask(task);
            fail();
        } catch (TaskUpdateException ex) {
            assertEquals("Unable to save the task due to an error. Reason: Save failed.", ex.getMessage());
        } catch (Throwable t) {
            fail();
        }
    }

    @Test
    public void testUpdateWithTag() throws DataIntegrityException, TaskUpdateException {
        Task task = new Task();
        task.setName("MyTestTask");
        task.setDescription("Desc1");
        task.setDueDate(Calendar.getInstance().getTime());
        TaskPriority priority = TaskPriority.HIGH;
        task.setPriority(priority);
        List<Tag> taskTagList = new ArrayList<>();
        Tag taskTag = new Tag();
        taskTag.setName("Tag1");
        taskTag.setUserId("U1");
        taskTag.setFavorite(false);
        taskTagList.add(taskTag);
        task.setTaskTags(taskTagList);

        NamedParameterJdbcTemplate template = Mockito.mock(NamedParameterJdbcTemplate.class);
        Mockito.when(template.update(Mockito.anyString(), Mockito.anyMap())).thenReturn(1);
        Mockito.when(template.query(Mockito.anyString(), Mockito.anyMap(), Mockito.any(ResultSetExtractor.class))).thenReturn(task);
        TaskDaoImpl taskDao = new TaskDaoImpl();


        TaskTagDaoImpl taskTagDao = Mockito.mock(TaskTagDaoImpl.class);
        Mockito.doNothing().when(taskTagDao).deleteTags(Mockito.anyString());
        Mockito.when(taskTagDao.createTag(Mockito.anyString(), Mockito.any(Tag.class))).thenReturn(taskTag);
        taskDao.setJdbcTemplate(template);
        taskDao.setTaskTagDao(taskTagDao);
        Task createdTask = taskDao.updateTask(task);
        assertEquals("MyTestTask", createdTask.getName());
        assertEquals("Desc1", createdTask.getDescription());
        assertEquals("HIGH", createdTask.getPriority().getDescription());
        assertEquals(1, createdTask.getTaskTags().size());
        assertEquals("Tag1", createdTask.getTaskTags().get(0).getName());
        assertEquals("U1", createdTask.getTaskTags().get(0).getUserId());
        assertEquals(false, createdTask.getTaskTags().get(0).isFavorite());
    }

    @Test
    public void testUpdateWithPriorityException() throws DataIntegrityException, TaskUpdateException {
        Task task = new Task();
        task.setName("MyTestTask");
        task.setDescription("Desc1");
        task.setDueDate(Calendar.getInstance().getTime());
        TaskPriority priority = TaskPriority.HIGH;
        task.setPriority(priority);
        List<Tag> taskTagList = new ArrayList<>();
        Tag taskTag = new Tag();
        taskTag.setName("Tag1");
        taskTag.setUserId("U1");
        taskTag.setFavorite(false);
        taskTagList.add(taskTag);
        task.setTaskTags(taskTagList);

        NamedParameterJdbcTemplate template = Mockito.mock(NamedParameterJdbcTemplate.class);
        Mockito.when(template.update(Mockito.anyString(), Mockito.anyMap())).thenReturn(1);
        Mockito.when(template.update(Mockito.anyString(), Mockito.anyMap())).thenThrow(new DataIntegrityViolationException("FK_Task_Priority"));
        TaskDaoImpl taskDao = new TaskDaoImpl();


        TaskTagDaoImpl taskTagDao = Mockito.mock(TaskTagDaoImpl.class);
        Mockito.doNothing().when(taskTagDao).deleteTags(Mockito.anyString());
        Mockito.when(taskTagDao.createTag(Mockito.anyString(), Mockito.any(Tag.class))).thenReturn(taskTag);
        taskDao.setJdbcTemplate(template);
        taskDao.setTaskTagDao(taskTagDao);
        try {
            taskDao.updateTask(task);
        } catch (DataIntegrityException ex) {
            if (!ex.getMessage().equals("Unable to save the task due to a data integrity violation due to: missing Priority.")) {
                fail();
            }
        } catch (Throwable throwable) {
            fail();
        }
    }

    @Test
    public void testUpdateWithGenericException() throws DataIntegrityException, TaskUpdateException {
        Task task = new Task();
        task.setName("MyTestTask");
        task.setDescription("Desc1");
        task.setDueDate(Calendar.getInstance().getTime());
        TaskPriority priority = TaskPriority.HIGH;
        task.setPriority(priority);
        List<Tag> taskTagList = new ArrayList<>();
        Tag taskTag = new Tag();
        taskTag.setName("Tag1");
        taskTag.setUserId("U1");
        taskTag.setFavorite(false);
        taskTagList.add(taskTag);
        task.setTaskTags(taskTagList);

        NamedParameterJdbcTemplate template = Mockito.mock(NamedParameterJdbcTemplate.class);
        Mockito.when(template.update(Mockito.anyString(), Mockito.anyMap())).thenReturn(1);
        Mockito.when(template.update(Mockito.anyString(), Mockito.anyMap())).thenThrow(new DataIntegrityViolationException("Other"));
        TaskDaoImpl taskDao = new TaskDaoImpl();


        TaskTagDaoImpl taskTagDao = Mockito.mock(TaskTagDaoImpl.class);
        Mockito.doNothing().when(taskTagDao).deleteTags(Mockito.anyString());
        Mockito.when(taskTagDao.createTag(Mockito.anyString(), Mockito.any(Tag.class))).thenReturn(taskTag);
        taskDao.setJdbcTemplate(template);
        taskDao.setTaskTagDao(taskTagDao);
        try {
            taskDao.updateTask(task);
        } catch (DataIntegrityException ex) {
            if (!ex.getMessage().equals("Unable to save the task due to a data integrity violation due to: unknown, please contact development.")) {
                fail();
            }
        } catch (Throwable throwable) {
            fail();
        }
    }

    @Test
    public void testGetTasksByUserId() throws DataIntegrityException {
        Task task = new Task();
        task.setName("MyTestTask");
        task.setDescription("Desc1");
        task.setDueDate(Calendar.getInstance().getTime());
        TaskPriority priority = TaskPriority.HIGH;
        task.setPriority(priority);

        List<Tag> taskTagList = new ArrayList<>();
        Tag taskTag = new Tag();
        taskTag.setUserId("User1");
        taskTag.setName("Tag1");
        taskTagList.add(taskTag);


        Task task1 = new Task();
        task1.setName("MyTestTask2");
        task1.setDescription("Desc2");
        task1.setDueDate(Calendar.getInstance().getTime());
        TaskPriority priority1 = TaskPriority.HIGH;
        task1.setPriority(priority);


        List<Task> tasks = new ArrayList<>();
        tasks.add(task);
        tasks.add(task1);

        NamedParameterJdbcTemplate template = Mockito.mock(NamedParameterJdbcTemplate.class);
        Mockito.when(template.query(Mockito.anyString(), Mockito.anyMap(), Mockito.any(RowMapper.class))).thenReturn(tasks);
        TaskDaoImpl taskDao = new TaskDaoImpl();

        NamedParameterJdbcTemplate templateTag = Mockito.mock(NamedParameterJdbcTemplate.class);
        TaskTagDaoImpl taskTagDao = Mockito.mock(TaskTagDaoImpl.class);
        Mockito.when(taskTagDao.getTags(Mockito.anyString())).thenReturn(taskTagList);
        taskTagDao.setJdbcTemplate(templateTag);
        taskDao.setJdbcTemplate(template);
        taskDao.setTaskTagDao(taskTagDao);
        List<Task> foundTasks = taskDao.getTasks("user1");
        assertEquals("MyTestTask", foundTasks.get(0).getName());
        assertEquals("Desc1", foundTasks.get(0).getDescription());
        assertEquals("HIGH", foundTasks.get(0).getPriority().getDescription());
        assertEquals(1, foundTasks.get(0).getTaskTags().size());
        assertEquals("Tag1", foundTasks.get(0).getTaskTags().get(0).getName());
        assertEquals("MyTestTask2", foundTasks.get(1).getName());
        assertEquals("Desc2", foundTasks.get(1).getDescription());
        assertEquals("HIGH", foundTasks.get(1).getPriority().getDescription());
        assertEquals(1, foundTasks.get(1).getTaskTags().size());
        assertEquals("Tag1", foundTasks.get(1).getTaskTags().get(0).getName());
    }

    @Test
    public void testGetTasksByUserIdNoTags() throws DataIntegrityException {
        Task task = new Task();
        task.setName("MyTestTask");
        task.setDescription("Desc1");
        task.setDueDate(Calendar.getInstance().getTime());
        TaskPriority priority = TaskPriority.HIGH;
        task.setPriority(priority);

        List<Tag> taskTagList = new ArrayList<>();


        Task task1 = new Task();
        task1.setName("MyTestTask2");
        task1.setDescription("Desc2");
        task1.setDueDate(Calendar.getInstance().getTime());
        TaskPriority priority1 = TaskPriority.HIGH;
        task1.setPriority(priority);


        List<Task> tasks = new ArrayList<>();
        tasks.add(task);
        tasks.add(task1);

        NamedParameterJdbcTemplate template = Mockito.mock(NamedParameterJdbcTemplate.class);
        Mockito.when(template.query(Mockito.anyString(), Mockito.anyMap(), Mockito.any(RowMapper.class))).thenReturn(tasks);
        TaskDaoImpl taskDao = new TaskDaoImpl();

        NamedParameterJdbcTemplate templateTag = Mockito.mock(NamedParameterJdbcTemplate.class);
        TaskTagDaoImpl taskTagDao = Mockito.mock(TaskTagDaoImpl.class);
        Mockito.when(taskTagDao.getTags(Mockito.anyString())).thenReturn(taskTagList);
        taskTagDao.setJdbcTemplate(templateTag);
        taskDao.setJdbcTemplate(template);
        taskDao.setTaskTagDao(taskTagDao);
        List<Task> foundTasks = taskDao.getTasks("user1");
        assertEquals("MyTestTask", foundTasks.get(0).getName());
        assertEquals("Desc1", foundTasks.get(0).getDescription());
        assertEquals("HIGH", foundTasks.get(0).getPriority().getDescription());
        assertEquals(0, foundTasks.get(0).getTaskTags().size());

        assertEquals("MyTestTask2", foundTasks.get(1).getName());
        assertEquals("Desc2", foundTasks.get(1).getDescription());
        assertEquals("HIGH", foundTasks.get(1).getPriority().getDescription());
        assertEquals(0, foundTasks.get(1).getTaskTags().size());

    }


    @Test
    public void testDeleteTask() throws DataIntegrityException, TaskUpdateException {
        Task task = new Task();
        task.setName("MyTestTask");
        task.setDescription("Desc1");
        task.setDueDate(Calendar.getInstance().getTime());
        TaskPriority priority = TaskPriority.HIGH;
        task.setPriority(priority);

        List<Tag> taskTagList = new ArrayList<>();


        Task task1 = new Task();
        task1.setName("MyTestTask2");
        task1.setDescription("Desc2");
        task1.setDueDate(Calendar.getInstance().getTime());
        TaskPriority priority1 = TaskPriority.HIGH;
        task1.setPriority(priority);


        List<Task> tasks = new ArrayList<>();
        tasks.add(task);
        tasks.add(task1);

        NamedParameterJdbcTemplate template = Mockito.mock(NamedParameterJdbcTemplate.class);
        Mockito.when(template.update(Mockito.anyString(), Mockito.anyMap())).thenReturn(1);
        Mockito.when(template.query(Mockito.anyString(), Mockito.anyMap(), Mockito.any(ResultSetExtractor.class))).thenReturn(task);
        TaskDaoImpl taskDao = new TaskDaoImpl();

        NamedParameterJdbcTemplate templateTag = Mockito.mock(NamedParameterJdbcTemplate.class);
        TaskTagDaoImpl taskTagDao = Mockito.mock(TaskTagDaoImpl.class);
        Mockito.doNothing().when(taskTagDao).deleteTags(Mockito.anyString());
        taskTagDao.setJdbcTemplate(templateTag);
        taskDao.setJdbcTemplate(template);
        taskDao.setTaskTagDao(taskTagDao);
        Task foundTask = taskDao.deleteTask("3");
        assertEquals("MyTestTask", foundTask.getName());
        assertEquals("Desc1", foundTask.getDescription());
        assertEquals("HIGH", foundTask.getPriority().getDescription());
        assertEquals(0, foundTask.getTaskTags().size());
    }

    @Test
    public void testMarkTaskCompleted() throws DataIntegrityException, TaskUpdateException {
        Task task = new Task();
        task.setName("MyTestTask");
        task.setDescription("Desc1");
        task.setDueDate(Calendar.getInstance().getTime());
        task.setCompleted(true);
        TaskPriority priority = TaskPriority.HIGH;
        task.setPriority(priority);

        List<Tag> taskTagList = new ArrayList<>();


        Task task1 = new Task();
        task1.setName("MyTestTask2");
        task1.setDescription("Desc2");
        task1.setDueDate(Calendar.getInstance().getTime());
        TaskPriority priority1 = TaskPriority.HIGH;
        task1.setPriority(priority);


        List<Task> tasks = new ArrayList<>();
        tasks.add(task);
        tasks.add(task1);

        NamedParameterJdbcTemplate template = Mockito.mock(NamedParameterJdbcTemplate.class);
        Mockito.when(template.update(Mockito.anyString(), Mockito.anyMap())).thenReturn(1);
        Mockito.when(template.query(Mockito.anyString(), Mockito.anyMap(), Mockito.any(ResultSetExtractor.class))).thenReturn(task);
        TaskDaoImpl taskDao = new TaskDaoImpl();

        NamedParameterJdbcTemplate templateTag = Mockito.mock(NamedParameterJdbcTemplate.class);
        TaskTagDaoImpl taskTagDao = Mockito.mock(TaskTagDaoImpl.class);
        Mockito.doNothing().when(taskTagDao).deleteTags(Mockito.anyString());
        taskTagDao.setJdbcTemplate(templateTag);
        taskDao.setJdbcTemplate(template);
        taskDao.setTaskTagDao(taskTagDao);
        Task foundTask = taskDao.markTaskCompleted("3");
        assertEquals("MyTestTask", foundTask.getName());
        assertEquals("Desc1", foundTask.getDescription());
        assertEquals(true, foundTask.isCompleted());
        assertEquals("HIGH", foundTask.getPriority().getDescription());
        assertEquals(0, foundTask.getTaskTags().size());
    }

    @Test
    public void testDeleteTaskNothingDeleted() throws DataIntegrityException {
        Task task = new Task();
        task.setName("MyTestTask");
        task.setDescription("Desc1");
        task.setDueDate(Calendar.getInstance().getTime());
        TaskPriority priority = TaskPriority.HIGH;
        task.setPriority(priority);

        List<Tag> taskTagList = new ArrayList<>();


        Task task1 = new Task();
        task1.setName("MyTestTask2");
        task1.setDescription("Desc2");
        task1.setDueDate(Calendar.getInstance().getTime());
        TaskPriority priority1 = TaskPriority.HIGH;
        task1.setPriority(priority);


        List<Task> tasks = new ArrayList<>();
        tasks.add(task);
        tasks.add(task1);

        NamedParameterJdbcTemplate template = Mockito.mock(NamedParameterJdbcTemplate.class);
        Mockito.when(template.update(Mockito.anyString(), Mockito.anyMap())).thenReturn(0);
        Mockito.when(template.query(Mockito.anyString(), Mockito.anyMap(), Mockito.any(ResultSetExtractor.class))).thenReturn(task);
        TaskDaoImpl taskDao = new TaskDaoImpl();

        NamedParameterJdbcTemplate templateTag = Mockito.mock(NamedParameterJdbcTemplate.class);
        TaskTagDaoImpl taskTagDao = Mockito.mock(TaskTagDaoImpl.class);
        Mockito.doNothing().when(taskTagDao).deleteTags(Mockito.anyString());
        taskTagDao.setJdbcTemplate(templateTag);
        taskDao.setJdbcTemplate(template);
        taskDao.setTaskTagDao(taskTagDao);
        try {
            taskDao.deleteTask("3");
            fail();
        } catch (TaskUpdateException ex) {
            assertEquals("Unable to save the task due to an error. Reason: Unable to delete task.", ex.getMessage());
        } catch (Throwable t) {
            fail();
        }
    }

    @Test
    public void testMarkCompleteTaskNothingComplete() throws DataIntegrityException {
        Task task = new Task();
        task.setName("MyTestTask");
        task.setCompleted(true);
        task.setDescription("Desc1");
        task.setDueDate(Calendar.getInstance().getTime());
        TaskPriority priority = TaskPriority.HIGH;
        task.setPriority(priority);

        List<Tag> taskTagList = new ArrayList<>();


        Task task1 = new Task();
        task1.setName("MyTestTask2");
        task1.setDescription("Desc2");
        task1.setDueDate(Calendar.getInstance().getTime());
        TaskPriority priority1 = TaskPriority.HIGH;
        task1.setPriority(priority);


        List<Task> tasks = new ArrayList<>();
        tasks.add(task);
        tasks.add(task1);

        NamedParameterJdbcTemplate template = Mockito.mock(NamedParameterJdbcTemplate.class);
        Mockito.when(template.update(Mockito.anyString(), Mockito.anyMap())).thenReturn(0);
        Mockito.when(template.query(Mockito.anyString(), Mockito.anyMap(), Mockito.any(ResultSetExtractor.class))).thenReturn(task);
        TaskDaoImpl taskDao = new TaskDaoImpl();

        NamedParameterJdbcTemplate templateTag = Mockito.mock(NamedParameterJdbcTemplate.class);
        TaskTagDaoImpl taskTagDao = Mockito.mock(TaskTagDaoImpl.class);
        Mockito.doNothing().when(taskTagDao).deleteTags(Mockito.anyString());
        taskTagDao.setJdbcTemplate(templateTag);
        taskDao.setJdbcTemplate(template);
        taskDao.setTaskTagDao(taskTagDao);
        try {
            taskDao.markTaskCompleted("3");
            fail();
        } catch (TaskUpdateException ex) {
            assertEquals("Unable to save the task due to an error. Reason: Unable to complete task.", ex.getMessage());
        } catch (Throwable t) {
            fail();
        }
    }


    @Test
    public void testResultSetExtractor() throws SQLException {
        ResultSet resultSet = Mockito.mock(ResultSet.class);
        Mockito.when(resultSet.next()).thenReturn(true);
        Mockito.when(resultSet.getString("id")).thenReturn("ID1");
        Mockito.when(resultSet.getString("name")).thenReturn("Name1");
        Mockito.when(resultSet.getString("description")).thenReturn("Desc1");
        Mockito.when(resultSet.getString("userId")).thenReturn("UserId1");
        Mockito.when(resultSet.getBoolean("complete")).thenReturn(true);
        Mockito.when(resultSet.getDate("dueDate")).thenReturn(new Date(243624363426234L));
        Mockito.when(resultSet.getTimestamp("createDate")).thenReturn(new Timestamp(243624363426234L));
        Mockito.when(resultSet.getTimestamp("lastModifiedDate")).thenReturn(new Timestamp(243624363426234L));
        Mockito.when(resultSet.getString("priorityId")).thenReturn("3");
        Mockito.when(resultSet.getString("priority")).thenReturn("HIGH");
        TaskDaoImpl.TaskResultSetExtractor extractor = new TaskDaoImpl.TaskResultSetExtractor();
        Task task = extractor.extractData(resultSet);
        assertEquals("ID1", task.getId());
        assertEquals("Name1", task.getName());
        assertEquals("Desc1", task.getDescription());
        assertEquals("UserId1", task.getUserId());
        assertEquals(true, task.isCompleted());
        assertEquals(243624363426234L, task.getDueDate().getTime());
        assertEquals(243624363426234L, task.getCreateDate().getTime());
        assertEquals(243624363426234L, task.getLastModifiedDate().getTime());
        assertEquals("3", task.getPriority().getId());
        assertEquals("HIGH", task.getPriority().getDescription());
    }

    @Test
    public void testResultSetExtractorNoResults() throws SQLException {
        ResultSet resultSet = Mockito.mock(ResultSet.class);
        Mockito.when(resultSet.next()).thenReturn(false);

        TaskDaoImpl.TaskResultSetExtractor extractor = new TaskDaoImpl.TaskResultSetExtractor();
        Task task = extractor.extractData(resultSet);
        assertNull(task);
    }

    @Test
    public void testRowMapper() throws SQLException {
        ResultSet resultSet = Mockito.mock(ResultSet.class);
        Mockito.when(resultSet.next()).thenReturn(true);
        Mockito.when(resultSet.getString("id")).thenReturn("ID1");
        Mockito.when(resultSet.getString("name")).thenReturn("Name1");
        Mockito.when(resultSet.getString("description")).thenReturn("Desc1");
        Mockito.when(resultSet.getString("userId")).thenReturn("UserId1");
        Mockito.when(resultSet.getBoolean("complete")).thenReturn(true);
        Mockito.when(resultSet.getDate("dueDate")).thenReturn(new Date(243624363426234L));
        Mockito.when(resultSet.getTimestamp("createDate")).thenReturn(new Timestamp(243624363426234L));
        Mockito.when(resultSet.getTimestamp("lastModifiedDate")).thenReturn(new Timestamp(243624363426234L));
        Mockito.when(resultSet.getString("priorityId")).thenReturn("3");
        Mockito.when(resultSet.getString("priority")).thenReturn("HIGH");
        TaskDaoImpl.TaskRowMapper rowMapper = new TaskDaoImpl.TaskRowMapper();
        Task task = rowMapper.mapRow(resultSet, 1);
        assertEquals("ID1", task.getId());
        assertEquals("Name1", task.getName());
        assertEquals("Desc1", task.getDescription());
        assertEquals("UserId1", task.getUserId());
        assertEquals(true, task.isCompleted());
        assertEquals(243624363426234L, task.getDueDate().getTime());
        assertEquals(243624363426234L, task.getCreateDate().getTime());
        assertEquals(243624363426234L, task.getLastModifiedDate().getTime());
        assertEquals("3", task.getPriority().getId());
        assertEquals("HIGH", task.getPriority().getDescription());
    }
}
