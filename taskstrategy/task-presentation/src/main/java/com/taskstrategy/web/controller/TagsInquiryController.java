package com.taskstrategy.web.controller;

import com.taskstrategy.business.api.TagService;
import com.taskstrategy.business.api.exception.TaskServiceException;
import com.taskstrategy.commons.domain.Tag;
import com.taskstrategy.commons.domain.Task;
import com.taskstrategy.data.api.exception.TaskUpdateException;
import com.taskstrategy.web.config.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;


/**
 * This class is just a controller to support the task view page
 */
@Controller
public class TagsInquiryController extends AbstractController{

    private static final String ERROR_MESSAGE = "errorMessage";
    private static final String TAG_VIEW = "tagView";
    private static final String TAGS = "tags";
    private static final String SUCCESS_MESSAGE = "successMessage";
    private static final String REDIRECT_TAGS = "redirect:/tags";

    @Autowired
    private TagService tagService;

    @Secured("ROLE_USER")
    @RequestMapping(value = "/tags", method = RequestMethod.GET)
    public String home(HttpSession session, ModelMap model) {

        List<Tag> tagList = tagService.getTags(getCurrentUserId());
        model.addAttribute(TAGS, tagList);
        return TAG_VIEW;
    }

    @Secured("ROLE_USER")
    @RequestMapping(value = "/tags/delete/{name}", method = RequestMethod.GET)
    public String deleteTask(@PathVariable(value = "name") String name, HttpSession session, ModelMap model, RedirectAttributes redirectAttributes, SessionStatus status) {
        String errorMessage = null;
        try {
            tagService.deleteTag(name, getCurrentUserId());
            redirectAttributes.addFlashAttribute(SUCCESS_MESSAGE, name + " successfully deleted.");
        } catch (TaskServiceException e) {
            errorMessage = e.getMessage();
        }
        if (errorMessage != null) {
            redirectAttributes.addFlashAttribute(ERROR_MESSAGE, errorMessage);
        }
        status.setComplete();
        return REDIRECT_TAGS;
    }
}
