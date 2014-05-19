package com.taskstrategy.data.api;

import com.taskstrategy.data.api.exception.DataIntegrityException;
import com.taskstrategy.data.api.exception.TaskUpdateException;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created with IntelliJ IDEA.
 * User: brian
 * Date: 10/19/13
 * Time: 9:36 PM
 * To change this template use File | Settings | File Templates.
 */
public class ExceptionTests {

    @Test
    public void testDataIntegrityException(){
        DataIntegrityException exception = new DataIntegrityException("missing User");
        assertEquals("Unable to save the task due to a data integrity violation due to missing User.", exception.getMessage());
    }

    @Test
    public void testTaskUpdateException(){
        TaskUpdateException exception = new TaskUpdateException("random");
        assertEquals("Unable to save the task due to an error. Reason: random.", exception.getMessage());
    }
}
