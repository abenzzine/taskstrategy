package com.taskstrategy.web.controller;

import com.taskstrategy.business.api.TaskService;
import com.taskstrategy.business.api.EmailService;
import com.taskstrategy.business.service.EmailServiceImpl;
import com.taskstrategy.web.controller.forms.ContactForm;
import com.taskstrategy.data.api.TaskDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import javax.validation.ConstraintViolation;
import javax.validation.groups.Default;
import javax.validation.Validator;
import java.util.Set;

/**
 * This class is just a controller to make sure that Spring MVC framework is setup properly.
 */
@Controller
public class MainController {

    private static final String CONTACT_FORM = "contactForm";
    private static final String ERROR_MESSAGE = "errorMessage";
    private static final String SUCCESS_MESSAGE = "successMessage";

    @Autowired
    private TaskService taskService;
    @Autowired
    private Validator validator;
    private EmailService emailService = new EmailServiceImpl();

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String home(ModelMap model) {
        return "index";
    }

    @RequestMapping(value = "/about", method = RequestMethod.GET)
    public String about(ModelMap model) {
        return "about";
    }

    @RequestMapping(value = "/contact", method = RequestMethod.GET)
    public String getContactView(ModelMap model) {
        ContactForm contactForm = new ContactForm();
        model.addAttribute(CONTACT_FORM, contactForm);
        return "contact";
    }

    @RequestMapping(value = "/privacy-policy", method = RequestMethod.GET)
    public String privacyPolicy(ModelMap model) {
        return "privacyPolicy";
    }

    @RequestMapping(value = "/terms-of-service", method = RequestMethod.GET)
    public String termsOfService(ModelMap model) {
        return "termsOfService";
    }

    @RequestMapping(value = "/contact", method = RequestMethod.POST)
    public String postContact(ContactForm contactForm, RedirectAttributes redirectAttributes) {
        Set<ConstraintViolation<ContactForm>> violationSet = validator.validate(contactForm, Default.class);
        if (!violationSet.isEmpty()) {
            StringBuilder messageBuilder = new StringBuilder("Unable to send message due to: ");
            for (ConstraintViolation<ContactForm> contactFormConstraintViolation : violationSet) {
                messageBuilder.append("<br />").append(contactFormConstraintViolation.getMessage());
            }
            redirectAttributes.addFlashAttribute(ERROR_MESSAGE, messageBuilder.toString());
        }
        else {
            emailService.send("taskstrategy@taskstrategy.com", "TaskStrategy Contact Form Sent", "Contact form sent from " + contactForm.getEmail() + " (" + contactForm.getName() + "). Comments: " + contactForm.getComments());
            redirectAttributes.addFlashAttribute(SUCCESS_MESSAGE, "Thank you. Your message has been sent. We will contact you as soon as we can.");
            redirectAttributes.addFlashAttribute(ERROR_MESSAGE, null);
            redirectAttributes.addFlashAttribute(CONTACT_FORM, contactForm);
        }
        return "redirect:/contact";
    }
}
