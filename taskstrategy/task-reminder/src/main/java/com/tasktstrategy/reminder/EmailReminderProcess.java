package com.tasktstrategy.reminder;

import com.taskstrategy.data.service.TaskDaoImpl;
import com.taskstrategy.data.service.TaskReminderDaoImpl;
import com.taskstrategy.data.service.UserDaoImpl;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by brian on 1/30/14.
 */
public class EmailReminderProcess {

    public static void main(String[] args) {
        String url = args[0];
        String username = args[1];
        String password = args[2];
        ReminderScheduler reminderScheduler = buildReminderScheduler(url, username, password);
        reminderScheduler.loadReminders();
        Executors.newSingleThreadExecutor().execute(reminderScheduler);
        long currentTime = System.currentTimeMillis();
        while (true) {
            if (TimeUnit.HOURS.convert(System.currentTimeMillis() - currentTime, TimeUnit.MILLISECONDS) > 1) {
                reminderScheduler.loadReminders();
                currentTime = System.currentTimeMillis();
            }
            try {
                Thread.sleep(60000);
            } catch (InterruptedException e) {
                //Ignore
            }
        }
    }

    private static ReminderScheduler buildReminderScheduler(String url, String username, String password) {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);

        NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(dataSource);
        TaskReminderDaoImpl taskReminderDao = new TaskReminderDaoImpl();
        taskReminderDao.setJdbcTemplate(template);
        TaskDaoImpl taskDao = new TaskDaoImpl();
        taskDao.setJdbcTemplate(template);
        taskDao.setTaskReminderDao(taskReminderDao);
        UserDaoImpl userDao = new UserDaoImpl();
        userDao.setJdbcTemplate(template);
        return new ReminderScheduler(taskDao, userDao);
    }

}
