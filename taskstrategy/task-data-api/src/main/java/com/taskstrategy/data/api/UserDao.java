package com.taskstrategy.data.api;

import com.taskstrategy.commons.domain.User;
import com.taskstrategy.data.api.exception.UserUpdateException;

/**
 * Created with IntelliJ IDEA.
 * User: brian
 * Date: 11/4/13
 * Time: 6:46 PM
 * To change this template use File | Settings | File Templates.
 */
public interface UserDao {

    User loadUserByUsername(String username);

    void createUser(User user) throws UserUpdateException;

    void deleteUser(String currentUser);

    void updateUsername(String currentUserId, String username) throws UserUpdateException;

    void updatePassword(String currentUserId, String password);

    String getUserIdFromReset(String resetId);

    void logResetPasswordEvent(String email, String resetId);

    void updatePasswordByUsername(String userId, String password);

    void invalidatePasswordResets();

    User loadUser(String userId);

}
