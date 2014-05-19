package com.taskstrategy.web.controller;

import com.taskstrategy.business.api.UserService;
import com.taskstrategy.commons.domain.BaseDomain;
import com.taskstrategy.commons.domain.User;
import com.taskstrategy.data.api.exception.UserUpdateException;
import com.taskstrategy.web.controller.forms.ResetPasswordForm;
import com.taskstrategy.web.controller.forms.UserForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.validation.groups.Default;
import java.util.HashSet;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: brian
 * Date: 11/2/13
 * Time: 11:12 PM
 * To change this template use File | Settings | File Templates.
 */
@Controller
public class LoginController {

    private static final String REDIRECT_SIGNUP = "redirect:/signup";
    private static final String SIGNUP = "signup";
    private static final String INDEX = "index";
    private static final String NEW_USER = "newUser";
    private static final String ERROR_MESSAGE = "errorMessage";
    private static final String SUCCESS_MESSAGE = "successMessage";
    private static final String RESET_PASSWORD_FORM = "resetPasswordForm";
    private static final String RESETPASSWORD = "resetpassword";
    private static final String FORGOTPASSWORD = "forgotpassword";
    private static final String FORGOT_PASSWORD_FORM = "forgotPasswordForm";

    @Autowired
    private ShaPasswordEncoder passwordEncoder;
    @Autowired
    private UserService userService;
    @Autowired
    private Validator validator;

    private <T extends BaseDomain> void checkForValidationViolations(T itemToCheck) throws UserUpdateException {
        Set<ConstraintViolation<T>> violationSet = validator.validate(itemToCheck, Default.class);
        String message = "";
        if (!violationSet.isEmpty()) {
            Set<String> messages = new HashSet<>();
            for (ConstraintViolation<?> violation : violationSet) {
                if (messages.add(violation.getMessage())) {
                    message += "<br>" + violation.getMessage();
                }
            }
            throw new UserUpdateException(message);
        }
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(ModelMap model) {
        return INDEX;
    }

    @RequestMapping(value = "/loginfailed", method = RequestMethod.GET)
    public String loginerror(ModelMap model) {
        model.addAttribute("error", "true");
        return INDEX;

    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(ModelMap model, SessionStatus sessionStatus) {
        return INDEX;
    }

    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public String signup(ModelMap model) {
        if (!model.containsKey(NEW_USER)) {
            model.addAttribute(NEW_USER, new UserForm());
        }
        return SIGNUP;
    }

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public String signupHandle(UserForm userForm, HttpSession session, ModelMap model, RedirectAttributes redirectAttributes, SessionStatus status) {
        Set<ConstraintViolation<UserForm>> violationSet = validator.validate(userForm, Default.class);
        if (!violationSet.isEmpty()) {
            StringBuilder messageBuilder = new StringBuilder("Unable to register user due to: ");
            for (ConstraintViolation<UserForm> userFormConstraintViolation : violationSet) {
                messageBuilder.append("<br />").append(userFormConstraintViolation.getMessage());
            }
            redirectAttributes.addFlashAttribute(ERROR_MESSAGE, messageBuilder.toString());
            return REDIRECT_SIGNUP;
        } else {
            User user = new User(userForm.getEmail(), passwordEncoder.encodePassword(userForm.getPassword(), null));
            try {
                userService.createUser(user);
                model.addAttribute("showLogin", "true");
                model.addAttribute("email",userForm.getEmail());
            } catch (UserUpdateException e) {
                redirectAttributes.addFlashAttribute(ERROR_MESSAGE, e.getMessage());
                return REDIRECT_SIGNUP;
            }
        }
        return INDEX;
    }


    @RequestMapping(value = "/forgot-password", method = RequestMethod.GET)
    public String resetPassword(Model model) {
        model.addAttribute(FORGOT_PASSWORD_FORM, new UserForm());
        return FORGOTPASSWORD;
    }

    @RequestMapping(value = "/forgot-password", method = RequestMethod.POST)
    public String resetPasswordPost(UserForm passwordForm, RedirectAttributes redirectAttributes) {
        userService.sendPasswordReset(passwordForm.getEmail());
        redirectAttributes.addFlashAttribute(SUCCESS_MESSAGE, "Thank you. Your password will be sent to your email address momentarily.");
        redirectAttributes.addFlashAttribute(RESET_PASSWORD_FORM, passwordForm);
        return "redirect:/forgot-password";
    }

    @RequestMapping(value = "/resetpassword/{resetId}", method = RequestMethod.GET)
    public String resetPassword(@PathVariable(value = "resetId") String resetId, Model model) {
        ResetPasswordForm form = new ResetPasswordForm();
        String userId = userService.getUserIdForPasswordReset(resetId);
        if (userId == null) {
            return "redirect:/";
        }
        form.setUserId(userId);
        form.setResetId(resetId);
        model.addAttribute(RESET_PASSWORD_FORM, form);
        return RESETPASSWORD;
    }

    @RequestMapping(value = "/resetpassword", method = RequestMethod.POST)
    public String resetPasswordPOST(ResetPasswordForm form, RedirectAttributes redirectAttributes) {
        Set<ConstraintViolation<ResetPasswordForm>> violationSet = validator.validate(form, Default.class);
        if (!violationSet.isEmpty()) {
            StringBuilder messageBuilder = new StringBuilder("Unable to update user due to: ");
            for (ConstraintViolation<ResetPasswordForm> userFormConstraintViolation : violationSet) {
                messageBuilder.append("<br />").append(userFormConstraintViolation.getMessage());
            }
            redirectAttributes.addFlashAttribute(ERROR_MESSAGE, messageBuilder.toString());
            return "redirect:/resetpassword/" + form.getResetId();
        } else {
            if (form.getPassword().equals(form.getConfirmPassword())) {
                userService.updatePasswordByUsername(form.getUserId(), passwordEncoder.encodePassword(form.getPassword(), null));
                form.setPassword("");
                form.setConfirmPassword("");
                redirectAttributes.addFlashAttribute(SUCCESS_MESSAGE, "User updated successfully.");
                redirectAttributes.addFlashAttribute(ERROR_MESSAGE, null);
                redirectAttributes.addFlashAttribute(RESET_PASSWORD_FORM, form);
            } else {
                form.setPassword("");
                form.setConfirmPassword("");
                redirectAttributes.addFlashAttribute(SUCCESS_MESSAGE, null);
                redirectAttributes.addFlashAttribute(ERROR_MESSAGE, "Passwords Must Match");
                redirectAttributes.addFlashAttribute(RESET_PASSWORD_FORM, form);
                return "redirect:/resetpassword/" + form.getResetId();
            }
        }
        return "redirect:/";
    }
}