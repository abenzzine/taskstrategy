package com.taskstrategy.data.service;

import com.taskstrategy.commons.domain.ChartTaskData;
import com.taskstrategy.data.api.ChartDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * The TaskDaoImpl class provides a convenient local implementation
 * of the TaskDao
 */
@Repository
@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = {Exception.class, RuntimeException.class})
public class ChartDaoImpl implements ChartDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(TaskDaoImpl.class);
    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    private static final String GET_TASK_COUNTS = "SELECT " +
            "SUM(CASE WHEN DATE(NOW()) = DATE(t.dueDate) AND t.complete = 0 THEN 1 ELSE 0 END) AS openDueTodayCount, " +
            "SUM(CASE WHEN WeekOfYear(NOW()) = WeekOfYear(t.dueDate) AND t.complete = 0 THEN 1 ELSE 0 END) AS openDueThisWeekCount, " +
            "SUM(CASE WHEN Month(NOW()) = Month(t.dueDate) AND t.complete = 0 THEN 1 ELSE 0 END) AS openDueThisMonthCount, " +
            "SUM(CASE WHEN DATE(NOW()) = DATE(t.dueDate) AND t.complete = 0 AND t.priorityId = 1 THEN 1 ELSE 0 END) AS lowDueTodayCount, " +
            "SUM(CASE WHEN WeekOfYear(NOW()) = WeekOfYear(t.dueDate) AND t.complete = 0 AND t.priorityId = 1 THEN 1 ELSE 0 END) AS lowDueThisWeekCount, " +
            "SUM(CASE WHEN Month(NOW()) = Month(t.dueDate) AND t.complete = 0 AND t.priorityId = 1 THEN 1 ELSE 0 END) AS lowDueThisMonthCount, " +
            "SUM(CASE WHEN DATE(NOW()) = DATE(t.dueDate) AND t.complete = 0 AND t.priorityId = 2 THEN 1 ELSE 0 END) AS medDueTodayCount, " +
            "SUM(CASE WHEN WeekOfYear(NOW()) = WeekOfYear(t.dueDate) AND t.complete = 0 AND t.priorityId = 2 THEN 1 ELSE 0 END) AS medDueThisWeekCount, " +
            "SUM(CASE WHEN Month(NOW()) = Month(t.dueDate) AND t.complete = 0 AND t.priorityId = 2 THEN 1 ELSE 0 END) AS medDueThisMonthCount, " +
            "SUM(CASE WHEN DATE(NOW()) = DATE(t.dueDate) AND t.complete = 0 AND t.priorityId = 3 THEN 1 ELSE 0 END) AS highDueTodayCount, " +
            "SUM(CASE WHEN WeekOfYear(NOW()) = WeekOfYear(t.dueDate) AND t.complete = 0 AND t.priorityId = 3 THEN 1 ELSE 0 END) AS highDueThisWeekCount, " +
            "SUM(CASE WHEN Month(NOW()) = Month(t.dueDate) AND t.complete = 0 AND t.priorityId = 3 THEN 1 ELSE 0 END) AS highDueThisMonthCount, " +
            "SUM(CASE WHEN DATE(NOW()) = DATE(t.dueDate) AND t.complete = 0 AND t.priorityId = 4 THEN 1 ELSE 0 END) AS criticalDueTodayCount, " +
            "SUM(CASE WHEN WeekOfYear(NOW()) = WeekOfYear(t.dueDate) AND t.complete = 0 AND t.priorityId = 4 THEN 1 ELSE 0 END) AS criticalDueThisWeekCount, " +
            "SUM(CASE WHEN Month(NOW()) = Month(t.dueDate) AND t.complete = 0 AND t.priorityId = 4 THEN 1 ELSE 0 END) AS criticalDueThisMonthCount, " +
            "SUM(CASE WHEN t.complete = 0 and t.dueDate < CURDATE() THEN 1 ELSE 0 END) AS pastDueTodayCount, " +
            "SUM(CASE WHEN WeekOfYear(NOW()) = WeekOfYear(t.dueDate) AND t.complete = 0 and t.dueDate < CURDATE() THEN 1 ELSE 0 END) AS pastDueThisWeekCount, " +
            "SUM(CASE WHEN Month(NOW()) = Month(t.dueDate) AND t.complete = 0 and t.dueDate < CURDATE() THEN 1 ELSE 0 END) AS pastDueThisMonthCount, " +
            "SUM(CASE WHEN DATE(NOW()) = DATE(t.dueDate) AND t.complete = 1 THEN 1 ELSE 0 END) AS completedTodayCount, " +
            "SUM(CASE WHEN WeekOfYear(NOW()) = WeekOfYear(t.dueDate) AND t.complete = 1 THEN 1 ELSE 0 END) AS completedThisWeekCount, " +
            "SUM(CASE WHEN Month(NOW()) = Month(t.dueDate) AND t.complete = 1 THEN 1 ELSE 0 END) AS completedThisMonthCount, " +
            "SUM(CASE WHEN DATE(NOW()) = DATE(t.dueDate) AND t.complete = 1 and (t.lastModifiedDate < t.dueDate) THEN 1 ELSE 0 END) AS completedEarlyTodayCount, " +
            "SUM(CASE WHEN WeekOfYear(NOW()) = WeekOfYear(t.dueDate) AND t.complete = 1 and (t.lastModifiedDate < t.dueDate) THEN 1 ELSE 0 END) AS completedEarlyThisWeekCount, " +
            "SUM(CASE WHEN Month(NOW()) = Month(t.dueDate) AND t.complete = 1 and (t.lastModifiedDate < t.dueDate) THEN 1 ELSE 0 END) AS completedEarlyThisMonthCount " +
            "FROM Task t WHERE t.userId = :userId";


    @Override
    public ChartTaskData getTaskChartInfo(String userId) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("userId", userId);
        return jdbcTemplate.query(GET_TASK_COUNTS, paramMap, new ChartTaskDataResultSetExtractor());
    }

    private static final class ChartTaskDataResultSetExtractor implements ResultSetExtractor<ChartTaskData> {
        @Override
        public ChartTaskData extractData(ResultSet resultSet) throws SQLException, DataAccessException {
            ChartTaskData chartTaskData = null;
            if (resultSet.next()) {
                chartTaskData = new ChartTaskData();
                chartTaskData.setOpenDueTodayCount(resultSet.getInt("openDueTodayCount"));
                chartTaskData.setOpenDueThisWeekCount(resultSet.getInt("openDueThisWeekCount"));
                chartTaskData.setOpenDueThisMonthCount(resultSet.getInt("openDueThisMonthCount"));

                chartTaskData.setLowDueTodayCount(resultSet.getInt("lowDueTodayCount"));
                chartTaskData.setLowDueThisWeekCount(resultSet.getInt("lowDueThisWeekCount"));
                chartTaskData.setLowDueThisMonthCount(resultSet.getInt("lowDueThisMonthCount"));

                chartTaskData.setMedDueTodayCount(resultSet.getInt("medDueTodayCount"));
                chartTaskData.setMedDueThisWeekCount(resultSet.getInt("medDueThisWeekCount"));
                chartTaskData.setMedDueThisMonthCount(resultSet.getInt("medDueThisMonthCount"));

                chartTaskData.setHighDueTodayCount(resultSet.getInt("highDueTodayCount"));
                chartTaskData.setHighDueThisWeekCount(resultSet.getInt("highDueThisWeekCount"));
                chartTaskData.setHighDueThisMonthCount(resultSet.getInt("highDueThisMonthCount"));

                chartTaskData.setCriticalDueTodayCount(resultSet.getInt("criticalDueTodayCount"));
                chartTaskData.setCriticalDueThisWeekCount(resultSet.getInt("criticalDueThisWeekCount"));
                chartTaskData.setCriticalDueThisMonthCount(resultSet.getInt("criticalDueThisMonthCount"));

                chartTaskData.setPastDueTodayCount(resultSet.getInt("pastDueTodayCount"));
                chartTaskData.setPastDueThisWeekCount(resultSet.getInt("pastDueThisWeekCount"));
                chartTaskData.setPastDueThisMonthCount(resultSet.getInt("pastDueThisMonthCount"));

                chartTaskData.setCompletedTodayCount(resultSet.getInt("completedTodayCount"));
                chartTaskData.setCompletedThisWeekCount(resultSet.getInt("completedThisWeekCount"));
                chartTaskData.setCompletedThisMonthCount(resultSet.getInt("completedThisMonthCount"));

                chartTaskData.setCompletedEarlyTodayCount(resultSet.getInt("completedEarlyTodayCount"));
                chartTaskData.setCompletedEarlyThisWeekCount(resultSet.getInt("completedEarlyThisWeekCount"));
                chartTaskData.setCompletedEarlyThisMonthCount(resultSet.getInt("completedEarlyThisMonthCount"));
            }
            return chartTaskData;
        }
    }


}
