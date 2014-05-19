package com.taskstrategy.web.controller.forms;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Size;
import javax.validation.groups.Default;

/**
 * Created by brian on 1/16/14.
 */
public class ResetPasswordForm {

    private String resetId;
    private String userId;
    @NotBlank(message = "Password cannot be blank.", groups = Default.class)
    @Size(min = 5, message = "Password cannot be less than 5 characters.", groups = Default.class)
    private String password;
    @NotBlank(message = "Confirm Password cannot be blank.", groups = Default.class)
    @Size(min = 5, message = "Confirm cannot be less than 5 characters.", groups = Default.class)
    private String confirmPassword;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getPassword() {

        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getResetId() {
        return resetId;
    }

    public void setResetId(String resetId) {
        this.resetId = resetId;
    }
}
