package gr.xe.core;
import org.openqa.selenium.WebDriver;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;

public class DriverFactory {

    private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();


    public static void initDriver(String browser) {

        boolean isHeadless = Boolean.parseBoolean(
                System.getProperty("headless", "false")
        );

        boolean isCI = System.getenv("CI") != null;

        switch (browser.toLowerCase()) {

            case "chrome":

                ChromeOptions chromeOptions = new ChromeOptions();

                if (isHeadless || isCI) {
                    chromeOptions.addArguments("--headless=new");
                }

                chromeOptions.addArguments("--no-sandbox");
                chromeOptions.addArguments("--disable-dev-shm-usage");
                chromeOptions.addArguments("--window-size=1920,1080");

                driver.set(new ChromeDriver(chromeOptions));

                break;

            case "firefox":

                FirefoxOptions firefoxOptions = new FirefoxOptions();
                if (isHeadless || isCI) {
                    firefoxOptions.addArguments("--headless");

                }
                driver.set(new FirefoxDriver(firefoxOptions));
                break;

            case "edge":
                EdgeOptions edgeOptions = new EdgeOptions();
                if (isHeadless || isCI) {
                    edgeOptions.addArguments("--headless=new");
                }

                edgeOptions.addArguments("--no-sandbox");
                edgeOptions.addArguments("--disable-dev-shm-usage");

                driver.set(new EdgeDriver(edgeOptions));
                break;
        }
    }

    public static WebDriver getDriver() {
        return driver.get();
    }

    public static void quitDriver() {
        driver.get().quit();
        driver.remove();
    }
}