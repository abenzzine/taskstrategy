package com.taskstrategy.data.api;

import com.taskstrategy.commons.domain.Task;
import com.taskstrategy.commons.domain.TaskPriority;
import com.taskstrategy.commons.domain.TaskNotification;
import com.taskstrategy.commons.domain.TaskSearchCriteria;
import com.taskstrategy.data.api.exception.DataIntegrityException;
import com.taskstrategy.data.api.exception.TaskUpdateException;

import java.util.List;

/**
 * The TaskDao interface defines all public operations available in the data layer.
 * Clients of the TaskDao api will also need to reference an implementation such
 * as the LocalDataServiceImpl to make use of the functionality.
 */
public interface TaskDao {

    /**
     * Creates a newTask markTask service.
    * @param task - the service to create
    *
            * @return the created service
    */
    Task createTask(Task task) throws DataIntegrityException, TaskUpdateException;

    /**
     * Updates an existing service.
     * @param task - the service to update
     *
     * @return the updated service
     */
    Task updateTask(Task task) throws DataIntegrityException, TaskUpdateException;

    /**
     * Deletes a service.
     * @param taskId - the service to delete
     */
    Task deleteTask(String taskId) throws TaskUpdateException;

    /**
     * Retrieves all tasks for a user.
     * @param userId - the user whose tasks will be retrieving
     * @return a list of user tasks
     */
    List<Task> getTasks(String userId);

    /**
     * Retrieves all open tasks for a user.
     * @param userId - the user whose tasks will be retrieving
     * @return a list of user tasks that are open
     */
    List<Task> getTaskSet(String userId, int noOfTasksInSet);

    /**
     * Obtains a service with the specific id
     * @param id
     * @return
     */
    Task getTaskById(String id);

    /**
     * Marks a task completed
     * @param taskId
     * @return
     * @throws TaskUpdateException
     */
    Task markTaskCompleted(String taskId) throws TaskUpdateException;
    Task markTaskUnCompleted(String taskId) throws TaskUpdateException;


    List<Task> getTasksDueToday(String userId);

    List<Task> getTasksDueThisWeek(String userId);

    List<Task> getTasksDueThisMonth(String userId);

    List<Task> getDoneTasksByUser(String userId);

    List<TaskPriority> getPriorities();

    List<TaskNotification> getFrequencies();

    List<Task> getTasksByTag(String userId, String tag);

    void deleteTasks(String userId);

    List<Task> getPastDueTasks(String currentUserId);

    List<Task> getTasksWithReminders();
}
