package gr.xe.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class HomePage extends BasePage {

    private static final String URL = "https://www.xe.gr/";
    private final By rentSelected = By.cssSelector("[data-testid='rent'].selected");
    private final By acceptCookiesBtn = By.id("accept-btn");
    private final By areaInput = By.cssSelector("[data-testid='area-input']");
    private final By autocompleteOptions = By.cssSelector("button[data-testid^='dropdown_option_']");
    private final By selectedAreaTags = By.cssSelector("[data-testid$='-area-tag-span']");
    private final By submitButton = By.cssSelector("[data-testid='submit-input']");

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public HomePage open() {
        driver.get(URL);
        find(areaInput);
        return this;
    }

    public HomePage acceptCookies() {
        click(acceptCookiesBtn);
        return this;
    }

    public boolean isRentSelected() {
        return find(rentSelected).isDisplayed();
    }

    public HomePage selectAllAutocompleteAreas(String area) {
        Set<String> selected = new HashSet<>();

        while (true) {
            type(areaInput, area);
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

    public SearchResultsPage clickSearch() {
        click(submitButton);
        return new SearchResultsPage(driver);
    }
}