package com.taskstrategy;

/**
 * Created with IntelliJ IDEA.
 * User: Owner
 * Date: 10/26/13
 * Time: 7:25 PM
 * To change this template use File | Settings | File Templates.
 */

import org.junit.runner.RunWith;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@Cucumber.Options(strict = true, features = {"src/test/java/com/taskstrategy"})
public class SeleniumRunner { }
