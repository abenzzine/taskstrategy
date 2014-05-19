package com.taskstrategy.commons.domain;


import com.taskstrategy.commons.domain.constraints.ValidTaskDate;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.validation.groups.Default;
import java.util.*;

/**
 * The Task class is responsible for defining the core fields that represent
 * a Task.
 */
public class Task extends BaseDomain implements Comparator<Task> {

    @NotNull(message = "Task id cannot be empty.", groups = Default.class)
    @Size(min = 1, message = "Task id cannot be empty.", groups = Default.class)
    private String id;
    @NotNull(message = "Task user id cannot be empty.", groups = Default.class)
    @Size(min = 1, message = "Task user id cannot be empty.", groups = Default.class)
    private String userId;
    private String parentTaskId;
    @NotNull(message = "Task name cannot be empty.", groups = Default.class)
    @Size(min = 1, max = 75, message = "Task name cannot be empty or more than 75 characters.", groups = Default.class)
    private String name;
    private String description;
    @ValidTaskDate(message = "Task must have a due date and cannot be before 01/01/2001.")
    private Date dueDate;
    @NotNull(message = "Task must have a priority set.", groups = Default.class)
    private TaskPriority priority;
    @Valid
    private List<Tag> taskTags;
    private boolean completed;
    private int passCount;
    private List<TaskReminder> reminders;

    public Task() {
        id = UUID.randomUUID().toString();
        dueDate = new Date();
        taskTags = new ArrayList<>();
        priority = TaskPriority.MEDIUM;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getParentTaskId() {
        return this.parentTaskId;
    }

    public void setParentTaskId(String parentTaskId) {
        this.parentTaskId = parentTaskId;
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

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public TaskPriority getPriority() {
        return priority;
    }

    public void setPriority(TaskPriority priority) {
        this.priority = priority;
    }

    public List<Tag> getTaskTags() {
        return taskTags;
    }

    public void setTaskTags(List<Tag> taskTags) {
        this.taskTags = taskTags;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public void setPassCount(int passCount) {
        this.passCount = passCount;
    }


    public int getPassCount() {
        return passCount;
    }

    public void incrementPassCount() {
        this.passCount++;
    }

    public List<TaskReminder> getReminders() {
        return reminders == null ? Collections.EMPTY_LIST : reminders;
    }

    public void setReminders(List<TaskReminder> reminders) {
        this.reminders = reminders;
    }

    // Overriding the compareTo method
    public int compareTo(Task t) {
        return (this.getName()).compareTo(t.getName());
    }

    // Overriding the compare method to sort the dueDate
    public int compare(Task t1, Task t2) {
        int result = 1;
        Date d1 = t1.getDueDate();
        Date d2 = t2.getDueDate();
        if (d1 != null & d2 != null) {
            result = t1.getDueDate().compareTo(t2.getDueDate());
        } else if (d1 == null) {
            result = -1;
        }
        return result;
    }


}
