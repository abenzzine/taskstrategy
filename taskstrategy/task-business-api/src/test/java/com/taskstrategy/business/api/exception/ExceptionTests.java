package com.taskstrategy.business.api.exception;

import com.taskstrategy.commons.domain.Task;
import org.junit.Test;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.groups.Default;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created with IntelliJ IDEA.
 * User: brian
 * Date: 10/19/13
 * Time: 9:46 PM
 * To change this template use File | Settings | File Templates.
 */
public class ExceptionTests {

    @Test
    public void testTaskServiceException() {
        TaskServiceException exception = new TaskServiceException("my Message");
        assertEquals("my Message", exception.getMessage());
    }

    @Test
    public void testTaskServiceValidationException() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Task task = new Task();

        TaskServiceValidationException exception = new TaskServiceValidationException(null);
        assertEquals("There was a problem processing the request due to validation issues: ", exception.getMessage());

        exception = new TaskServiceValidationException(validator.validate(task, Default.class));
        assertTrue(exception.getMessage().contains("There was a problem processing the request due to validation issues:"));
        assertTrue(exception.getMessage().contains("Task due date cannot be blank."));
        assertTrue(exception.getMessage().contains("Task must have a priority set."));
        assertTrue(exception.getMessage().contains("Task user id cannot be empty."));
        assertTrue(exception.getMessage().contains("Task name cannot be empty."));
    }
}
