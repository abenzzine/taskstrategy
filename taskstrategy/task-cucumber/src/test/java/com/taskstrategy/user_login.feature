Feature: User login


Scenario: User is able to log in using their account

  Given The home page url

  And The browser title should have "Task Strategy"

  And Login Page is loaded

  When the user logs in with user id "user_1@user.com" and password as "user1"

  Then expect user to be logged into the system



