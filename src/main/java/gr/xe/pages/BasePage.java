package gr.xe.pages;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;


import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public abstract class BasePage {

    protected final WebDriver driver;
    protected final WebDriverWait wait;
    protected JavascriptExecutor js;
    protected Actions actions;


    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        this.js = (JavascriptExecutor) driver;
        actions = new Actions(driver);
    }


    protected WebElement find(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }


    protected List<WebElement> findAll(By locator) {
        return driver.findElements(locator);
    }


    protected void click(By locator) {
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
    }


    protected void type(By locator, String text) {
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));

        element.clear();
        element.sendKeys(text);
    }


    protected void clearAndType(By locator, String text) {

        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
        element.sendKeys(Keys.CONTROL + "a");
        element.sendKeys(Keys.DELETE);
        element.sendKeys(text);
        element.sendKeys(Keys.TAB);
    }


    public int extractNumber(String text) {
        return Integer.parseInt(text.replaceAll("[^0-9]", ""));
    }


    protected void scrollOneStep() {
        js.executeScript("window.scrollBy(0,400);");
    }


    protected boolean isAtBottom() {
        return (Boolean) js.executeScript(
                "return window.innerHeight + window.scrollY >= document.body.scrollHeight - 5"
        );
    }
}