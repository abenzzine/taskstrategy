package com.taskstrategy.commons.domain;

import java.util.List;

/**
 * This class is responsible for holding an ordered collection of tasks.
 */
public class TaskSet {

    private final Task[] tasks;
    private int currentTask = -1;

    public TaskSet(List<Task> taskList)
    {
        if (taskList == null) throw new IllegalArgumentException();

        tasks = taskList.toArray(new Task[taskList.size()]);
    }

    public int getActiveTaskPtr()
    {
        return currentTask;
    }

    public Task getActiveTask(){return currentTask < getTaskCount() ? tasks[currentTask] : null;}

    public void next()
    {
      currentTask++;
    }

    public int getTaskCount()
    {
       return tasks.length;
    }

    public boolean hasReviewed(int taskNo)
    {
        return taskNo < getActiveTaskPtr();
    }


    public boolean hasMoreTasks(){return currentTask + 1 < tasks.length;}
}
