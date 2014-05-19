package com.taskstrategy.data.service;

import com.taskstrategy.commons.domain.Tag;
import com.taskstrategy.data.api.exception.DataIntegrityException;
import com.taskstrategy.data.api.exception.TaskUpdateException;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created with IntelliJ IDEA.
 * User: brian
 * Date: 10/14/13
 * Time: 8:43 PM
 * This class is responsible for testing the TaskTagDao
 */

public class TaskTagDaoTest {

    @Test
    public void testCreate() throws DataIntegrityException, TaskUpdateException {
        Tag taskTag = new Tag();
        taskTag.setUserId("U1");
        taskTag.setName("Name1");
        taskTag.setFavorite(true);
        taskTag.setDisplayColor("#333000");

        NamedParameterJdbcTemplate templateTag = Mockito.mock(NamedParameterJdbcTemplate.class);
        TaskTagDaoImpl taskTagDao = new TaskTagDaoImpl();
        taskTagDao.setJdbcTemplate(templateTag);

        Mockito.when(templateTag.update(Mockito.anyString(), Mockito.anyMap())).thenReturn(1);
        Mockito.when(templateTag.query(Mockito.anyString(), Mockito.anyMap(), Mockito.any(ResultSetExtractor.class))).thenReturn(taskTag);
        Tag createdTag = taskTagDao.createTag("1", taskTag);
        assertEquals("U1", createdTag.getUserId());
        assertEquals("Name1", createdTag.getName());
        assertEquals(true, createdTag.isFavorite());
        assertEquals("#333000", createdTag.getDisplayColor());
    }

    @Test
    public void testCreateNull() throws DataIntegrityException, TaskUpdateException {
        Tag taskTag = new Tag();
        taskTag.setUserId("U1");
        taskTag.setName("Name1");
        taskTag.setFavorite(true);
        taskTag.setDisplayColor("#333000");

        NamedParameterJdbcTemplate templateTag = Mockito.mock(NamedParameterJdbcTemplate.class);
        TaskTagDaoImpl taskTagDao = new TaskTagDaoImpl();
        taskTagDao.setJdbcTemplate(templateTag);

        Mockito.when(templateTag.update(Mockito.anyString(), Mockito.anyMap())).thenReturn(1);
        Mockito.when(templateTag.query(Mockito.anyString(), Mockito.anyMap(), Mockito.any(ResultSetExtractor.class))).thenReturn(taskTag);
        try {
            taskTagDao.createTag(null, null);
            fail();
        } catch (TaskUpdateException ex) {
            assertEquals("Unable to save the task due to an error. Reason: Tag details missing.", ex.getMessage());
        } catch (Throwable t) {
            fail();
        }
    }

    @Test
    public void testCreateCountException() throws DataIntegrityException {
        Tag taskTag = new Tag();
        taskTag.setUserId("U1");
        taskTag.setName("Name1");
        taskTag.setFavorite(true);
        taskTag.setDisplayColor("#333000");

        NamedParameterJdbcTemplate templateTag = Mockito.mock(NamedParameterJdbcTemplate.class);
        TaskTagDaoImpl taskTagDao = new TaskTagDaoImpl();
        taskTagDao.setJdbcTemplate(templateTag);

        Mockito.when(templateTag.update(Mockito.anyString(), Mockito.anyMap())).thenReturn(0);
        Mockito.when(templateTag.query(Mockito.anyString(), Mockito.anyMap(), Mockito.any(ResultSetExtractor.class))).thenReturn(null);
        try {
            taskTagDao.createTag("", taskTag);
            fail();
        } catch (TaskUpdateException ex) {
            assertEquals("Unable to save the task due to an error. Reason: Tag cannot be created.", ex.getMessage());
        } catch (Throwable t) {
            fail();
        }
    }

