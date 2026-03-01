package gr.xe.tests;

import static org.assertj.core.api.Assertions.assertThat;

import io.qameta.allure.Allure;

import org.testng.annotations.Test;

import gr.xe.core.BaseTest;
import gr.xe.pages.HomePage;
import gr.xe.pages.SearchResultsPage;

public class RentSearchTest extends BaseTest {

    @Test
    public void shouldFilterResultsCorrectly() {

        SearchResultsPage resultsPage =
                new HomePage(driver)
                        .open()
                        .acceptCookies()
                        .selectAllAutocompleteAreas("Παγκράτι")
                        .clickSearch()
                        .setPriceFilter(200, 700)
                        .setSizeFilter(75, 150);
        Allure.step("Verify all ad prices are between 200 and 700", () -> {
            resultsPage.getAdPrices().forEach(price ->
                    Allure.step("Check price: " + price, () ->
                            assertThat(price)
                                    .isBetween(200, 700)
                    )
            );
        });

        Allure.step("Verify all ad sizes are between 75 and 150", () -> {
            resultsPage.getAdSizes().forEach(size ->
                    Allure.step("Check size: " + size, () ->
                            assertThat(size)
                                    .isBetween(75, 150)
                    )
            );
        });
    }
}