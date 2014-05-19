package com.taskstrategy.business.api.exception;

import javax.validation.ConstraintViolation;
import java.util.HashSet;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: brian
 * Date: 10/19/13
 * Time: 8:48 PM
 * To change this template use File | Settings | File Templates.
 */
public class TaskServiceValidationException extends Exception {

    private Set<? extends ConstraintViolation> violations;

    public TaskServiceValidationException(Set<? extends ConstraintViolation> violations) {
        super();
        this.violations = violations;
    }

    @Override
    public String getMessage() {
        Set<String> messages = new HashSet<>();
        String message = "There was a problem processing the request due to validation issues: ";
        if (violations != null) {
            for (ConstraintViolation<?> violation : violations) {
                if (messages.add(violation.getMessage())) {
                    message += "<br>" + violation.getMessage();
                }
            }
        }
        return message;
    }
}
