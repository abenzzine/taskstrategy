package com.taskstrategy.commons.domain;

import java.util.UUID;

/**
 * Created by brian on 2/1/14.
 */
public class TaskReminder {

    private String id;
    private int quantifier;
    private TaskReminderQualifier qualifier;

    public TaskReminder() {
        this.id = UUID.randomUUID().toString();
    }

    public int getQuantifier() {
        return quantifier;
    }

    public void setQuantifier(int quantifier) {
        this.quantifier = quantifier;
    }

    public TaskReminderQualifier getQualifier() {
        return qualifier;
    }

    public void setQualifier(TaskReminderQualifier qualifier) {
        this.qualifier = qualifier;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
