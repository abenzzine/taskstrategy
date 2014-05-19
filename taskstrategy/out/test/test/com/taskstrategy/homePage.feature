Feature: Task Strategy Home Page Features
In order to ensure that Task Strategy application is up and running

Scenario: Main page is accessible scenario

  Given The home page url

  Then The browser title should have "Task Strategy"

  Given testSign

  Then AssertSignIn

#  Given testTasks
#
#  Then AssertTasks