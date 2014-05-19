package com.taskstrategy.web.controller.forms;

/**
 * Created by brian on 2/1/14.
 */
public class TaskReminderForm {

    private String quantifier;
    private String qualifier;

    public String getQuantifier() {
        return quantifier;
    }

    public void setQuantifier(String quantifier) {
        this.quantifier = quantifier;
    }

    public String getQualifier() {
        return qualifier;
    }

    public void setQualifier(String qualifier) {
        this.qualifier = qualifier;
    }
}
