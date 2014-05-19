package com.taskstrategy.commons.domain;


import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.groups.Default;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import static junit.framework.Assert.*;

/**
 * Created with IntelliJ IDEA.
 * User: brian
 * Date: 10/19/13
 * Time: 10:04 PM
 * To change this template use File | Settings | File Templates.
 */
public class TaskTagTests {

    @Test
    public void verifyPojo() {
        Tag task = new Tag();

        task.setFavorite(true);
        Date date = new Date(8978943789L);
        task.setCreateDate(date);
        task.setDisplayColor("Blue");
        task.setName("Name1");
        task.setUserId("User1");
        Date lastModified = new Date(8978943790L);
        task.setLastModifiedDate(lastModified);

        assertEquals(true, task.isFavorite());
        assertEquals("Blue", task.getDisplayColor());
        assertEquals("Name1", task.getName());
        assertEquals("User1", task.getUserId());
        assertEquals(8978943789L, task.getCreateDate().getTime());
        assertEquals(8978943790L, task.getLastModifiedDate().getTime());
    }

    @Test
    public void verifyNameValidation() {
        Tag t = new Tag();
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Tag>> violations = validator.validate(t, Default.class);
        assertEquals(2, violations.size());
        List<String> violationMessages = new ArrayList<>();
        violationMessages.clear();
        for (ConstraintViolation<Tag> violation : violations) {
            violationMessages.add(violation.getMessage());
        }
        assertTrue(violationMessages.contains("Task Tag name cannot be empty."));

        t.setName("");
        violations = validator.validate(t, Default.class);
        violationMessages.clear();
        for (ConstraintViolation<Tag> violation : violations) {
            violationMessages.add(violation.getMessage());
        }
        assertTrue(violationMessages.contains("Task Tag name cannot be empty."));

        t.setName("1");
        violations = validator.validate(t, Default.class);
        violationMessages.clear();
        for (ConstraintViolation<Tag> violation : violations) {
            violationMessages.add(violation.getMessage());
        }
        assertFalse(violationMessages.contains("Task Tag name cannot be empty."));

        t.setName(null);
        violations = validator.validate(t, Default.class);
        violationMessages.clear();
        for (ConstraintViolation<Tag> violation : violations) {
            violationMessages.add(violation.getMessage());
        }
        assertTrue(violationMessages.contains("Task Tag name cannot be empty."));
    }

    @Test
    public void verifyUserIdValidation() {
        Tag t = new Tag();
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Tag>> violations = validator.validate(t, Default.class);
        assertEquals(2, violations.size());
        List<String> violationMessages = new ArrayList<>();
        violationMessages.clear();
        for (ConstraintViolation<Tag> violation : violations) {
            violationMessages.add(violation.getMessage());
        }
        assertTrue(violationMessages.contains("Task Tag user id cannot be empty."));

        t.setUserId("");
        violations = validator.validate(t, Default.class);
        violationMessages.clear();
        for (ConstraintViolation<Tag> violation : violations) {
            violationMessages.add(violation.getMessage());
        }
        assertTrue(violationMessages.contains("Task Tag user id cannot be empty."));

        t.setUserId("1");
        violations = validator.validate(t, Default.class);
        violationMessages.clear();
        for (ConstraintViolation<Tag> violation : violations) {
            violationMessages.add(violation.getMessage());
        }
        assertFalse(violationMessages.contains("Task Tag user id cannot be empty."));

        t.setUserId(null);
        violations = validator.validate(t, Default.class);
        violationMessages.clear();
        for (ConstraintViolation<Tag> violation : violations) {
            violationMessages.add(violation.getMessage());
        }
        assertTrue(violationMessages.contains("Task Tag user id cannot be empty."));
    }
}
