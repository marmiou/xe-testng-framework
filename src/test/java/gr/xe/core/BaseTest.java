package gr.xe.core;

import org.openqa.selenium.WebDriver;
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

    }

    @AfterMethod(alwaysRun = true)
    public void teardown() {

        if (DriverFactory.getDriver() != null) {
            DriverFactory.quitDriver();
        }
    }

}