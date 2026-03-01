package gr.xe.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.Keys;

import java.util.List;
import java.util.Objects;

public class SearchResultsPage extends BasePage {

    private static final String RESULTS_URL_PART = "results";

    // Locators
    private final By priceFilterButton = By.cssSelector("[data-testid='price-filter-button']");
    private final By sizeFilterButton = By.cssSelector("[data-testid='size-filter-button']");
    private final By minimumPriceInput = By.cssSelector("[data-testid='minimum_price_input']");
    private final By maximumPriceInput = By.cssSelector("[data-testid='maximum_price_input']");
    private final By minimumSizeInput = By.cssSelector("[data-testid='minimum_size_input']");
    private final By maximumSizeInput = By.cssSelector("[data-testid='maximum_size_input']");
    private final By adPrices = By.cssSelector("[data-testid='property-ad-price']");
    private final By adTitles = By.cssSelector("[data-testid='property-ad-title']");


    public SearchResultsPage(WebDriver driver) {
        super(driver);
        wait.until(d ->
                d.getCurrentUrl().contains(RESULTS_URL_PART)
        );
        waitForResults();
    }

    public SearchResultsPage setPriceFilter(int min, int max) {

        click(priceFilterButton);
        setRange(minimumPriceInput, maximumPriceInput, min, max);
        waitForResults();
        return this;
    }

    public SearchResultsPage setSizeFilter(int min, int max) {
        click(sizeFilterButton);
        setRange(minimumSizeInput, maximumSizeInput, min, max);
        waitForResults();
        return this;
    }

    private void setRange(By minLocator, By maxLocator, int min, int max) {
        clearAndType(minLocator, min);
        clearAndType(maxLocator, max);
        wait.until(d ->
                getNumericValue(minLocator) == min &&
                        getNumericValue(maxLocator) == max
        );
    }

    private void clearAndType(By locator, int value) {
        WebElement element = find(locator);
        element.sendKeys(Keys.CONTROL + "a");
        element.sendKeys(Keys.DELETE);
        element.sendKeys(String.valueOf(value));
        element.sendKeys(Keys.TAB);
    }

    private int getNumericValue(By locator) {
        String value = find(locator).getAttribute("value");
        return extractNumber(
                Objects.requireNonNull(value)
        );
    }

    private void waitForResults() {
        wait.until(d ->
                !findAll(adPrices).isEmpty()
        );
    }

    public List<Integer> getAdPrices() {
        return findAll(adPrices)
                .stream()
                .map(WebElement::getText)
                .map(this::extractNumber)
                .toList();
    }

    public List<Integer> getAdSizes() {

        return findAll(adTitles)
                .stream()
                .map(WebElement::getText)
                .map(this::extractNumber)
                .toList();
    }
}