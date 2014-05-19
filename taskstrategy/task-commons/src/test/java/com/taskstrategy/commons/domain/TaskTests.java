package com.taskstrategy.commons.domain;


import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.groups.Default;
import java.util.*;

import static junit.framework.Assert.*;

/**
 * Created with IntelliJ IDEA.
 * User: brian
 * Date: 10/19/13
 * Time: 10:04 PM
 * To change this template use File | Settings | File Templates.
 */
public class TaskTests {

    @Test
    public void verifyPojo() {
        Task task = new Task();
        assertNotNull(task.getId());
        assertNotNull(task.getTaskTags());

        task.setDescription("Desc1");
        Date dueDate = new Date(1234455676L);
        task.setId("ID1");
        task.setDueDate(dueDate);
        task.setName("Name1");
        task.setPriority(TaskPriority.LOW);
        task.setUserId("User1");
        task.setCompleted(true);
        List<Tag> taskTagList = new ArrayList<>();
        Tag t = new Tag();
        t.setUserId("User1");
        t.setName("TagA");
        t.setDisplayColor("c");
        t.setFavorite(false);
        taskTagList.add(t);
        task.setTaskTags(taskTagList);
        Date createDate = new Date(1234455677L);
        task.setCreateDate(createDate);
        Date lastModifiedDate = new Date(1234455678L);
        task.setLastModifiedDate(lastModifiedDate);


        assertEquals("ID1", task.getId());
        assertEquals("Desc1", task.getDescription());
        assertEquals("Name1", task.getName());
        assertEquals(1234455676L, task.getDueDate().getTime());
        assertEquals("1", task.getPriority().getId());
        assertEquals("Low", task.getPriority().getDescription());
        assertEquals(true, task.isCompleted());
        assertEquals("User1", task.getUserId());
        assertEquals("TagA", task.getTaskTags().get(0).getName());
        assertEquals(1234455677L, task.getCreateDate().getTime());
        assertEquals(1234455678L, task.getLastModifiedDate().getTime());
    }

    @Test
    public void verifyIDValidation() {
        Task t = new Task();
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Task>> violations = validator.validate(t, Default.class);
        assertEquals(4, violations.size());
        List<String> violationMessages = new ArrayList<>();
        t.setId(null);
        violations = validator.validate(t, Default.class);
        assertEquals(5, violations.size());

        violationMessages.clear();
        for (ConstraintViolation<Task> violation : violations) {
            violationMessages.add(violation.getMessage());
        }
        assertTrue(violationMessages.contains("Task id cannot be empty."));

        t.setId("");
        violations = validator.validate(t, Default.class);
        assertEquals(5, violations.size());

        violationMessages.clear();
        for (ConstraintViolation<Task> violation : violations) {
            violationMessages.add(violation.getMessage());
        }
        assertTrue(violationMessages.contains("Task id cannot be empty."));

        t.setId("1");
        violations = validator.validate(t, Default.class);
        assertEquals(4, violations.size());
    }

    @Test
    public void verifyUserIDValidation() {
        Task t = new Task();
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Task>> violations = validator.validate(t, Default.class);
        List<String> violationMessages = new ArrayList<>();
        for (ConstraintViolation<Task> violation : violations) {
            violationMessages.add(violation.getMessage());
        }
        assertTrue(violationMessages.contains("Task user id cannot be empty."));

        t.setUserId("1");
        violations = validator.validate(t, Default.class);
        violationMessages.clear();
        for (ConstraintViolation<Task> violation : violations) {
            violationMessages.add(violation.getMessage());
        }
        assertEquals(3, violations.size());
        assertFalse(violationMessages.contains("Task user id cannot be empty."));

        t.setUserId("");
        violations = validator.validate(t, Default.class);
        assertEquals(4, violations.size());

        violationMessages.clear();
        for (ConstraintViolation<Task> violation : violations) {
            violationMessages.add(violation.getMessage());
        }
        assertTrue(violationMessages.contains("Task user id cannot be empty."));

        t.setUserId("1");
        violations = validator.validate(t, Default.class);
        violationMessages.clear();
        for (ConstraintViolation<Task> violation : violations) {
            violationMessages.add(violation.getMessage());
        }
        assertEquals(3, violations.size());
        assertFalse(violationMessages.contains("Task user id cannot be empty."));
    }

    @Test
    public void verifyNameValidation() {
        Task t = new Task();
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Task>> violations = validator.validate(t, Default.class);
        List<String> violationMessages = new ArrayList<>();
        for (ConstraintViolation<Task> violation : violations) {
            violationMessages.add(violation.getMessage());
        }
        assertTrue(violationMessages.contains("Task name cannot be empty."));

        t.setName("1");
        violations = validator.validate(t, Default.class);
        violationMessages.clear();
        for (ConstraintViolation<Task> violation : violations) {
            violationMessages.add(violation.getMessage());
        }
        assertEquals(3, violations.size());
        assertFalse(violationMessages.contains("Task name cannot be empty."));

        t.setName("");
        violations = validator.validate(t, Default.class);
        assertEquals(4, violations.size());

        violationMessages.clear();
        for (ConstraintViolation<Task> violation : violations) {
            violationMessages.add(violation.getMessage());
        }
        assertTrue(violationMessages.contains("Task name cannot be empty."));

        t.setName("1");
        violations = validator.validate(t, Default.class);
        violationMessages.clear();
        for (ConstraintViolation<Task> violation : violations) {
            violationMessages.add(violation.getMessage());
        }
        assertEquals(3, violations.size());
        assertFalse(violationMessages.contains("Task name cannot be empty."));
    }

    @Test
    public void verifyDueDateValidation() {
        Task t = new Task();
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Task>> violations = validator.validate(t, Default.class);
        List<String> violationMessages = new ArrayList<>();
        for (ConstraintViolation<Task> violation : violations) {
            violationMessages.add(violation.getMessage());
        }
        assertTrue(violationMessages.contains("Task due date cannot be blank."));

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        t.setDueDate(calendar.getTime());
        violations = validator.validate(t, Default.class);
        violationMessages.clear();
        for (ConstraintViolation<Task> violation : violations) {
            violationMessages.add(violation.getMessage());
        }
        assertFalse(violationMessages.contains("Task due date cannot be blank."));

        calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        t.setDueDate(calendar.getTime());
        violations = validator.validate(t, Default.class);
        violationMessages.clear();
        for (ConstraintViolation<Task> violation : violations) {
            violationMessages.add(violation.getMessage());
        }
        assertTrue(violationMessages.contains("Task due date must be in the future."));

        calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        t.setDueDate(calendar.getTime());
        violations = validator.validate(t, Default.class);
        violationMessages.clear();
        for (ConstraintViolation<Task> violation : violations) {
            violationMessages.add(violation.getMessage());
        }
        assertFalse(violationMessages.contains("Task due date must be in the future."));
    }

    @Test
    public void verifyPriorityValidation() {
        Task t = new Task();
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Task>> violations = validator.validate(t, Default.class);
        List<String> violationMessages = new ArrayList<>();
        for (ConstraintViolation<Task> violation : violations) {
            violationMessages.add(violation.getMessage());
        }
        assertTrue(violationMessages.contains("Task must have a priority set."));

        t.setPriority(TaskPriority.MEDIUM);
        violations = validator.validate(t, Default.class);
        violationMessages.clear();
        for (ConstraintViolation<Task> violation : violations) {
            violationMessages.add(violation.getMessage());
        }
        assertFalse(violationMessages.contains("Task must have a priority set."));
    }
}
