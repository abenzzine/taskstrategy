package com.taskstrategy.web.controller;

import com.taskstrategy.business.api.TagService;
import com.taskstrategy.business.api.TaskService;
import com.taskstrategy.business.api.exception.TaskServiceException;
import com.taskstrategy.commons.domain.ChartTaskData;
import com.taskstrategy.commons.domain.Tag;
import com.taskstrategy.commons.domain.Task;
import com.taskstrategy.data.api.exception.TaskUpdateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;


/**
 * This class is just a controller to support the task view page
 */
@Controller
@SessionAttributes("actionModeNotification")
public class TaskInquiryController extends AbstractController {

    private static final String ERROR_MESSAGE = "errorMessage";
    private static final String TASK_VIEW = "taskView";
    private static final String TASKS = "tasks";
    private static final String DONE_TASKS = "done";
    private static final String SUCCESS_MESSAGE = "successMessage";
    private static final String REDIRECT_TASKS = "redirect:/tasks";
    private static final String REDIRECT_DONE_TASKS = "redirect:/done";
    private static final String FAVORITE_TAGS = "favoriteTags";
    private static final String TASK_DASHBOARD = "taskDashboard";

    @Autowired
    private TaskService taskService;
    @Autowired
    private TagService tagService;


    @Secured("ROLE_USER")
    @RequestMapping(value = "/dashboard", method = RequestMethod.GET)
    public String dashboard(HttpSession session, ModelMap model) {
        if (!model.containsAttribute("actionModeNotification")) {
            model.addAttribute("actionModeNotification", true);
        } else {
            model.addAttribute("actionModeNotification", false);
        }
        ChartTaskData taskData = taskService.getTaskChartCounts(getCurrentUserId());
        List<Task> taskList = taskService.getTasks(getCurrentUserId());
        model.addAttribute(TASKS, taskList);
        model.addAttribute("pastDueCount", taskData.getPastDueTodayCount());
        model.addAttribute("dueTodayCount", taskData.getOpenDueTodayCount());
        model.addAttribute("dueWeekCount", taskData.getOpenDueThisWeekCount());
        model.addAttribute("completedThisWeek", taskData.getCompletedThisWeekCount());
        return TASK_DASHBOARD;
    }

    @Secured("ROLE_USER")
    @RequestMapping(value = "/tasks", method = RequestMethod.GET)
    public String home(HttpSession session, ModelMap model, @RequestParam(value = "tag", required = false) String tag) {
        if (StringUtils.isEmpty(tag)) {
            List<Task> taskList = taskService.getTasks(getCurrentUserId());
            model.addAttribute(TASKS, taskList);
            model.addAttribute("searchTag", null);
            model.addAttribute("subtitle", "");
        } else {
            List<Task> taskList = taskService.getTasksByTag(getCurrentUserId(), tag);
            model.addAttribute(TASKS, taskList);
            model.addAttribute("searchTag", tag);
            model.addAttribute("subtitle", "Tag: " + tag);
        }

        return TASK_VIEW;
    }

    @Secured("ROLE_USER")
    @RequestMapping(value = "/tasks/today", method = RequestMethod.GET)
    public String taskListToday(HttpSession session, ModelMap model) {
        List<Task> taskList = taskService.getTasksDueToday(getCurrentUserId());
        model.addAttribute(TASKS, taskList);
        model.addAttribute("subtitle", "Due Today");
        return TASK_VIEW;
    }

    @Secured("ROLE_USER")
    @RequestMapping(value = "/tasks/week", method = RequestMethod.GET)
    public String taskListWeek(HttpSession session, ModelMap model) {
        List<Task> taskList = taskService.getTasksDueThisWeek(getCurrentUserId());
        model.addAttribute(TASKS, taskList);
        model.addAttribute("subtitle", "Due This Week");
        return TASK_VIEW;
    }

    @Secured("ROLE_USER")
    @RequestMapping(value = "/tasks/month", method = RequestMethod.GET)
    public String taskListMonth(HttpSession session, ModelMap model) {
        List<Task> taskList = taskService.getTasksDueThisMonth(getCurrentUserId());
        model.addAttribute(TASKS, taskList);
        model.addAttribute("subtitle", "Due This Week");
        return TASK_VIEW;
    }

    @Secured("ROLE_USER")
    @RequestMapping(value = "/tasks/pastdue", method = RequestMethod.GET)
    public String pastDueTasks(HttpSession session, ModelMap model) {
        List<Task> taskList = taskService.getPastDueTasks(getCurrentUserId());
        model.addAttribute(TASKS, taskList);
        model.addAttribute("subtitle", "Past Due");
        return TASK_VIEW;
    }


    @Secured("ROLE_USER")
    @RequestMapping(value = "/done", method = RequestMethod.GET)
    public String complete(HttpSession session, ModelMap model, @RequestParam(value = "tag", required = false) String tag) {
        if (StringUtils.isEmpty(tag)) {
            List<Task> taskList = taskService.getDoneTasksByUser(getCurrentUserId());
            model.addAttribute(DONE_TASKS, taskList);
        } else {
            List<Task> taskList = taskService.getTasksByTag(getCurrentUserId(), tag);
            model.addAttribute(DONE_TASKS, taskList);
        }
        return DONE_TASKS;
    }

    @Secured("ROLE_USER")
    @RequestMapping(value = "/tasks/delete/{id}", method = RequestMethod.GET)
    public String deleteTask(@PathVariable(value = "id") String id, HttpSession session, ModelMap model, RedirectAttributes redirectAttributes, SessionStatus status) {
        String errorMessage = null;
        Task task = taskService.getTask(id);
        try {
            taskService.deleteTask(id);
            redirectAttributes.addFlashAttribute(SUCCESS_MESSAGE, "Task " + task.getName() + " successfully deleted.");
        } catch (TaskUpdateException | TaskServiceException e) {
            errorMessage = e.getMessage();
        }
        if (errorMessage != null) {
            redirectAttributes.addFlashAttribute(ERROR_MESSAGE, errorMessage);
        }
        status.setComplete();
        if (task.isCompleted()) {
            return REDIRECT_DONE_TASKS;
        }

        return REDIRECT_TASKS;
    }

    @Secured("ROLE_USER")
    @RequestMapping(value = "/done/{id}", method = RequestMethod.GET)
    public String completeTask(@PathVariable(value = "id") String id, HttpSession session, ModelMap model, RedirectAttributes redirectAttributes, SessionStatus status) {
        String errorMessage = null;
        try {
            Task task = taskService.getTask(id);
            taskService.completeTask(task.getId());
            redirectAttributes.addFlashAttribute(SUCCESS_MESSAGE, "Task " + task.getName() + " successfully completed.");
        } catch (Exception e) {
            errorMessage = e.getMessage();
        }
        if (errorMessage != null) {
            redirectAttributes.addFlashAttribute(ERROR_MESSAGE, errorMessage);
        }
        status.setComplete();
        return REDIRECT_TASKS;
    }
}
