package gr.xe.pages;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import io.qameta.allure.Step;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class HomePage extends BasePage {

    private static final String URL = "https://www.xe.gr/";
    private final By acceptCookiesBtn = By.id("accept-btn");
    private final By areaInput = By.cssSelector("[data-testid='area-input']");
    private final By autocompleteOptions = By.cssSelector("button[data-testid^='dropdown_option_']");
    private final By selectedAreaTags = By.cssSelector("[data-testid$='-area-tag-span']");
    private final By submitButton = By.cssSelector("[data-testid='submit-input']");

    public HomePage(WebDriver driver) {
        super(driver);
    }

    @Step("Open XE homepage")
    public HomePage open() {
        driver.get(URL);
        find(areaInput);
        return this;
    }

    @Step("Accept cookies")
    public HomePage acceptCookies() {

        try {
            WebElement accept = wait.until(
                    ExpectedConditions.elementToBeClickable(acceptCookiesBtn)
            );
            accept.click();
        }
        catch(Exception e){
                // popup did not appear → safe to continue
            }
            return this;
        }

    @Step("Select all autocomplete areas for: {area}")
    public HomePage selectAllAutocompleteAreas(String area) {
        Set<String> selected = new HashSet<>();

        while (true) {
            type(areaInput, area);
            wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
                    autocompleteOptions
            ));
            List<WebElement> options = findAll(autocompleteOptions);
            boolean foundNew = false;
            for (WebElement option : options) {
                String text = option.getText();
                if (!selected.contains(text)) {
                    int before = findAll(selectedAreaTags).size();
                    option.click();
                    wait.until(d ->
                            findAll(selectedAreaTags).size() > before
                    );
                    selected.add(text);
                    foundNew = true;
                    break;
                }
            }
            if (!foundNew) {
                break;
            }
        }
        return this;
    }

    @Step("Click Search button")
    public SearchResultsPage clickSearch() {
        click(submitButton);
        return new SearchResultsPage(driver);
    }

    public List<String> getSelectedAreas() {
        return findAll(selectedAreaTags)
                .stream()
                .map(WebElement::getText)
                .map(String::trim)
                .toList();
    }
}