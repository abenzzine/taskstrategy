package com.taskstrategy.business.api;

import com.taskstrategy.commons.domain.User;
import com.taskstrategy.data.api.exception.UserUpdateException;

/**
 * Created with IntelliJ IDEA.
 * User: brian
 * Date: 11/4/13
 * Time: 7:01 PM
 * To change this template use File | Settings | File Templates.
 */
public interface UserService {

    User loadUserByUsername(String username);

    void createUser(User user) throws UserUpdateException;

    void deleteAccount(String currentUser);

    void updateUsername(String currentUserId, String username) throws UserUpdateException;

    void updatePassword(String currentUserId, String password) throws UserUpdateException;

    void sendPasswordReset(String email);

    String getUserIdForPasswordReset(String resetId);

    void updatePasswordByUsername(String userId, String password);

    void invalidatePasswordResets();
}
