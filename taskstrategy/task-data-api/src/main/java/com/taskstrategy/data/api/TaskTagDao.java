package com.taskstrategy.data.api;

import com.taskstrategy.commons.domain.Tag;
import com.taskstrategy.data.api.exception.DataIntegrityException;
import com.taskstrategy.data.api.exception.TaskUpdateException;

import java.util.List;

/**
 * The TaskDao interface defines all public operations available in the data layer.
 * Clients of the TaskDao api will also need to reference an implementation such
 * as the LocalDataServiceImpl to make use of the functionality.
 */
public interface TaskTagDao {


    /**
     * Creates as tag with the specified details
     * @param taskTag
     * @return
     * @throws DataIntegrityException
     * @throws TaskUpdateException
     */
    Tag createTag(String taskId, Tag taskTag) throws DataIntegrityException, TaskUpdateException;


    /**
     * Deletes the specified tag based on the provided details.
     * @param name
     * @param userId
     * @return
     * @throws TaskUpdateException
     */
    Tag deleteTag(String name, String userId) throws TaskUpdateException, DataIntegrityException;

    /**
     * Retrieves all tasks for a user.
     *
     * @param taskId - the task for which tags should be retrieved.
     * @return a list of user tasks
     */
    List<Tag> getTags(String taskId);

    /**
     * Attempts to delete all task with the specified task id
     * @param taskId
     */
    void deleteTags(String taskId);

    /**
     * Obtains a specific tag based on the name and userId
     * @param name
     * @param userId
     * @return
     */
    Tag getTag(String name, String userId);

    List<Tag> getTagsByUser(String userId);

    void updateTag(Tag tag) throws TaskUpdateException;

    List<Tag> getFavoriteTagsByUser(String userId);

    void deleteTaskTagsByUser(String currentUserId);

    void deleteTagsByUser(String currentUserId);
}
