package com.taskstrategy.web.controller;

import com.taskstrategy.business.api.TagService;
import com.taskstrategy.business.api.TaskService;
import com.taskstrategy.business.api.UserService;
import com.taskstrategy.commons.domain.User;
import com.taskstrategy.data.api.exception.UserUpdateException;
import com.taskstrategy.web.controller.forms.ResetPasswordForm;
import com.taskstrategy.web.controller.forms.UserForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.validation.groups.Default;
import java.util.Set;

/**
 * Created by brian on 1/16/14.
 */
@Controller
public class ProfileController extends AbstractController {

    private static final String EDIT_USER_FORM = "editProfileForm";
    private static final String PROFILE = "profile";
    private static final String ERROR_MESSAGE = "errorMessage";
    private static final String SUCCESS_MESSAGE = "successMessage";
    private static final String RESET_PASSWORD_FORM = "resetPasswordForm";
    @Autowired
    private UserService userService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private TagService tagService;
    @Autowired
    private Validator validator;
    @Autowired
    private ShaPasswordEncoder passwordEncoder;


    @Secured("ROLE_USER")
    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public String home(HttpSession session, ModelMap model) {
        User user = getCurrentUser();
        UserForm profileForm = new UserForm();
        profileForm.setEmail(user.getUsername());
        model.addAttribute(EDIT_USER_FORM, profileForm);
        model.addAttribute(RESET_PASSWORD_FORM, new ResetPasswordForm());
        return PROFILE;
    }

    @Secured("ROLE_USER")
    @RequestMapping(value = "/profile/delete", method = RequestMethod.POST)
    public String deleteProfle(HttpSession session, ModelMap model, SessionStatus sessionStatus) {
        taskService.deleteTasks(getCurrentUserId());
        tagService.deleteTaskTags(getCurrentUserId());
        tagService.deleteTags(getCurrentUserId());
        userService.deleteAccount(getCurrentUserId());

        sessionStatus.setComplete();
        session.invalidate();
        return "redirect:/";
    }

    @Secured("ROLE_USER")
    @RequestMapping(value = "/profile/update", method = RequestMethod.POST)
    public String updateProfile(UserForm form, RedirectAttributes redirectAttributes) {
        form.setPassword("fakePassword");
        Set<ConstraintViolation<UserForm>> violationSet = validator.validate(form, Default.class);
        if (!violationSet.isEmpty()) {
            StringBuilder messageBuilder = new StringBuilder("Unable to update user due to: ");
            for (ConstraintViolation<UserForm> userFormConstraintViolation : violationSet) {
                messageBuilder.append("<br />").append(userFormConstraintViolation.getMessage());
            }
            redirectAttributes.addFlashAttribute(ERROR_MESSAGE, messageBuilder.toString());
        } else {
            String errorMessage = null;
            try {
                userService.updateUsername(getCurrentUserId(), form.getEmail());
            } catch (UserUpdateException e) {
                errorMessage = e.getMessage();
            }
            if (errorMessage != null) {
                redirectAttributes.addFlashAttribute(ERROR_MESSAGE, errorMessage);
                redirectAttributes.addFlashAttribute(EDIT_USER_FORM, form);
                redirectAttributes.addFlashAttribute(SUCCESS_MESSAGE, null);
            } else {
                getCurrentUser().setUsername(form.getEmail());
                redirectAttributes.addFlashAttribute(SUCCESS_MESSAGE, "User updated successfully.");
                redirectAttributes.addFlashAttribute(ERROR_MESSAGE, null);
                redirectAttributes.addFlashAttribute(EDIT_USER_FORM, form);
            }
        }
        return "redirect:/" + PROFILE;
    }

    @Secured("ROLE_USER")
    @RequestMapping(value = "/profile/updatepassword", method = RequestMethod.POST)
    public String updatePassword(ResetPasswordForm form, RedirectAttributes redirectAttributes) {
        Set<ConstraintViolation<ResetPasswordForm>> violationSet = validator.validate(form, Default.class);
        if (!violationSet.isEmpty()) {
            StringBuilder messageBuilder = new StringBuilder("Unable to update user due to: ");
            for (ConstraintViolation<ResetPasswordForm> userFormConstraintViolation : violationSet) {
                messageBuilder.append("<br />").append(userFormConstraintViolation.getMessage());
            }
            redirectAttributes.addFlashAttribute(ERROR_MESSAGE, messageBuilder.toString());
        } else {
            if (form.getPassword().equals(form.getConfirmPassword())) {
                try {
                    userService.updatePassword(getCurrentUserId(), passwordEncoder.encodePassword(form.getPassword(), null));
                    form.setPassword("");
                    form.setConfirmPassword("");
                    redirectAttributes.addFlashAttribute(SUCCESS_MESSAGE, "User updated successfully.");
                    redirectAttributes.addFlashAttribute(ERROR_MESSAGE, null);
                    redirectAttributes.addFlashAttribute("resetPasswordForm", form);
                } catch (UserUpdateException e) {
                    form.setPassword("");
                    form.setConfirmPassword("");
                    redirectAttributes.addFlashAttribute(SUCCESS_MESSAGE, null);
                    redirectAttributes.addFlashAttribute(ERROR_MESSAGE, e.getMessage());
                    redirectAttributes.addFlashAttribute("resetPasswordForm", form);
                }
            } else {
                form.setPassword("");
                form.setConfirmPassword("");
                redirectAttributes.addFlashAttribute(SUCCESS_MESSAGE, null);
                redirectAttributes.addFlashAttribute(ERROR_MESSAGE, "Passwords Must Match");
                redirectAttributes.addFlashAttribute(RESET_PASSWORD_FORM, form);
            }
        }
        return "redirect:/" + PROFILE;
    }


}
