package com.taskstrategy.commons.domain;


import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.validation.groups.Default;
/**
 * Created with IntelliJ IDEA.
 * User: Latif
 * Date: 1/23/14
 * Time: 3:52 PM
 * To change this template use File | Settings | File Templates.
 */
public enum TaskNotification {

    NONE(1),
    SEVEN_AM(2),
    EIGHT_AM(3),
    NINE_AM(4),
    TWO_HOURS(5),
    ONE_HOUR(6),
    HALF_HOUR(7),
    TWENTY_FOUR_HOURS(8);

    private int id;


    TaskNotification(int id)
    {
        this.id = id;
    }

    public String getId() {
        return "" + id;
    }

    public String getDescription() {
        return name();
    }

    public static TaskNotification getTaskNotification(String id)
    {
        TaskNotification frequency = null;

        switch (id)
        {
            case "1":
                frequency = TaskNotification.NONE;
                break;
            case "2":
                frequency = TaskNotification.SEVEN_AM;
                break;
            case "3":
                frequency = TaskNotification.EIGHT_AM;;
                break;

            case "4":
                frequency = TaskNotification.NINE_AM;;
                break;
            case "5":
                frequency = TaskNotification.TWO_HOURS;;
                break;
            case "6":
                frequency = TaskNotification.ONE_HOUR;
                break;
            case "7":
                frequency = TaskNotification.HALF_HOUR;
                break;
            case "8" :
                frequency = TaskNotification.TWENTY_FOUR_HOURS;
              break;
        }
        return frequency;
    }

}
