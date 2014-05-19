package com.taskstrategy.commons.domain;

/**
 * Created by brian on 2/1/14.
 */
public enum TaskReminderQualifier {

    MIN(1, "Minutes"), HOUR(2, "Hours"), DAY(3, "Days"), WEEK(4, "Weeks");

    private final int id;
    private final String name;

    TaskReminderQualifier(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public static TaskReminderQualifier getQualifier(int qualifier) {
        switch (qualifier) {
            case 1:
                return MIN;
            case 2:
                return HOUR;
            case 3:
                return DAY;
            case 4:
                return WEEK;
        }
        return null;
    }
}
