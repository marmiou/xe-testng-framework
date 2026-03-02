package gr.xe.pages;

import java.util.*;

import io.qameta.allure.Allure;
import io.qameta.allure.Step;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class SearchResultsPage extends BasePage {

    private static final String RESULTS_URL_PART = "results";

    private final By priceFilterButton = By.cssSelector("[data-testid='price-filter-button']");
    private final By sizeFilterButton = By.cssSelector("[data-testid='size-filter-button']");
    private final By minimumPriceInput = By.cssSelector("[data-testid='minimum_price_input']");
    private final By maximumPriceInput = By.cssSelector("[data-testid='maximum_price_input']");
    private final By minimumSizeInput = By.cssSelector("[data-testid='minimum_size_input']");
    private final By maximumSizeInput = By.cssSelector("[data-testid='maximum_size_input']");

    private final By adPrices = By.cssSelector("[data-testid='property-ad-price']");
    private final By adTitles = By.cssSelector("[data-testid='property-ad-title']");

    // ✅ REAL AD CARD ROOT
    private final By adCards =
            By.cssSelector("div.common-ad[data-testid^='property-ad-']");

    private final By adImageCarousels =
            By.cssSelector("[data-testid='property-ad-images-carousel']");

    private final By carouselSlides =
            By.cssSelector(".slick-slide:not(.slick-cloned)[data-index]");

    public SearchResultsPage(WebDriver driver) {
        super(driver);
        wait.until(d -> d.getCurrentUrl().contains(RESULTS_URL_PART));
        waitForResults();
    }

    @Step("Set price filter from {min} to {max}")
    public SearchResultsPage setPriceFilter(int min,int max){
        click(priceFilterButton);
        setRange(minimumPriceInput,maximumPriceInput,min,max);
        waitForResults();
        return this;
    }

    @Step("Set size filter from {min} to {max}")
    public SearchResultsPage setSizeFilter(int min,int max){
        click(sizeFilterButton);
        setRange(minimumSizeInput,maximumSizeInput,min,max);
        waitForResults();
        return this;
    }

    private void setRange(By minLocator,By maxLocator,int min,int max){
        clearAndType(minLocator,min);
        clearAndType(maxLocator,max);
        wait.until(d->getNumericValue(minLocator)==min&&getNumericValue(maxLocator)==max);
    }

    private void clearAndType(By locator,int value){
        WebElement element=find(locator);
        element.sendKeys(Keys.CONTROL+"a");
        element.sendKeys(Keys.DELETE);
        element.sendKeys(String.valueOf(value));
        element.sendKeys(Keys.TAB);
    }

    private int getNumericValue(By locator){
        String value=find(locator).getAttribute("value");
        return extractNumber(Objects.requireNonNull(value));
    }

    private void waitForResults(){
        wait.until(d->!findAll(adPrices).isEmpty());
    }

    @Step("Scroll one step down")
    public void scrollOneStep(){
        js.executeScript("window.scrollBy(0,400);");
    }

    @Step("Check if reached bottom")
    public boolean isAtBottom(){
        return (Boolean)js.executeScript(
                "return window.innerHeight + window.scrollY >= document.body.scrollHeight - 5;"
        );
    }

    @Step("Collect rendered ad prices")
    public List<Integer> collectRenderedAdPrices(){
        return findAll(adPrices)
                .stream()
                .map(WebElement::getText)
                .map(this::extractNumber)
                .toList();
    }

    @Step("Collect rendered ad sizes")
    public List<Integer> collectRenderedAdSizes(){
        return findAll(adTitles)
                .stream()
                .map(WebElement::getText)
                .map(this::extractNumber)
                .toList();
    }

    // ✅ FINAL METHOD
    @Step("Collect unique ads while scrolling")
    public Map<String,AdData> collectUniqueAds(){
        Map<String,AdData> ads=new LinkedHashMap<>();

        while(true){
            for(WebElement card:findAll(adCards)){
                String uuid=card.getAttribute("id");

                if(!ads.containsKey(uuid)){
                    AdData data=new AdData();
                    data.price=
                            extractNumber(
                                    card.findElement(adPrices).getText()
                            );
                    data.size=
                            extractNumber(
                                    card.findElement(adTitles).getText()
                            );
                    data.images=
                            card.findElements(carouselSlides)
                                    .size();
                    ads.put(uuid,data);

                    Allure.step(
                            "Found Ad | "+uuid+
                                    " | price="+data.price+
                                    " | size="+data.size+
                                    " | images="+data.images
                    );
                }
            }
            if(isAtBottom()){
                Allure.step("Reached bottom");
                break;
            }
            scrollOneStep();
        }
        return ads;
    }

    public static class AdData{
        public int price;
        public int size;
        public int images;

    }
}