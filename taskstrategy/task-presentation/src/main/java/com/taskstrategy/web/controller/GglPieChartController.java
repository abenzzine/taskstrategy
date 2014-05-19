package com.taskstrategy.web.controller;

import com.taskstrategy.business.api.TaskService;
import com.taskstrategy.commons.domain.ChartTaskData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created with IntelliJ IDEA.
 * User: Latif
 * Date: 01/15/14
 * Time: 7:27 PM
 * To change this template use File | Settings | File Templates.
 */

@Controller
@RequestMapping("/tasks")
public class GglPieChartController extends AbstractController {

    @Autowired
    TaskService taskService;


    @RequestMapping(value = "/report", method = RequestMethod.GET)
    public String ChartsData(ModelMap model) {

        ChartTaskData taskChartCounts = taskService.getTaskChartCounts(getCurrentUserId());

        model.addAttribute("Due_Today", taskChartCounts.getOpenDueTodayCount());
        model.addAttribute("Due_This_Week", taskChartCounts.getOpenDueThisWeekCount());
        model.addAttribute("Due_This_Month", taskChartCounts.getOpenDueThisMonthCount());
        model.addAttribute("Completed_Today", taskChartCounts.getCompletedTodayCount());
        model.addAttribute("Completed_This_Week", taskChartCounts.getCompletedThisWeekCount());
        model.addAttribute("Completed_This_Month", taskChartCounts.getCompletedThisMonthCount());
        model.addAttribute("Completed_Early_Today", taskChartCounts.getCompletedEarlyTodayCount());
        model.addAttribute("Completed_Early_This_Week", taskChartCounts.getCompletedEarlyThisWeekCount());
        model.addAttribute("Completed_Early_This_Month", taskChartCounts.getCompletedEarlyThisMonthCount());
        model.addAttribute("Past_Due_Today", taskChartCounts.getPastDueTodayCount());
        model.addAttribute("Past_Due_This_Week", taskChartCounts.getPastDueThisWeekCount());
        model.addAttribute("Past_Due_This_Month", taskChartCounts.getPastDueThisMonthCount());
        model.addAttribute("Low_Priority_Today", taskChartCounts.getLowDueTodayCount());
        model.addAttribute("Low_Priority_This_Week", taskChartCounts.getLowDueThisWeekCount());
        model.addAttribute("Low_Priority_This_Month", taskChartCounts.getLowDueThisMonthCount());
        model.addAttribute("Med_Priority_Today", taskChartCounts.getMedDueTodayCount());
        model.addAttribute("Med_Priority_This_Week", taskChartCounts.getMedDueThisWeekCount());
        model.addAttribute("Med_Priority_This_Month", taskChartCounts.getMedDueThisMonthCount());
        model.addAttribute("High_Priority_Today", taskChartCounts.getHighDueTodayCount());
        model.addAttribute("High_Priority_This_Week", taskChartCounts.getHighDueThisWeekCount());
        model.addAttribute("High_Priority_This_Month", taskChartCounts.getHighDueThisMonthCount());
        model.addAttribute("Critical_Priority_Today", taskChartCounts.getCriticalDueTodayCount());
        model.addAttribute("Critical_Priority_This_Week", taskChartCounts.getCriticalDueThisWeekCount());
        model.addAttribute("Critical_Priority_This_Month", taskChartCounts.getCriticalDueThisMonthCount());
        return "report";

    }

}