    @Test
    public void testCreateNotInitiallyFound() throws DataIntegrityException, TaskUpdateException {
        Tag taskTag = new Tag();
        taskTag.setUserId("U1");
        taskTag.setName("Name1");
        taskTag.setFavorite(true);
        taskTag.setDisplayColor("#333000");

        NamedParameterJdbcTemplate templateTag = Mockito.mock(NamedParameterJdbcTemplate.class);
        TaskTagDaoImpl taskTagDao = new TaskTagDaoImpl();
        taskTagDao.setJdbcTemplate(templateTag);

        Mockito.when(templateTag.update(Mockito.anyString(), Mockito.anyMap())).thenReturn(1);
        Mockito.when(templateTag.query(Mockito.anyString(), Mockito.anyMap(), Mockito.any(ResultSetExtractor.class))).thenReturn(null);
        Tag createdTag = taskTagDao.createTag("", taskTag);
        assertNull(createdTag);
    }

    @Test
    public void testCreateFKUser() throws DataIntegrityException {
        Tag taskTag = new Tag();
        taskTag.setUserId("U1");
        taskTag.setName("Name1");
        taskTag.setFavorite(true);
        taskTag.setDisplayColor("#333000");

        NamedParameterJdbcTemplate templateTag = Mockito.mock(NamedParameterJdbcTemplate.class);
        TaskTagDaoImpl taskTagDao = new TaskTagDaoImpl();
        taskTagDao.setJdbcTemplate(templateTag);

        Mockito.when(templateTag.update(Mockito.anyString(), Mockito.anyMap())).thenThrow(new DataIntegrityViolationException("FK_User_Id"));
        Mockito.when(templateTag.query(Mockito.anyString(), Mockito.anyMap(), Mockito.any(ResultSetExtractor.class))).thenReturn(null);
        try {
            taskTagDao.createTag("", taskTag);
            fail();
        } catch (DataIntegrityException ex) {
            assertEquals("Unable to save the task due to a data integrity violation due to: missing User.", ex.getMessage());
        } catch (Throwable t) {
            fail();
        }
    }

    @Test
    public void testDelete() throws DataIntegrityException, TaskUpdateException {
        Tag taskTag = new Tag();
        taskTag.setUserId("U1");
        taskTag.setName("Name1");
        taskTag.setFavorite(true);
        taskTag.setDisplayColor("#333000");

        NamedParameterJdbcTemplate templateTag = Mockito.mock(NamedParameterJdbcTemplate.class);
        TaskTagDaoImpl taskTagDao = new TaskTagDaoImpl();
        taskTagDao.setJdbcTemplate(templateTag);

        Mockito.when(templateTag.update(Mockito.anyString(), Mockito.anyMap())).thenReturn(1);
        Mockito.when(templateTag.query(Mockito.anyString(), Mockito.anyMap(), Mockito.any(ResultSetExtractor.class))).thenReturn(taskTag);
        Tag createdTag = taskTagDao.deleteTag("1", "1");
        assertEquals("U1", createdTag.getUserId());
        assertEquals("Name1", createdTag.getName());
        assertEquals(true, createdTag.isFavorite());
        assertEquals("#333000", createdTag.getDisplayColor());
    }

    @Test
    public void testDeleteCountException() throws DataIntegrityException {
        Tag taskTag = new Tag();
        taskTag.setUserId("U1");
        taskTag.setName("Name1");
        taskTag.setFavorite(true);
        taskTag.setDisplayColor("#333000");

        NamedParameterJdbcTemplate templateTag = Mockito.mock(NamedParameterJdbcTemplate.class);
        TaskTagDaoImpl taskTagDao = new TaskTagDaoImpl();
        taskTagDao.setJdbcTemplate(templateTag);

        Mockito.when(templateTag.update(Mockito.anyString(), Mockito.anyMap())).thenReturn(0);
        Mockito.when(templateTag.query(Mockito.anyString(), Mockito.anyMap(), Mockito.any(ResultSetExtractor.class))).thenReturn(null);
        try {
            taskTagDao.deleteTag("1", "1");
            fail();
        } catch (TaskUpdateException ex) {
            assertEquals("Unable to save the task due to an error. Reason: Tag was not successfully deleted.", ex.getMessage());
        } catch (Throwable t) {
            fail();
        }
    }

