package com.taskstrategy.web.util;

import com.taskstrategy.business.api.TaskService;
import com.taskstrategy.commons.domain.*;
import com.taskstrategy.commons.util.DateFormatUtil;
import com.taskstrategy.data.api.exception.DataIntegrityException;
import com.taskstrategy.web.config.Constants;
import com.taskstrategy.web.controller.forms.TaskForm;
import com.taskstrategy.web.controller.forms.TaskReminderForm;
import org.springframework.ui.ModelMap;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is responsible for providing common utility methods
 * related to task administration
 */
public class TaskUtil {

    private TaskUtil(){}

    public static void setupTaskFormModel(String id, TaskService taskService, ModelMap model)
    {
        try
        {
         addTaskFormToModel(id, taskService, model);
         addTaskPrioritiesToModel(taskService, model);
         addTaskFrequenciesToModel(taskService, model);
        } catch (DataIntegrityException ex)
        {
            ex.printStackTrace();
        }
    }

    public static void addTaskFormToModel(String id, TaskService taskService, ModelMap model) throws DataIntegrityException
    {
        if (taskService == null) throw new IllegalArgumentException("Task Service must be specified.");


        Task task = taskService.getTask(id);
        model.addAttribute(Constants.NEW_TASK_FORM, buildForm(task));
        model.addAttribute(Constants.IS_EDIT, true);
        setupQualifiers(model);
    }

    public static void addTaskPrioritiesToModel(TaskService taskService, ModelMap model)
    {
        List<TaskPriority> priorities = taskService.getPriorities();
        model.addAttribute("priorities", priorities);
    }

    public static void addTaskFrequenciesToModel(TaskService taskService, ModelMap model)
    {
        List<TaskNotification> frequencies = taskService.getFrequencies();
        model.addAttribute("frequencies", frequencies);
    }

    public static void setupQualifiers(ModelMap model) {
        List<TaskReminderQualifier> reminderQualifiers = new ArrayList<>();
        reminderQualifiers.add(TaskReminderQualifier.MIN);
        reminderQualifiers.add(TaskReminderQualifier.HOUR);
        reminderQualifiers.add(TaskReminderQualifier.DAY);
        reminderQualifiers.add(TaskReminderQualifier.WEEK);
        model.addAttribute("reminderQualifiers", reminderQualifiers);
    }

    public static TaskForm buildForm(Task task) throws DataIntegrityException {
        TaskForm taskForm = new TaskForm();
        taskForm.setId(task.getId());
        taskForm.setDescription(task.getDescription());
        if (task.getDueDate() != null) {
            taskForm.setDueDate(DateFormatUtil.formatDate(task.getDueDate()));
        }
        taskForm.setName(task.getName());
        if (task.getPriority() != null) {
            taskForm.setPriority(task.getPriority().getId());
        }

        List<TaskReminderForm> taskReminderForms = new ArrayList<>();
        if (task.getReminders() != null) {
            for (TaskReminder taskReminder : task.getReminders()) {
                TaskReminderForm form = new TaskReminderForm();
                form.setQualifier(String.valueOf(taskReminder.getQualifier().getId()));
                form.setQuantifier(String.valueOf(taskReminder.getQuantifier()));
                taskReminderForms.add(form);
            }
        }
        taskForm.setReminders(taskReminderForms);

        List<String> taskTags = new ArrayList<>();
        for (Tag taskTag : task.getTaskTags()) {
            taskTags.add(taskTag.getName());
        }
        taskForm.setTaskTags(taskTags);
        return taskForm;
    }
}
