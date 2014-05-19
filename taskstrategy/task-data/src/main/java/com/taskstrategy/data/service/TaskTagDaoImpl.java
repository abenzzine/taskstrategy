package com.taskstrategy.data.service;

import com.taskstrategy.commons.domain.Tag;
import com.taskstrategy.data.api.TaskTagDao;
import com.taskstrategy.data.api.exception.DataIntegrityException;
import com.taskstrategy.data.api.exception.TaskUpdateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The TaskDaoImpl class provides a convenient local implementation
 * of the TaskDao
 */
@Repository
@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = {Exception.class, RuntimeException.class})
public class TaskTagDaoImpl implements TaskTagDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(TaskTagDaoImpl.class);
    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    private static final String INSERT_TAG = "INSERT INTO Tag (name, userId, favorite, displayColor, createDate, lastModifiedDate) VALUES (:name, :userId, :favorite, :displayColor, NOW(), NOW())";
    private static final String INSERT_TAG_MAPPING = "INSERT INTO TaskTag(taskId, name, userId) VALUES(:taskId, :name, :userId)";
    private static final String GET_TAG = "SELECT * FROM Tag Where name = :name and userId = :userId";
    private static final String GET_TAG_BY_TASK_ID = "SELECT * FROM Tag t, TaskTag ttg Where t.name = ttg.name and t.userId = ttg.userId and ttg.taskId = :taskId order by t.name asc";
    private static final String DELETE_TAG = "DELETE FROM Tag Where name = :name and userId = :userId";
    private static final String DELETE_TAGS = "DELETE FROM TaskTag Where taskId = :taskId";
    private static final String GET_TAGS_BY_USER = "SELECT * FROM Tag Where userId = :userId";
    private static final String GET_FAVORITE_TAGS_BY_USER = "SELECT * FROM Tag Where userId = :userId and favorite = true";
    private static final String UPDATE_TAG = "UPDATE Tag SET favorite = :favorite, displayColor = :color WHERE name = :name and userId = :userId";
    private static final String DELETE_TAGS_BY_USER = "DELETE FROM TaskTag WHERE userId = :userId";
    private static final String DELETE_TAGS_BY_USER_ID = "DELETE FROM Tag WHERE userId = :userId";

    @Override
    public Tag createTag(String taskId, Tag taskTag) throws DataIntegrityException, TaskUpdateException {
        if (taskTag != null) {
            Tag tag = getTag(taskTag.getName(), taskTag.getUserId());
            if (tag == null) {
                try {
                    Map<String, Object> params = new HashMap<>();
                    params.put("name", taskTag.getName());
                    params.put("userId", taskTag.getUserId());
                    params.put("favorite", taskTag.isFavorite());
                    params.put("displayColor", taskTag.getDisplayColor());
                    int count = jdbcTemplate.update(INSERT_TAG, params);
                    if (count != 1) {
                        throw new TaskUpdateException("Tag cannot be created");
                    }
                    params.put("taskId", taskId);
                    count = jdbcTemplate.update(INSERT_TAG_MAPPING, params);
                    if (count != 1) {
                        throw new TaskUpdateException("Tag Mapping cannot be created");
                    }
                    LOGGER.debug("Loading saved details.");
                    return getTag(taskTag.getName(), taskTag.getUserId());
                } catch (DataIntegrityViolationException ex) {
                    LOGGER.error("Error creating tag", ex);
                    String messageReason = "unknown, please contact development";
                    if (ex.getMessage().contains("FK_User_Id")) {
                        messageReason = "missing User";
                    }
                    throw new DataIntegrityException(messageReason);
                }
            } else {
                Map<String, Object> params = new HashMap<>();
                params.put("taskId", taskId);
                params.put("name", taskTag.getName());
                params.put("userId", taskTag.getUserId());
                int count = jdbcTemplate.update(INSERT_TAG_MAPPING, params);
                if (count != 1) {
                    throw new TaskUpdateException("Tag Mapping cannot be created");
                }
                return tag;
            }

        } else {
            LOGGER.info("No tag details were provided for creation.");
            throw new TaskUpdateException("Tag details missing");
        }
    }

    @Override
    public Tag getTag(String name, String userId) {
        Map<String, Object> params = new HashMap<>();
        params.put("name", name);
        params.put("userId", userId);
        return jdbcTemplate.query(GET_TAG, params, new TaskTagResultSetExtractor());
    }

    @Override
    public Tag deleteTag(String name, String userId) throws TaskUpdateException, DataIntegrityException {
        Tag taskTag = getTag(name, userId);
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("name", name);
            params.put("userId", userId);
            int count = jdbcTemplate.update(DELETE_TAG, params);
            if (count != 1) {
                LOGGER.error("Tag was not able to be deleted with name = " + name + " userId = " + userId);
                throw new TaskUpdateException("Tag was not successfully deleted");
            }
        } catch (DataIntegrityViolationException ex) {
            LOGGER.error("Error creating tag", ex);
            String messageReason = "unknown, please contact development";
            if (ex.getMessage().contains("FK")) {
                messageReason = "Tag being used by a Task";
            }
            throw new DataIntegrityException(messageReason);
        }
        return taskTag;
    }

    @Override
    public List<Tag> getTags(String taskId) {
        Map<String, Object> params = new HashMap<>();
        params.put("taskId", taskId);
        List<Tag> taskTags = jdbcTemplate.query(GET_TAG_BY_TASK_ID, params, new TaskTagRowMapper());
        if (taskTags == null) {
            taskTags = new ArrayList<>();
        }
        return taskTags;
    }

    @Override
    public List<Tag> getTagsByUser(String userId) {
        Map<String, Object> params = new HashMap<>();
        params.put("userId", userId);
        return jdbcTemplate.query(GET_TAGS_BY_USER, params, new TaskTagRowMapper());
    }

    @Override
    public List<Tag> getFavoriteTagsByUser(String userId) {
        Map<String, Object> params = new HashMap<>();
        params.put("userId", userId);
        return jdbcTemplate.query(GET_FAVORITE_TAGS_BY_USER, params, new TaskTagRowMapper());
    }

    @Override
    public void deleteTags(String taskId) {
        Map<String, Object> params = new HashMap<>();
        params.put("taskId", taskId);
        int count = jdbcTemplate.update(DELETE_TAGS, params);
        if (count < 1) {
            LOGGER.warn("Tags may not have been deleted for taskId = " + taskId);
        }
    }

    @Override
    public void updateTag(Tag tag) throws TaskUpdateException {
        Map<String, Object> params = new HashMap<>();
        params.put("favorite", tag.isFavorite());
        params.put("name", tag.getName());
        params.put("userId", tag.getUserId());
        params.put("color", tag.getDisplayColor());
        int count = jdbcTemplate.update(UPDATE_TAG, params);
        if (count != 1) {
            LOGGER.error("Tag was not able to be updated with name = " + tag.getName() + " userId = " + tag.getUserId());
            throw new TaskUpdateException("Tag was not successfully updated");
        }
    }

    @Override
    public void deleteTaskTagsByUser(String currentUserId) {
        Map<String, Object> params = new HashMap<>();
        params.put("userId", currentUserId);
        jdbcTemplate.update(DELETE_TAGS_BY_USER, params);
    }

    @Override
    public void deleteTagsByUser(String currentUserId) {
        Map<String, Object> params = new HashMap<>();
        params.put("userId", currentUserId);
        jdbcTemplate.update(DELETE_TAGS_BY_USER_ID, params);
    }

    protected static final class TaskTagResultSetExtractor implements ResultSetExtractor<Tag> {

        @Override
        public Tag extractData(ResultSet resultSet) throws SQLException, DataAccessException {
            Tag taskTag = null;
            if (resultSet.next()) {
                taskTag = new Tag();
                taskTag.setName(resultSet.getString("name"));
                taskTag.setUserId(resultSet.getString("userId"));
                taskTag.setFavorite(resultSet.getBoolean("favorite"));
                taskTag.setDisplayColor(resultSet.getString("displayColor"));
                taskTag.setCreateDate(resultSet.getTimestamp("createDate"));
                taskTag.setLastModifiedDate(resultSet.getTimestamp("lastModifiedDate"));
            }
            return taskTag;
        }
    }

    protected static final class TaskTagRowMapper implements RowMapper<Tag> {

        @Override
        public Tag mapRow(ResultSet resultSet, int i) throws SQLException {
            Tag taskTag = new Tag();
            taskTag.setName(resultSet.getString("name"));
            taskTag.setUserId(resultSet.getString("userId"));
            taskTag.setFavorite(resultSet.getBoolean("favorite"));
            taskTag.setDisplayColor(resultSet.getString("displayColor"));
            taskTag.setCreateDate(resultSet.getTimestamp("createDate"));
            taskTag.setLastModifiedDate(resultSet.getTimestamp("lastModifiedDate"));
            return taskTag;
        }

    }

    protected void setJdbcTemplate(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
}
