package com.taskstrategy.web.controller.forms;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: brian
 * Date: 10/24/13
 * Time: 6:58 PM
 * To change this template use File | Settings | File Templates.
 */
public class TaskForm {

    private String id;
    private String name;
    private String description;
    private String dueDate;
    private String priority;
    private List<String> taskTags;
    private boolean completed;
    private List<TaskReminderForm> reminders;

    public TaskForm() {
        reminders = new ArrayList<>();
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public List<String> getTaskTags() {
        return taskTags;
    }

    public void setTaskTags(List<String> taskTags) {
        this.taskTags = taskTags;
    }

    public List<TaskReminderForm> getReminders() {
        return reminders;
    }

    public void setReminders(List<TaskReminderForm> reminders) {
        this.reminders = reminders;
    }
}
