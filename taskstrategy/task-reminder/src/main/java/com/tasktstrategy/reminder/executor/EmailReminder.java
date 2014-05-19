package com.tasktstrategy.reminder.executor;

import com.taskstrategy.business.api.EmailService;
import com.taskstrategy.business.service.EmailServiceImpl;
import com.taskstrategy.commons.domain.Tag;
import com.taskstrategy.commons.domain.Task;
import com.taskstrategy.commons.util.DateFormatUtil;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * Created by brian on 1/23/14.
 */
public class EmailReminder implements Delayed, Runnable {

    private final long delay;
    private final TimeUnit delayUnit;
    private final String emailAddress;
    private final Task task;

    public EmailReminder(long delay, TimeUnit delayUnit, String emailAddress, Task task) {
        this.delay = delay;
        this.delayUnit = delayUnit;
        this.emailAddress = emailAddress;
        this.task = task;
    }

    @Override
    public void run() {
        EmailService emailService = new EmailServiceImpl();
        String subject = "TaskStrategy: Reminder for Task: " + task.getName();
        StringBuilder body = new StringBuilder();
        body.append("TaskStrategy is notifying you of a reminder you setup on the task: ").append(task.getName());
        body.append(System.lineSeparator());
        body.append("Due: ").append(DateFormatUtil.formatDate(task.getDueDate()));
        body.append(System.lineSeparator());
        body.append("Description: ").append(task.getDescription());
        body.append(System.lineSeparator());
        body.append("Priority: ").append(task.getPriority().getDescription());
        body.append(System.lineSeparator());
        body.append("Tags: ");
        for (Tag tag : task.getTaskTags()) {
            body.append(tag.getName()).append(", ");
        }
        emailService.send(emailAddress, subject, body.toString());
    }

    @Override
    public long getDelay(TimeUnit unit) {
        return unit.convert(delay - delayUnit.convert(System.currentTimeMillis(), TimeUnit.MILLISECONDS), delayUnit);
    }

    @Override
    public int compareTo(Delayed o) {
        return 0;
    }
}
