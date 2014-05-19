package com.taskstrategy.business.service;

import com.taskstrategy.business.api.EmailService;
import com.taskstrategy.business.api.UserService;
import com.taskstrategy.commons.domain.BaseDomain;
import com.taskstrategy.commons.domain.User;
import com.taskstrategy.data.api.UserDao;
import com.taskstrategy.data.api.exception.UserUpdateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.validation.groups.Default;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * The TaskServiceImpl class provides a convenient local implementation
 * of the TaskService
 */
@Service
public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);
    @Autowired
    private UserDao userDao;
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

    @Override
    public User loadUserByUsername(String username) {
        return userDao.loadUserByUsername(username);
    }

    @Override
    public void createUser(User user) throws UserUpdateException {
        checkForValidationViolations(user);
        userDao.createUser(user);
    }

    @Override
    public void deleteAccount(String currentUser) {
        userDao.deleteUser(currentUser);
    }

    @Override
    public void updateUsername(String currentUserId, String username) throws UserUpdateException {
        userDao.updateUsername(currentUserId, username);
    }

    @Override
    public void updatePassword(String currentUserId, String password) throws UserUpdateException {
        User user = new User("dummy", password);
        checkForValidationViolations(user);
        userDao.updatePassword(currentUserId, password);
    }

    @Override
    public void sendPasswordReset(String email) {
        EmailService emailService = new EmailServiceImpl();
        String resetId = UUID.randomUUID().toString();
        emailService.send(email, "Task Strategy Password Reset", "Here is your task strategy password reset link. http://taskstrategy.com/resetpassword/" + resetId);
        userDao.logResetPasswordEvent(email, resetId);
    }

    @Override
    public String getUserIdForPasswordReset(String resetId) {
        return userDao.getUserIdFromReset(resetId);
    }

    @Override
    public void updatePasswordByUsername(String userId, String password) {
        userDao.updatePasswordByUsername(userId, password);
    }

    @Override
    public void invalidatePasswordResets() {
        userDao.invalidatePasswordResets();
    }
}
