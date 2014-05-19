package com.taskstrategy.business.api;

import com.taskstrategy.business.api.exception.TaskServiceException;
import com.taskstrategy.commons.domain.Tag;
import com.taskstrategy.data.api.exception.DataIntegrityException;
import com.taskstrategy.data.api.exception.TaskUpdateException;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: brian
 * Date: 11/2/13
 * Time: 9:12 AM
 * To change this template use File | Settings | File Templates.
 */
public interface TagService {

    List<Tag> getTags(String userId);

    List<Tag> getFavoriteTags(String userId);

    void deleteTag(String tagName, String userId) throws TaskServiceException;

    Tag getTag(String name, String userId);

    void updateTag(Tag tag) throws TaskUpdateException;

    void deleteTaskTags(String currentUserId);

    void deleteTags(String currentUserId);
}
