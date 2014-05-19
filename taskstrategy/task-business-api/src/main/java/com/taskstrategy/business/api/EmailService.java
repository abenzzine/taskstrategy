package com.taskstrategy.business.api;

public interface EmailService {
    boolean send(String emailAddress, String subject, String body);
}
