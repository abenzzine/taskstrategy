package com.taskstrategy.web.controller.forms;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Size;
import javax.validation.groups.Default;

public class ContactForm {

    @NotBlank(message = "Name cannot be blank.", groups = Default.class)
    private String name;
    @Email(message = "Email must be a valid email address.", groups = Default.class)
    @NotBlank(message = "Email must be a valid email address.", groups = Default.class)
    @Size(max = 45, message = "Email must be less than 45 characters.", groups = Default.class)
    private String email;
    @NotBlank(message = "Comments cannot be blank.", groups = Default.class)
    private String comments;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}
