package com.taskstrategy.web.controller;

import com.taskstrategy.business.api.TagService;
import com.taskstrategy.business.api.exception.TaskServiceException;
import com.taskstrategy.business.api.exception.TaskServiceValidationException;
import com.taskstrategy.commons.domain.Tag;
import com.taskstrategy.commons.domain.Task;
import com.taskstrategy.commons.domain.TaskPriority;
import com.taskstrategy.commons.util.DateFormatUtil;
import com.taskstrategy.data.api.exception.DataIntegrityException;
import com.taskstrategy.data.api.exception.TaskUpdateException;
import com.taskstrategy.web.controller.forms.TagForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
public class TagController extends AbstractController {

    private static final String NEW_TAG_FORM = "newTagForm";
    private static final String ERROR_MESSAGE = "errorMessage";
    private static final String TAG_EDIT = "tagEdit";

    @Autowired
    private TagService tagService;

    @Secured("ROLE_USER")
    @RequestMapping(value = "/tag/edit/{name}", method = RequestMethod.GET)
    public String editTask(@PathVariable(value = "name") String name, HttpSession session, ModelMap model) {
        Tag tag = tagService.getTag(name, getCurrentUserId());
        model.addAttribute(NEW_TAG_FORM, buildForm(tag));
        return TAG_EDIT;
    }

    @Secured("ROLE_USER")
    @RequestMapping(value = "/tag/update", method = RequestMethod.POST)
    public String handleCreateTask(TagForm tagForm, HttpSession session, ModelMap model, RedirectAttributes redirectAttributes, SessionStatus status) {
        String errorMessage = null;
        Tag tag = new Tag();
        tag.setUserId(getCurrentUserId());
        try {
            processUpdateForm(tagForm, tag);
            tagService.updateTag(tag);
        } catch (TaskUpdateException e) {
            errorMessage = e.getMessage();
        }

        if (errorMessage != null) {
            redirectAttributes.addFlashAttribute(ERROR_MESSAGE, errorMessage);
            redirectAttributes.addFlashAttribute(NEW_TAG_FORM, buildForm(tag));
            return "redirect:/tag/edit/" + tag.getName();
        }

        return "redirect:/tags";
    }

    private void processUpdateForm(TagForm tagForm, Tag tag) {
        tag.setName(tagForm.getName());
        tag.setFavorite(tagForm.isFavorite());
        tag.setDisplayColor(tagForm.getColor());
    }

    private TagForm buildForm(Tag tag) {
        TagForm tagForm = new TagForm();
        tagForm.setName(tag.getName());
        tagForm.setFavorite(tag.isFavorite());
        tagForm.setColor(tag.getDisplayColor());
        return tagForm;
    }
}
