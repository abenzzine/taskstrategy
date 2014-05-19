package com.taskstrategy.web.controller;

import com.taskstrategy.business.api.TagService;
import com.taskstrategy.commons.domain.Tag;
import com.taskstrategy.commons.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by brian on 1/17/14.
 */

public class ControllerInterceptor extends HandlerInterceptorAdapter {

    private static final String TAGS = "favoriteTags";
    @Autowired
    private TagService tagService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        return super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        if (!"anonymousUser".equals(SecurityContextHolder.getContext().getAuthentication().getPrincipal())) {
            String userId = getCurrentUserId();
            List<Tag> tagList = tagService.getFavoriteTags(userId);
            request.setAttribute(TAGS, tagList);
        }
    }

    public String getCurrentUserId() {
        return getCurrentUser().getId();
    }

    public User getCurrentUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
