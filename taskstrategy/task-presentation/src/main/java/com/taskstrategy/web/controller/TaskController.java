package com.taskstrategy.web.controller;

import com.taskstrategy.business.api.TaskService;
import com.taskstrategy.business.api.exception.TaskServiceException;
import com.taskstrategy.business.api.exception.TaskServiceValidationException;
import com.taskstrategy.commons.domain.*;
import com.taskstrategy.commons.util.DateFormatUtil;
import com.taskstrategy.data.api.exception.DataIntegrityException;
import com.taskstrategy.data.api.exception.TaskUpdateException;
import com.taskstrategy.web.config.Constants;
import com.taskstrategy.web.controller.forms.IDForm;
import com.taskstrategy.web.controller.forms.TaskForm;
import com.taskstrategy.web.controller.forms.TaskReminderForm;
import com.taskstrategy.web.util.TaskUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;


/**
 * This class is just a controller to support the task view page
 */
@Controller
@SessionAttributes(Constants.IS_EDIT)
public class TaskController extends AbstractController {


    private static final String ERROR_MESSAGE = "errorMessage";

    private static final String TASK_NEW = "taskNew";
    private static final String REDIRECT_DONE_TASKS = "redirect:/done";

    @Autowired
    private TaskService taskService;

    @Secured("ROLE_USER")
    @RequestMapping(value = "/tasks/edit/{id}", method = RequestMethod.GET)
    public String editTask(@PathVariable(value = "id") String id, ModelMap model) throws DataIntegrityException, TaskUpdateException, TaskServiceException, TaskServiceValidationException {
        TaskUtil.setupTaskFormModel(id, taskService, model);
        return TASK_NEW;
    }

    @Secured("ROLE_USER")
    @RequestMapping(value = "/tasks/create", method = RequestMethod.GET)
    public String createTask(ModelMap model, SessionStatus status) {
        model.clear();
        status.setComplete();
        return "redirect:/tasks/new";
    }

    @Secured("ROLE_USER")
    @RequestMapping(value = "/tasks/new", method = RequestMethod.GET)
    public String createTask(ModelMap model) {
        if (!model.containsKey(Constants.NEW_TASK_FORM)) {
            model.addAttribute(Constants.NEW_TASK_FORM, new TaskForm());
        }
        TaskUtil.addTaskPrioritiesToModel(taskService, model);
        TaskUtil.addTaskFrequenciesToModel(taskService, model);
        TaskUtil.setupQualifiers(model);
        return TASK_NEW;
    }

    @Secured("ROLE_USER")
    @RequestMapping(value = "/tasks/quickTasks", method = RequestMethod.POST)
    public String handleCreateTask(@RequestParam("txtaTasks") String rawTasks, HttpSession session, ModelMap model, RedirectAttributes redirectAttributes, SessionStatus status) throws DataIntegrityException {
        String errorMessage = null;

        try {
            String[] taskAry = rawTasks.split("\n");
            for (String taskItem : taskAry) {
                String trimmedTask = taskItem.trim();
                if (trimmedTask.length() > 0) {
                    Task simpleTask = new Task();
                    simpleTask.setName(trimmedTask);
                    simpleTask.setUserId(getCurrentUserId());
                    taskService.createQuickTask(simpleTask);
                }
            }
        } catch (TaskServiceException e) {
            errorMessage = e.getMessage();
        }

        if (errorMessage != null) {

            redirectAttributes.addFlashAttribute(ERROR_MESSAGE, errorMessage);
            return "redirect:/action";
        }

        status.setComplete();
        return "redirect:/action";
    }


