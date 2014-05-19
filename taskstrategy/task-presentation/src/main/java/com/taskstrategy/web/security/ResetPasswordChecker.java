package com.taskstrategy.web.security;

import com.taskstrategy.business.api.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.TimeUnit;

/**
 * Created by brian on 1/16/14.
 */
@Component
public class ResetPasswordChecker {

    @Autowired
    private UserService userService;

    @PostConstruct
    public void init() {
        Thread thread = new Thread(new ResetPasswordCheckRunner(userService));
        thread.setDaemon(true);
        thread.start();
    }

    private static final class ResetPasswordCheckRunner implements Runnable {

        private UserService userService;

        private ResetPasswordCheckRunner(UserService userService) {
            this.userService = userService;
        }

        @Override
        public void run() {
            while (true) {
                userService.invalidatePasswordResets();
                try {
                    Thread.sleep(TimeUnit.MILLISECONDS.convert(60, TimeUnit.SECONDS));
                } catch (InterruptedException e) {
                }
            }
        }
    }
}
