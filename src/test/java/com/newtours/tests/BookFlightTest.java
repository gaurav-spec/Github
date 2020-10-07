package com.newtours.tests;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.newtours.pages.*;
import com.tests.BaseTest;
import org.testng.Assert;
import org.testng.annotations.*;

public class BookFlightTest extends BaseTest {

    ExtentReports extentReports;
    ExtentTest extentTest;
    ExtentHtmlReporter extentHtmlReporter;
    private String noOfPassengers;
    private String expectedPrice;

    @BeforeClass
    public void setExtentReport() {
        extentHtmlReporter = new ExtentHtmlReporter(System.getProperty("user.dir") + "/QubitAITestingReport.html");
        extentHtmlReporter.config().setEncoding("utf-8");
        extentHtmlReporter.config().setDocumentTitle("Automation Reports");
        extentHtmlReporter.config().setReportName("Qubit Automation Test Result");
        extentHtmlReporter.config().setTheme(Theme.DARK);
        extentReports = new ExtentReports();
        extentReports.setSystemInfo("Organization", "QubitAI");
        extentReports.setSystemInfo("Browser", "Chrome");
        extentReports.attachReporter(extentHtmlReporter);
    }

    @BeforeTest
    @Parameters({"noOfPassengers", "expectedPrice"})
    public void setupParameters(String noOfPassengers, String expectedPrice) {
        this.noOfPassengers = noOfPassengers;
        this.expectedPrice = expectedPrice;
    }

    @Test
    public void registrationPage() {
        ExtentTest logger = extentReports.createTest("registration");
        RegistrationPage registrationPage = new RegistrationPage(driver);
        registrationPage.goTo();
        logger.log(Status.INFO, "go to registration page");
        registrationPage.enterUserDetails("selenium", "docker");
        logger.log(Status.INFO, "enter user details");
        registrationPage.enterUserCredentials("selenium", "docker");
        logger.log(Status.INFO, "enter user credentials");
//        String path = takeScreenshot(this.toString());
//        try{
//            extentTest.pass("Registration Completed", MediaEntityBuilder.createScreenCaptureFromPath(path).build());
//        } catch (IOException e) {
//            extentTest.pass("Test passed , Cannot attach screenshot");
//        }
        registrationPage.submit();
        logger.log(Status.INFO, "submit registration details");
        extentReports.flush();
    }

    @Test(dependsOnMethods = "registrationPage")
    public void registrationConfirmationPage() {
        ExtentTest logger1 = extentReports.createTest("registration confirmation");
        RegistrationConfirmationPage registrationConfirmationPage = new RegistrationConfirmationPage(driver);
        registrationConfirmationPage.goToFlightDetailsPage();
//        String path = takeScreenshot(this.toString());
//        try{
//            extentTest.pass("Registration Confirmed", MediaEntityBuilder.createScreenCaptureFromPath(path).build());
//        } catch (IOException e) {
//            extentTest.pass("Test passed , Cannot attach screenshot");
//        }
        logger1.log(Status.INFO, "registration confirmed");
        extentReports.flush();
    }

    @Test(dependsOnMethods = "registrationConfirmationPage")
    public void flightDetailsPage() {
        ExtentTest logger2 = extentReports.createTest("find flight details");
        FlightDetailsPage flightDetailsPage = new FlightDetailsPage(driver);
        flightDetailsPage.selectPassengers(noOfPassengers);
        flightDetailsPage.goToFindFlightsPage();
//        String path = takeScreenshot(this.toString());
//        try{
//            extentTest.pass("Flight Details page", MediaEntityBuilder.createScreenCaptureFromPath(path).build());
//        } catch (IOException e) {
//            extentTest.pass("Test passed , Cannot attach screenshot");
//        }
        logger2.log(Status.INFO, "go to find flights");
        extentReports.flush();
    }

    @Test(dependsOnMethods = "flightDetailsPage")
    public void findFlightPage() {
        ExtentTest logger3 = extentReports.createTest("find flights");
        FindFlightPage findFlightPage = new FindFlightPage(driver);
        findFlightPage.submitFindFlightPage();
        findFlightPage.goToFlightConfirmationPage();
        logger3.log(Status.INFO, "search flights");
        extentReports.flush();
    }

    @Test(dependsOnMethods = "findFlightPage")
    public void flightConfirmationPage() {
        ExtentTest logger4 = extentReports.createTest("flight confirmation page");
        FlightConfirmationPage flightConfirmationPage = new FlightConfirmationPage(driver);
        String actualPrice = flightConfirmationPage.getPrice();
        Assert.assertEquals(actualPrice, expectedPrice);
        if (actualPrice.equalsIgnoreCase(expectedPrice)) {
            logger4.pass("Expected and actual flight price is same");
        } else {
            logger4.fail("Expected and actual flight price is not same");
        }
//        String path = takeScreenshot(this.toString());
//        try{
//            extentTest.pass("flight confirmation page", MediaEntityBuilder.createScreenCaptureFromPath(path).build());
//        } catch (IOException e) {
//            extentTest.pass("Test passed , Cannot attach screenshot");
//        }
        extentReports.flush();
    }

    @AfterClass
    public void flushExtentReport() {
        extentReports.flush();
    }

//    public String takeScreenshot(String methodName) {
//        String fileName = getScreenshotName(methodName);
//        String directory = System.getProperty("user.dir") + "/screenshots/";
//        new File(directory).mkdirs();
//        String path = directory + fileName;
//
//        try {
//            File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
//            FileUtils.copyFile(screenshot, new File(path));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return path;
//    }
//
//    public static String getScreenshotName(String methodName) {
//        Date d = new Date();
//        String fileName = methodName + "_" + d.toString().replace(" ", "_") + ".png";
//        return fileName;
//    }
}