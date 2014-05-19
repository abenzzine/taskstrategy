package com.taskstrategy.data.api.exception;

/**
 * Created with IntelliJ IDEA.
 * User: brian
 * Date: 10/15/13
 * Time: 7:38 PM
 * To change this template use File | Settings | File Templates.
 */
public class TaskUpdateException extends Exception {

    public TaskUpdateException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return "Unable to save the task due to an error. Reason: " + super.getMessage() + ".";
    }
}
