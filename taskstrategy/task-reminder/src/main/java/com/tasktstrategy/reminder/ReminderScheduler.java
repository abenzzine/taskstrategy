package com.tasktstrategy.reminder;

import com.taskstrategy.commons.domain.Task;
import com.taskstrategy.commons.domain.TaskReminder;
import com.taskstrategy.commons.domain.User;
import com.taskstrategy.data.api.TaskDao;
import com.taskstrategy.data.api.UserDao;
import com.tasktstrategy.reminder.executor.EmailReminder;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by brian on 1/23/14.
 */
public class ReminderScheduler implements Runnable {

    private DelayQueue<EmailReminder> emailReminders;
    private Executor executor;
    private TaskDao taskDao;
    private UserDao userDao;

    public ReminderScheduler(TaskDao taskDao, UserDao userDao) {
        executor = Executors.newCachedThreadPool();
        this.taskDao = taskDao;
        this.userDao = userDao;
        emailReminders = new DelayQueue<>();
    }

    @Override
    public void run() {
        while (true) {
            try {
                EmailReminder emailReminder = emailReminders.poll(60, TimeUnit.SECONDS);
                if (emailReminder != null) {
                    executor.execute(emailReminder);
                }
            } catch (InterruptedException e) {
                //Ignore, we don't care.
            }
        }
    }

    public void loadReminders() {
        emailReminders.clear();
        List<Task> taskList = taskDao.getTasksWithReminders();
        for (Task task : taskList) {
            Date dueDate = task.getDueDate();
            for (TaskReminder taskReminder : task.getReminders()) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(dueDate);
                switch (taskReminder.getQualifier()) {
                    case DAY:
                        calendar.add(Calendar.DATE, taskReminder.getQuantifier() * -1);
                        break;
                    case HOUR:
                        calendar.add(Calendar.HOUR, taskReminder.getQuantifier() * -1);
                        break;
                    case MIN:
                        calendar.add(Calendar.MINUTE, taskReminder.getQuantifier() * -1);
                        break;
                    case WEEK:
                        calendar.add(Calendar.DATE, taskReminder.getQuantifier() * -7);
                        break;
                }

                User user = userDao.loadUser(task.getUserId());
                EmailReminder reminder = new EmailReminder(calendar.getTimeInMillis(), TimeUnit.MILLISECONDS, user.getUsername(), task);
                emailReminders.add(reminder);
            }
        }
    }

}
