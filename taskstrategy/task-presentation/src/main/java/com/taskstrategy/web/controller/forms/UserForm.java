package com.taskstrategy.web.controller.forms;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Size;
import javax.validation.groups.Default;

/**
 * Created with IntelliJ IDEA.
 * User: brian
 * Date: 11/4/13
 * Time: 7:44 PM
 * To change this template use File | Settings | File Templates.
 */
public class UserForm {

    @Email(message = "Email must be a valid email address.", groups = Default.class)
    @NotBlank(message = "Email must be a valid email address.", groups = Default.class)
    @Size(max = 45, message = "Email must be less than 45 characters.", groups = Default.class)
    private String email;
    @NotBlank(message = "Password cannot be blank.", groups = Default.class)
    @Size(min = 5, message = "Password cannot be less than 5 characters.", groups = Default.class)
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
