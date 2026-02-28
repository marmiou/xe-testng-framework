package gr.xe.core;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;

import java.time.Duration;

public final class DriverFactory {

    private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    private DriverFactory() {}

    public static WebDriver getDriver() {

        return driver.get();

    }

    public static void initDriver(String browser) {

        WebDriver webDriver;

        switch (browser.toLowerCase()) {

            case "chrome":

                ChromeOptions chromeOptions = new ChromeOptions();

                chromeOptions.addArguments("--start-maximized");

                webDriver = new ChromeDriver(chromeOptions);

                break;

            case "firefox":

                FirefoxOptions firefoxOptions = new FirefoxOptions();

                webDriver = new FirefoxDriver(firefoxOptions);

                webDriver.manage().window().maximize();

                break;

            case "edge":

                EdgeOptions edgeOptions = new EdgeOptions();

                webDriver = new EdgeDriver(edgeOptions);

                webDriver.manage().window().maximize();

                break;

            default:

                throw new IllegalArgumentException("Unsupported browser: " + browser);

        }

        webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        webDriver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));

        driver.set(webDriver);

    }

    public static void quitDriver() {

        if (driver.get() != null) {

            driver.get().quit();

            driver.remove();

        }

    }

}