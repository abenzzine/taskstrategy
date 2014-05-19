package com.taskstrategy.business.service;

import com.taskstrategy.business.api.TagService;
import com.taskstrategy.business.api.exception.TaskServiceException;
import com.taskstrategy.business.api.exception.TaskServiceValidationException;
import com.taskstrategy.commons.domain.BaseDomain;
import com.taskstrategy.commons.domain.Tag;
import com.taskstrategy.data.api.TaskTagDao;
import com.taskstrategy.data.api.exception.DataIntegrityException;
import com.taskstrategy.data.api.exception.TaskUpdateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.validation.groups.Default;
import java.util.List;
import java.util.Set;

/**
 * The TaskServiceImpl class provides a convenient local implementation
 * of the TaskService
 */
@Service
public class TagServiceImpl implements TagService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TagServiceImpl.class);
    @Autowired
    private TaskTagDao taskTagDao;
    @Autowired
    private Validator validator;

    private <T extends BaseDomain> void checkForValidationViolations(T itemToCheck) throws TaskServiceValidationException {
        Set<ConstraintViolation<T>> violationSet = validator.validate(itemToCheck, Default.class);
        if (!violationSet.isEmpty()) {
            throw new TaskServiceValidationException(violationSet);
        }
    }

    @Override
    public List<Tag> getTags(String userId) {
        return taskTagDao.getTagsByUser(userId);
    }

    @Override
    public List<Tag> getFavoriteTags(String userId) {
        return taskTagDao.getFavoriteTagsByUser(userId);
    }

    @Override
    public void deleteTag(String tagName, String userId) throws TaskServiceException {
        try {
            taskTagDao.deleteTag(tagName, userId);
        } catch (DataIntegrityException e) {
            LOGGER.error("Error processing the delete tag business function.", e);
            throw new TaskServiceException(e.getMessage());
        } catch (TaskUpdateException e) {
            LOGGER.error("Error processing the delete tag business function.", e);
            throw new TaskServiceException(e.getMessage());
        }
    }

    @Override
    public Tag getTag(String name, String userId) {
        return taskTagDao.getTag(name, userId);
    }

    @Override
    public void updateTag(Tag tag) throws TaskUpdateException {
        taskTagDao.updateTag(tag);
    }

    @Override
    public void deleteTaskTags(String currentUserId) {
        taskTagDao.deleteTaskTagsByUser(currentUserId);
    }

    @Override
    public void deleteTags(String currentUserId) {
        taskTagDao.deleteTagsByUser(currentUserId);
    }
}
