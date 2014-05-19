package com.taskstrategy.web.security;

import com.taskstrategy.business.api.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * Created with IntelliJ IDEA.
 * User: brian
 * Date: 11/4/13
 * Time: 6:26 PM
 * To change this template use File | Settings | File Templates.
 */
public class TaskUserDetailsService implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException, DataAccessException {
        return userService.loadUserByUsername(username);
    }

}
