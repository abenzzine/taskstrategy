package com.taskstrategy.data.api;

import com.taskstrategy.commons.domain.TaskReminder;
import com.taskstrategy.data.api.exception.DataIntegrityException;
import com.taskstrategy.data.api.exception.TaskUpdateException;

import java.util.List;

/**
 * Created by brian on 2/2/14.
 */
public interface TaskReminderDao {
    TaskReminder createReminder(String taskId, TaskReminder reminder) throws DataIntegrityException, TaskUpdateException;

    List<TaskReminder> getReminders(String taskId);

    void deleteReminders(String taskId) throws TaskUpdateException;
}
