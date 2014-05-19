package com.taskstrategy.web.controller;

import com.taskstrategy.business.api.TaskService;
import com.taskstrategy.commons.domain.Task;
import com.taskstrategy.commons.domain.TaskSet;
import com.taskstrategy.commons.domain.User;
import com.taskstrategy.web.config.Constants;
import com.taskstrategy.web.util.TaskUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * This class manages behavior related to action mode.
 */
@Controller
public class ActionModeController extends AbstractController {

    @Autowired
    private TaskService taskService;

    @Secured("ROLE_USER")
    @RequestMapping(value = "/action", method = RequestMethod.GET)
    public String initializeTaskSet(HttpSession session, ModelMap model) {

        int noOfTasksToInclude = 5;
        TaskSet taskSet = (TaskSet) session.getAttribute(Constants.TASK_SET);

        if (taskSet == null)
        {
            taskSet = getNewTaskSet(noOfTasksToInclude);
            taskSet.next();
            session.setAttribute(Constants.TASK_SET, taskSet);
        }
        model.addAttribute(Constants.TASK_SET, taskSet);
        setupTaskForm(taskSet, taskService, model);
        return Constants.ACTION_MODE_VIEW;
    }

    private TaskSet getNewTaskSet(int noOfTasksToInclude) {
        User currentUser = getCurrentUser();
        return new TaskSet(taskService.getTaskSet(currentUser.getId(), noOfTasksToInclude));
    }

    @Secured("ROLE_USER")
    @RequestMapping(value = "/create_subtasks", method = RequestMethod.POST)
    public String createSubtasks(@RequestParam("taskId") String taskId, @RequestParam("subtasks") String subtaskText, HttpSession session, ModelMap model) {
        String nextView = "redirect:/" + Constants.ACTION_MODE_VIEW +"/";
        String subtasks[] = subtaskText.split("\n");
        List<Task> subTasksToCreate = new LinkedList<Task>();

        for (String s : subtasks)
        {
            String taskText = s.trim();
            if (taskText.length() == 0)
            {
                continue;
            }

            Task t = new Task();
            t.setName(taskText);
            subTasksToCreate.add(t);
        }

        try {
            TaskSet taskSet = (TaskSet) session.getAttribute(Constants.TASK_SET);
            taskService.createSubtasks(taskId, subTasksToCreate);
            taskSet.next();
            setupTaskForm(taskSet, taskService, model);

        } catch (Exception e) {
            e.printStackTrace();
        }

        model.addAttribute(Constants.TASK_SET, session.getAttribute(Constants.TASK_SET));
        return nextView;
    }

    @Secured("ROLE_USER")
    @RequestMapping(value = "/action/take_action", method = RequestMethod.POST)
    public String processAction(@RequestParam("actionType") String actionType,
            @RequestParam("taskId") String taskId, HttpSession session, ModelMap model) {
        String nextView = Constants.ACTION_MODE_VIEW;
        TaskSet taskSet = (TaskSet) session.getAttribute(Constants.TASK_SET);
        switch (actionType)
        {
            case "redisplay":
                break;
            case "complete":
                try {
                    taskService.completeTask(taskId);
                    taskSet.next();
                    setupTaskForm(taskSet, taskService, model);
                } catch (Exception e) {
                    e.printStackTrace();}
                break;
            case "pass":
                try {
                    Task currentTask = taskService.getTask(taskId);
                    currentTask.incrementPassCount();
                    taskService.updateTask(currentTask);
                    taskSet.next();
                    setupTaskForm(taskSet, taskService, model);

                } catch (Exception e) {
                    e.printStackTrace();}
                break;
            case "delete":
                try {
                    taskService.deleteTask(taskId);
                    taskSet.next();
                    setupTaskForm(taskSet, taskService, model);
                } catch (Exception e) {
                    e.printStackTrace();}
                break;
            case "breakdown":
                try {
                    nextView = Constants.BREAKDOWN_VIEW;
                } catch (Exception e) {
                    e.printStackTrace();}
                break;
            case "stuck":
                try {
                    nextView = Constants.STUCK_VIEW;
                } catch (Exception e) {
                    e.printStackTrace();}
                break;
            default:
                throw new UnsupportedOperationException("Action Type of " + actionType + " is not supported.");

        }
        model.addAttribute(Constants.TASK_SET, taskSet);
        return nextView;
    }

    private static void setupTaskForm(TaskSet taskSet, TaskService taskService, ModelMap model)
    {
        Task activeTask = taskSet.getActiveTask();
        if (activeTask != null)
        {
            TaskUtil.setupTaskFormModel(activeTask.getId(), taskService, model);
        }
    }
}
