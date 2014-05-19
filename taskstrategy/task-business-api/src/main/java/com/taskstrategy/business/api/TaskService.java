package com.taskstrategy.business.api;

import com.taskstrategy.business.api.exception.TaskServiceException;
import com.taskstrategy.business.api.exception.TaskServiceValidationException;
import com.taskstrategy.commons.domain.ChartTaskData;
import com.taskstrategy.commons.domain.Task;
import com.taskstrategy.commons.domain.TaskNotification;
import com.taskstrategy.commons.domain.TaskPriority;
import com.taskstrategy.data.api.exception.DataIntegrityException;
import com.taskstrategy.data.api.exception.TaskUpdateException;

import java.util.List;

/**
 * The TaskService interface defines all public operations available in the business layer.
 * Clients of the TaskService api will also need to reference an implementation such
 * as the LocalBusinessServiceImpl to make use of the functionality.
 */
public interface TaskService {

    /**
     * Creates a new task.
     *
     * @param task - the task to create
     * @return the created task
     */
    Task createTask(Task task) throws DataIntegrityException, TaskUpdateException, TaskServiceException, TaskServiceValidationException;

    /**
     * Creates new subtasks.
     *
     * @param taskId   - the parent task Id
     * @param subtasks - the subtasks to create
     */
    void createSubtasks(String taskId, List<Task> subtasks) throws DataIntegrityException, TaskUpdateException, TaskServiceException, TaskServiceValidationException;


    /**
     * Updates an existing service.
     *
     * @param task - the service to update
     * @return the updated service
     */
    Task updateTask(Task task) throws DataIntegrityException, TaskUpdateException, TaskServiceException, TaskServiceValidationException;

    /**
     * Deletes a service.
     *
     * @param taskId - the service to delete
     */
    Task deleteTask(String taskId) throws TaskUpdateException, TaskServiceException;

    /**
     * Retrieves all tasks for a user.
     *
     * @param userId - the user whose tasks will be retrieving
     * @return a list of user tasks
     */
    List<Task> getTasks(String userId);


    ChartTaskData getTaskChartCounts(String userId);

    Task getTask(String taskId);

    /**
     * @param taskId
     * @throws TaskServiceException
     * @throws TaskServiceValidationException
     */
    void completeTask(String taskId) throws TaskServiceException;

    void unCompleteTask(String taskId) throws TaskServiceException;

    List<TaskPriority> getPriorities();

    List<TaskNotification> getFrequencies();

    void deleteTasks(String userId);


    List<Task> getTasksByTag(String currentUserId, String tag);

    List<Task> getDoneTasksByUser(String currentUserId);

    List<Task> getTasksDueToday(String currentUserId);

    List<Task> getTasksDueThisWeek(String currentUserId);

    List<Task> getTasksDueThisMonth(String currentUserId);

    List<Task> getTaskSet(String id, int noOfTasksInSet);

    List<Task> getPastDueTasks(String currentUserId);

    void createQuickTask(Task simpleTask) throws TaskServiceException;
}
