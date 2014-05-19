package com.taskstrategy;

import cucumber.api.PendingException;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import java.util.concurrent.TimeUnit;

import static com.thoughtworks.selenium.SeleneseTestBase.fail;
@PropertySource("classpath:task-strategy.properties")
public class WebSteps {

    @Autowired
    private Environment environment;
    private WebDriver driver;
    private String baseUrl = "http://localhost:8080/";
    private boolean acceptNextAlert = true;
    private StringBuffer verificationErrors = new StringBuffer();

    @Before
    public void setUp() throws Exception {
        driver = new HtmlUnitDriver();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

    @Given("^The home page url$")
    public void the_home_page_url() {
        driver = new HtmlUnitDriver();
        driver.get(baseUrl);
 
    }
    
    @Then("^The browser title should have \"([^\"]*)\"$")
    public void The_browser_title_should_have(String arg1) throws Throwable {
    	//assertThat ("Browser title:",driver.getTitle(), containsString(arg1));
        Assert.assertNotNull(driver.findElement(By.id("password")));
        Assert.assertNotNull(driver.findElement(By.id("email")));
        Assert.assertNotNull(driver.findElement(By.id("footer")));
        Assert.assertNotNull(driver.findElement(By.id("header")));
        Assert.assertNotNull(driver.findElement(By.id("bottom-content")));
        Assert.assertNotNull(driver.findElement(By.id("signin")));
        Assert.assertNotNull(driver.findElement(By.id("page")));
//        driver.findElement(By.id("signup")).click();
//        driver.navigate().forward();
//        System.out.println("URL= " + driver.getCurrentUrl());
    }

    @Given("^testSign$")
    public void testForSignin1() throws Exception {
        driver.get(baseUrl + "/");
        driver.findElement(By.id("email")).clear();
        driver.findElement(By.id("email")).sendKeys("admin");
        driver.findElement(By.id("password")).clear();
        driver.findElement(By.id("password")).sendKeys("admin");
        driver.findElement(By.id("signin")).click();
    }

    @Then("^AssertSignIn$")
    public void testSignIn() throws Throwable {
        //assertThat ("Browser title:",driver.getTitle(), containsString(arg1));
        Assert.assertEquals(driver.getCurrentUrl(), "http://localhost:8080/tasks");
        System.out.println("URL= " + driver.getCurrentUrl());
    }

//    @Given("^testTasks$")
//    public void testForTasks() throws Exception {
////        driver.get(baseUrl + "/taskstrategy/");
////        driver.findElement(By.id("email")).clear();
////        driver.findElement(By.id("email")).sendKeys("admin");
////        driver.findElement(By.id("password")).clear();
////        driver.findElement(By.id("password")).sendKeys("admin");
////        driver.findElement(By.id("signin")).click();
////        driver.findElement(By.xpath("(//a[contains(text(),'New Task')])[2]")).click();
////        driver.findElement(By.id("name")).clear();
////        driver.findElement(By.id("name")).sendKeys("TestingTask");
////        driver.findElement(By.cssSelector("th.switch")).click();
////        driver.findElement(By.cssSelector("div.datepicker-months > table.table-condensed > thead > tr > th.switch")).click();
////        driver.findElement(By.xpath("//div[3]/table/tbody/tr/td/span[11]")).click();
////        driver.findElement(By.id("description")).clear();
////        driver.findElement(By.id("description")).sendKeys("This is a testing Task");
////        driver.findElement(By.id("save")).click();
////        Assert.assertEquals("TestingTask", driver.findElement(By.linkText("TestingTask")).getText());
////        driver.findElement(By.xpath("//div[@id='task-list']/table/tbody/tr[3]/td[6]/a[2]/span")).click();
////        driver.findElement(By.linkText("Logout")).click();
//
//        driver.get(baseUrl + "/taskstrategy/");
//        driver.findElement(By.id("email")).clear();
//        driver.findElement(By.id("email")).sendKeys("admin");
//        driver.findElement(By.id("password")).clear();
//        driver.findElement(By.id("password")).sendKeys("admin");
//        driver.findElement(By.id("signin")).click();
//        driver.findElement(By.xpath("(//a[contains(text(),'New Task')])[2]")).click();
//        driver.findElement(By.id("name")).clear();
//        driver.findElement(By.id("name")).sendKeys("testingTask");
//        driver.findElement(By.id("description")).clear();
//        driver.findElement(By.id("description")).sendKeys("This is a testing Task");
//        driver.findElement(By.id("save")).click();
//        driver.findElement(By.id("save")).click();
//        driver.findElement(By.xpath("//div[@id='task-list']/table/tbody/tr[3]/td[6]/a[2]/span")).click();
//        driver.findElement(By.linkText("Logout")).click();
//    }
//    @Then("^AssertTasks$")
//    public void testTasks() throws Throwable {
//        return;
//    }

    @After
    public void tearDown() throws Exception {
        driver.quit();
        String verificationErrorString = verificationErrors.toString();
        if (!"".equals(verificationErrorString)) {
            fail(verificationErrorString);
        }
    }

    private boolean isElementPresent(By by) {
        try {
            driver.findElement(by);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    private boolean isAlertPresent() {
        try {
            driver.switchTo().alert();
            return true;
        } catch (NoAlertPresentException e) {
            return false;
        }
    }

    private String closeAlertAndGetItsText() {
        try {
            Alert alert = driver.switchTo().alert();
            String alertText = alert.getText();
            if (acceptNextAlert) {
                alert.accept();
            } else {
                alert.dismiss();
            }
            return alertText;
        } finally {
            acceptNextAlert = true;
        }
    }

    @Given("^Login Page is loaded$")
    public void Login_Page_is_loaded() throws Throwable {
        // Express the Regexp above with the code you wish you had
        throw new PendingException();
    }


    @When("^the user logs in with user id \"([^\"]*)\" and password as \"([^\"]*)\"$")
    public void the_user_logs_in_with_user_id_and_password_as(String arg1, String arg2) throws Throwable {
        // Express the Regexp above with the code you wish you had
        throw new PendingException();
    }

    @Then("^expect user to be logged into the system$")
    public void expect_user_to_be_logged_into_the_system() throws Throwable {
        // Express the Regexp above with the code you wish you had
        throw new PendingException();
    }


}


