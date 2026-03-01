package gr.xe.core;

import java.io.ByteArrayInputStream;

import io.qameta.allure.Allure;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;

public abstract class BaseTest {

    protected WebDriver driver;

    @Parameters("browser")
    @BeforeMethod(alwaysRun = true)
    public void setup(String browser) {
        DriverFactory.initDriver(browser);
        driver = DriverFactory.getDriver();
        driver.manage().deleteAllCookies();
    }

    @AfterMethod(alwaysRun = true)
    public void teardown() {
        if (DriverFactory.getDriver() != null) {
            DriverFactory.quitDriver();
        }
    }

    @AfterMethod
    public void attachScreenshotOnFailure(ITestResult result) {

        if (result.getStatus() == ITestResult.FAILURE) {

            byte[] screenshot =
                    ((TakesScreenshot) driver)
                            .getScreenshotAs(OutputType.BYTES);
            Allure.addAttachment(
                    "Screenshot",
                    new ByteArrayInputStream(screenshot)
            );
        }
    }

}