    @Test
    public void testGetTags() throws DataIntegrityException, TaskUpdateException {
        Tag taskTag = new Tag();
        taskTag.setUserId("U1");
        taskTag.setName("Name1");
        taskTag.setFavorite(true);
        taskTag.setDisplayColor("#333000");

        Tag taskTag1 = new Tag();
        taskTag1.setUserId("U2");
        taskTag1.setName("Name2");
        taskTag1.setFavorite(false);
        taskTag1.setDisplayColor("#FFF000");

        List<Tag> taskTagList = new ArrayList<>();
        taskTagList.add(taskTag);
        taskTagList.add(taskTag1);

        NamedParameterJdbcTemplate templateTag = Mockito.mock(NamedParameterJdbcTemplate.class);
        TaskTagDaoImpl taskTagDao = new TaskTagDaoImpl();
        taskTagDao.setJdbcTemplate(templateTag);

        Mockito.when(templateTag.update(Mockito.anyString(), Mockito.anyMap())).thenReturn(1);
        Mockito.when(templateTag.query(Mockito.anyString(), Mockito.anyMap(), Mockito.any(RowMapper.class))).thenReturn(taskTagList);
        List<Tag> loadedTags = taskTagDao.getTags("T");
        assertEquals("U1", loadedTags.get(0).getUserId());
        assertEquals("Name1", loadedTags.get(0).getName());
        assertEquals(true, loadedTags.get(0).isFavorite());
        assertEquals("#333000", loadedTags.get(0).getDisplayColor());
        assertEquals("U2", loadedTags.get(1).getUserId());
        assertEquals("Name2", loadedTags.get(1).getName());
        assertEquals(false, loadedTags.get(1).isFavorite());
        assertEquals("#FFF000", loadedTags.get(1).getDisplayColor());
    }

    @Test
    public void testGetTagsNoneLoaded() throws DataIntegrityException, TaskUpdateException {
        Tag taskTag = new Tag();
        taskTag.setUserId("U1");
        taskTag.setName("Name1");
        taskTag.setFavorite(true);
        taskTag.setDisplayColor("#333000");

        Tag taskTag1 = new Tag();
        taskTag1.setUserId("U2");
        taskTag1.setName("Name2");
        taskTag1.setFavorite(false);
        taskTag1.setDisplayColor("#FFF000");

        List<Tag> taskTagList = new ArrayList<>();
        taskTagList.add(taskTag);
        taskTagList.add(taskTag1);

        NamedParameterJdbcTemplate templateTag = Mockito.mock(NamedParameterJdbcTemplate.class);
        TaskTagDaoImpl taskTagDao = new TaskTagDaoImpl();
        taskTagDao.setJdbcTemplate(templateTag);

        Mockito.when(templateTag.update(Mockito.anyString(), Mockito.anyMap())).thenReturn(1);
        Mockito.when(templateTag.query(Mockito.anyString(), Mockito.anyMap(), Mockito.any(RowMapper.class))).thenReturn(null);
        List<Tag> loadedTags = taskTagDao.getTags("T");

        assertEquals(0, loadedTags.size());
    }

    @Test
    public void testDeleteTags() throws DataIntegrityException, TaskUpdateException {
        Tag taskTag = new Tag();
        taskTag.setUserId("U1");
        taskTag.setName("Name1");
        taskTag.setFavorite(true);
        taskTag.setDisplayColor("#333000");

        Tag taskTag1 = new Tag();
        taskTag1.setUserId("U2");
        taskTag1.setName("Name2");
        taskTag1.setFavorite(false);
        taskTag1.setDisplayColor("#FFF000");

        List<Tag> taskTagList = new ArrayList<>();
        taskTagList.add(taskTag);
        taskTagList.add(taskTag1);

        NamedParameterJdbcTemplate templateTag = Mockito.mock(NamedParameterJdbcTemplate.class);
        TaskTagDaoImpl taskTagDao = new TaskTagDaoImpl();
        taskTagDao.setJdbcTemplate(templateTag);

        Mockito.when(templateTag.update(Mockito.anyString(), Mockito.anyMap())).thenReturn(1);
        taskTagDao.deleteTags("");
    }

