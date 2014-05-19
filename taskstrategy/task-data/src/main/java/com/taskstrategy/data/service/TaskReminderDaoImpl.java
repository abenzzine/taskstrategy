package com.taskstrategy.data.service;

import com.taskstrategy.commons.domain.TaskReminder;
import com.taskstrategy.commons.domain.TaskReminderQualifier;
import com.taskstrategy.data.api.TaskReminderDao;
import com.taskstrategy.data.api.exception.DataIntegrityException;
import com.taskstrategy.data.api.exception.TaskUpdateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by brian on 2/2/14.
 */
@Repository
@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = {Exception.class, RuntimeException.class})
public class TaskReminderDaoImpl implements TaskReminderDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(TaskTagDaoImpl.class);
    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    private static final String INSERT_REMINDER = "INSERT INTO TaskReminder(id, taskId, quantifier, qualifier) VALUES (:id, :taskId, :quantifier, :qualifier)";
    private static final String DELETE_REMINDER = "DELETE FROM TaskReminder Where taskId = :taskId";
    private static final String GET_REMINDER = "SELECT * FROM TaskReminder Where taskId = :taskId";


    @Override
    public TaskReminder createReminder(String taskId, TaskReminder reminder) throws DataIntegrityException, TaskUpdateException {
        if (reminder != null) {
            try {
                Map<String, Object> params = new HashMap<>();
                params.put("id", reminder.getId());
                params.put("taskId", taskId);

                if (reminder.getQuantifier() > 0) {
                    params.put("quantifier", reminder.getQuantifier());
                }else{
                    throw new TaskUpdateException("Reminder cannot be created - Please insert positive integer for frequency");
                }


                params.put("qualifier", reminder.getQualifier().getId());
                int count = jdbcTemplate.update(INSERT_REMINDER, params);
                if (count != 1) {
                    throw new TaskUpdateException("Reminder cannot be created");
                }
                return reminder;
            } catch (DataIntegrityViolationException ex) {
                LOGGER.error("Error creating reminder", ex);
                String messageReason = "unknown, please contact development";
                if (ex.getMessage().contains("fk_TaskReminder_1")) {
                    messageReason = "missing task";
                }
                throw new DataIntegrityException(messageReason);
            }
        } else {
            LOGGER.info("No reminder details were provided for creation.");
            throw new TaskUpdateException("Reminder details missing");
        }
    }

    @Override
    public List<TaskReminder> getReminders(String taskId) {
        Map<String, Object> params = new HashMap<>();
        params.put("taskId", taskId);
        return jdbcTemplate.query(GET_REMINDER, params, new TaskReminderRowMapper());
    }

    @Override
    public void deleteReminders(String taskId) throws TaskUpdateException {
        Map<String, Object> params = new HashMap<>();
        params.put("taskId", taskId);
        jdbcTemplate.update(DELETE_REMINDER, params);
    }

    protected static final class TaskReminderRowMapper implements RowMapper<TaskReminder> {

        @Override
        public TaskReminder mapRow(ResultSet resultSet, int i) throws SQLException {
            TaskReminder reminder = new TaskReminder();
            reminder.setId(resultSet.getString("id"));
            reminder.setQuantifier(resultSet.getInt("quantifier"));
            reminder.setQualifier(TaskReminderQualifier.getQualifier(resultSet.getInt("qualifier")));
            return reminder;
        }

    }

    public void setJdbcTemplate(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
}
