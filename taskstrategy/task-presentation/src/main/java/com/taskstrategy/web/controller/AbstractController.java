package com.taskstrategy.web.controller;

import com.taskstrategy.commons.domain.User;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Created with IntelliJ IDEA.
 * User: brian
 * Date: 11/4/13
 * Time: 7:16 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractController {

    public String getCurrentUserId() {
        return getCurrentUser().getId();
    }

    public User getCurrentUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