    @Test
    public void testDeleteTagsPart2() throws DataIntegrityException, TaskUpdateException {
        Tag taskTag = new Tag();
        taskTag.setUserId("U1");
        taskTag.setName("Name1");
        taskTag.setFavorite(true);
        taskTag.setDisplayColor("#333000");

        Tag taskTag1 = new Tag();
        taskTag1.setUserId("U2");
        taskTag1.setName("Name2");
        taskTag1.setFavorite(false);
        taskTag1.setDisplayColor("#FFF000");

        List<Tag> taskTagList = new ArrayList<>();
        taskTagList.add(taskTag);
        taskTagList.add(taskTag1);

        NamedParameterJdbcTemplate templateTag = Mockito.mock(NamedParameterJdbcTemplate.class);
        TaskTagDaoImpl taskTagDao = new TaskTagDaoImpl();
        taskTagDao.setJdbcTemplate(templateTag);

        Mockito.when(templateTag.update(Mockito.anyString(), Mockito.anyMap())).thenReturn(0);
        taskTagDao.deleteTags("");
    }

    @Test
    public void testResultSetExtractor() throws SQLException {
        ResultSet resultSet = Mockito.mock(ResultSet.class);
        Mockito.when(resultSet.next()).thenReturn(true);
        Mockito.when(resultSet.getString("name")).thenReturn("Name1");
        Mockito.when(resultSet.getString("taskId")).thenReturn("TaskId1");
        Mockito.when(resultSet.getString("userId")).thenReturn("UserId1");
        Mockito.when(resultSet.getTimestamp("createDate")).thenReturn(new Timestamp(243624363426234L));
        Mockito.when(resultSet.getTimestamp("lastModifiedDate")).thenReturn(new Timestamp(243624363426234L));
        TaskTagDaoImpl.TaskTagResultSetExtractor extractor = new TaskTagDaoImpl.TaskTagResultSetExtractor();
        Tag taskTag = extractor.extractData(resultSet);
        assertEquals("Name1", taskTag.getName());
        assertEquals("UserId1", taskTag.getUserId());
        assertEquals(243624363426234L, taskTag.getCreateDate().getTime());
        assertEquals(243624363426234L, taskTag.getLastModifiedDate().getTime());
    }

    @Test
    public void testResultSetExtractorNoResults() throws SQLException {
        ResultSet resultSet = Mockito.mock(ResultSet.class);
        Mockito.when(resultSet.next()).thenReturn(false);

        TaskTagDaoImpl.TaskTagResultSetExtractor extractor = new TaskTagDaoImpl.TaskTagResultSetExtractor();
        Tag taskTag = extractor.extractData(resultSet);
        assertNull(taskTag);
    }

    @Test
    public void testRowMapper() throws SQLException {
        ResultSet resultSet = Mockito.mock(ResultSet.class);
        Mockito.when(resultSet.next()).thenReturn(true);
        Mockito.when(resultSet.getString("name")).thenReturn("Name1");
        Mockito.when(resultSet.getString("taskId")).thenReturn("TaskId1");
        Mockito.when(resultSet.getString("userId")).thenReturn("UserId1");
        Mockito.when(resultSet.getTimestamp("createDate")).thenReturn(new Timestamp(243624363426234L));
        Mockito.when(resultSet.getTimestamp("lastModifiedDate")).thenReturn(new Timestamp(243624363426234L));
        TaskTagDaoImpl.TaskTagRowMapper extractor = new TaskTagDaoImpl.TaskTagRowMapper();
        Tag taskTag = extractor.mapRow(resultSet, 1);
        assertEquals("Name1", taskTag.getName());
        assertEquals("UserId1", taskTag.getUserId());
        assertEquals(243624363426234L, taskTag.getCreateDate().getTime());
        assertEquals(243624363426234L, taskTag.getLastModifiedDate().getTime());
    }
}