    @Secured("ROLE_USER")
    @RequestMapping(value = "/tasks/new", method = RequestMethod.POST)
    public String handleCreateTask(TaskForm taskForm, ModelMap model, RedirectAttributes redirectAttributes, SessionStatus status) throws DataIntegrityException {
        String errorMessage = null;
        Task task = new Task();
        try {

            processCreateForm(taskForm, task);
            if (!StringUtils.isEmpty(taskForm.getId())) {

                taskService.unCompleteTask(taskForm.getId());
                taskService.updateTask(task);
            } else {

                task = taskService.createTask(task);
            }
        } catch (ParseException e) {
            errorMessage = "Unable to process specified due date, please contact support";
        } catch (TaskUpdateException | DataIntegrityException | TaskServiceValidationException |
                TaskServiceException e) {
            errorMessage = e.getMessage();
        } catch(NumberFormatException e){
            errorMessage = "Unable to add reminder, please ensure reminder input is an Integer and not empty";
        }

        if (errorMessage != null) {
            redirectAttributes.addFlashAttribute(ERROR_MESSAGE, errorMessage);
            if (!model.containsAttribute(Constants.IS_EDIT)) {
                task.setId(null);
            }
            redirectAttributes.addFlashAttribute(Constants.NEW_TASK_FORM, TaskUtil.buildForm(task));
            return "redirect:/tasks/new";
        }

        status.setComplete();
        return "redirect:/tasks";
    }

    @Secured("ROLE_USER")
    @RequestMapping(value = "/tasks/uncomplete", method = RequestMethod.GET)
    public String uncompleteTask(IDForm idForm, HttpSession session, ModelMap model, RedirectAttributes redirectAttributes, SessionStatus status) throws DataIntegrityException {
        String errorMessage = null;
        try {
            Task task = taskService.getTask(idForm.getId());
            if (task != null) {

                taskService.unCompleteTask(idForm.getId());
            }
        } catch (TaskServiceException e) {
            errorMessage = e.getMessage();
        }

        if (errorMessage != null) {

            redirectAttributes.addFlashAttribute(ERROR_MESSAGE, errorMessage);
        }

        status.setComplete();
        return REDIRECT_DONE_TASKS;
    }


    private void processCreateForm(TaskForm taskForm, Task task) throws ParseException, DataIntegrityException {
        if (!StringUtils.isEmpty(taskForm.getId())) {
            task.setId(taskForm.getId());
        }
        task.setUserId(getCurrentUserId());
        task.setDescription(taskForm.getDescription());
        if (!StringUtils.isEmpty(taskForm.getDueDate())) {
            task.setDueDate(DateFormatUtil.parseDate(taskForm.getDueDate()));
        }
        task.setName(taskForm.getName());
        TaskPriority priority = getPriority(taskForm.getPriority());
        task.setPriority(priority);
        List<Tag> taskTags = new ArrayList<>();
        if (taskForm.getTaskTags() != null) {
            for (String tag : taskForm.getTaskTags()) {
                Tag taskTag = new Tag();
                taskTag.setName(tag);
                taskTag.setUserId(getCurrentUserId());
                taskTags.add(taskTag);
            }
        }
        task.setTaskTags(taskTags);
        task.setCompleted(task.isCompleted());
        List<TaskReminder> reminders = new ArrayList<>();
        for (TaskReminderForm taskReminderForm : taskForm.getReminders()) {
            TaskReminder taskReminder = new TaskReminder();
            if (taskReminderForm.getQuantifier() != null && taskReminderForm.getQualifier() != null && !StringUtils.isEmpty(taskReminderForm.getQuantifier())) {
                taskReminder.setQuantifier(Integer.parseInt(taskReminderForm.getQuantifier()));
                taskReminder.setQualifier(TaskReminderQualifier.getQualifier(Integer.parseInt(taskReminderForm.getQualifier())));
                reminders.add(taskReminder);
            }
        }
        task.setReminders(reminders);
    }



    public TaskPriority getPriority(String id) {
        List<TaskPriority> priorities = taskService.getPriorities();
        for (TaskPriority priority : priorities) {
            if (priority.getId().equals(id)) {
                return priority;
            }
        }
        return null;
    }

    public TaskNotification getFrequency(String id) {
        List<TaskNotification> frequencies = taskService.getFrequencies();
        for (TaskNotification frequency : frequencies) {
            if (frequency.getId().equals(id)) {
                return frequency;
            }
        }
        return null;
    }
}
