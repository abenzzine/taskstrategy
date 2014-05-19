package com.taskstrategy.commons.domain;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.validation.groups.Default;

/**
 * Created with IntelliJ IDEA.
 * User: brian
 * Date: 10/14/13
 * Time: 7:23 PM
 * To change this template use File | Settings | File Templates.
 */
public enum TaskPriority {

    LOW(1, "Low"), MEDIUM(2, "Medium"), HIGH(3, "High"), CRITICAL(4, "Critical");

    private int id;
    private String text;


    TaskPriority(int id, String text)
    {
        this.id = id;
        this.text = text;
    }

    public String getId() {
        return "" + id;
    }

    public String getDescription() {
        return text;
    }

    public static TaskPriority getTaskPriority(String id)
    {
        TaskPriority priority = null;
        switch (id)
        {
            case "1":
                priority = TaskPriority.LOW;
                break;
            case "2":
                priority = TaskPriority.MEDIUM;
                break;
            case "3":
                priority = TaskPriority.HIGH;
                break;
            case "4":
                priority = TaskPriority.CRITICAL;
                break;
        }
        return priority;
    }

}
