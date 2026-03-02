package gr.xe.pages;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import io.qameta.allure.Allure;
import io.qameta.allure.Step;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class SearchResultsPage extends BasePage {

    private static final String RESULTS_URL_PART = "results";
    private static final String ID = "id";
    private static final String VALUE = "value";
    private static final String ARIA_DISABLED = "aria-disabled";

    private final By priceFilterButton = By.cssSelector("[data-testid='price-filter-button']");
    private final By sizeFilterButton = By.cssSelector("[data-testid='size-filter-button']");
    private final By minimumPriceInput = By.cssSelector("[data-testid='minimum_price_input']");
    private final By maximumPriceInput = By.cssSelector("[data-testid='maximum_price_input']");
    private final By minimumSizeInput = By.cssSelector("[data-testid='minimum_size_input']");
    private final By maximumSizeInput = By.cssSelector("[data-testid='maximum_size_input']");
    private final By adCards = By.cssSelector("div.common-ad[data-testid^='property-ad-']");
    private final By adPrices = By.cssSelector("[data-testid='property-ad-price']");
    private final By adTitles = By.cssSelector("[data-testid='property-ad-title']");
    private final By carouselSlides = By.cssSelector(".slick-slide:not(.slick-cloned)[data-index]");
    private final By sortingDropdown = By.cssSelector("[data-testid='open-property-sorting-dropdown']");
    private final By sortPriceDesc = By.cssSelector("[data-testid='price_desc']");
    private final By nextPageButton = By.cssSelector("a[rel='next']");

    public SearchResultsPage(WebDriver driver) {
        super(driver);
        wait.until(d -> d.getCurrentUrl().contains(RESULTS_URL_PART));
        waitForResults();
    }


    @Step("Set price filter {min}-{max}")
    public SearchResultsPage setPriceFilter(int min, int max) {
        click(priceFilterButton);
        setRange(minimumPriceInput, maximumPriceInput, min, max);
        waitForResults();
        return this;
    }


    @Step("Set size filter {min}-{max}")
    public SearchResultsPage setSizeFilter(int min, int max) {
        click(sizeFilterButton);
        setRange(minimumSizeInput, maximumSizeInput, min, max);
        waitForResults();
        return this;
    }


    private void setRange(By minLocator, By maxLocator, int min, int max) {
        clearAndType(minLocator, String.valueOf(min));
        clearAndType(maxLocator, String.valueOf(max));
        wait.until(d ->
                getNumericValue(minLocator) == min &&
                        getNumericValue(maxLocator) == max
        );
    }


    private int getNumericValue(By locator) {
        return extractNumber(find(locator).getAttribute(VALUE));
    }


    private void waitForResults() {
        wait.until(d -> !findAll(adCards).isEmpty());
    }


    private Map<String, AdData> collectVisibleAds() {
        Map<String, AdData> ads = new LinkedHashMap<>();
        for (WebElement card : findAll(adCards)) {
            String uuid = card.getAttribute(ID);

            AdData data = new AdData();
            data.price = extractNumber(card.findElement(adPrices).getText());
            data.size = extractNumber(card.findElement(adTitles).getText());
            data.images = card.findElements(carouselSlides).size();
            ads.put(uuid, data);
        }
        return ads;
    }


    @Step("Collect all ads from all pages")
    public Map<String, AdData> collectAllAds() {

        Map<String, AdData> allAds = new LinkedHashMap<>();

        while (true) {
            while (!isAtBottom()) {
                scrollOneStep();
            }

            Map<String, AdData> visible = collectVisibleAds();
            visible.forEach(allAds::putIfAbsent);

            Allure.step("Collected from page. Total so far: " + allAds.size());
            if (!hasNextPage()) {
                Allure.step("No more pages");
                break;
            }
            Allure.step("Moving to next page");
            goToNextPage();
        }

        return allAds;
    }


    @Step("Sort by price descending")
    public SearchResultsPage sortByPriceDesc() {
        click(sortingDropdown);
        click(sortPriceDesc);
        waitForResults();
        return this;
    }

    private boolean hasNextPage() {
        List<WebElement> nextButtons = findAll(nextPageButton);
        if (nextButtons.isEmpty()) {
            return false;
        }
        return !"true".equals(nextButtons.get(0).getAttribute(ARIA_DISABLED));
    }

    @Step("Go to next page")
    private void goToNextPage() {
        WebElement next = find(nextPageButton);
        js.executeScript("arguments[0].scrollIntoView({block:'center'});", next);
        String oldUrl = driver.getCurrentUrl();
        next.click();
        wait.until(d -> !d.getCurrentUrl().equals(oldUrl));
        waitForResults();
    }


    public static class AdData {
        public int price;
        public int size;
        public int images;
    }
}