package gr.xe.tests;

import gr.xe.core.BaseTest;
import gr.xe.pages.HomePage;
import gr.xe.pages.SearchResultsPage;

import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

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
        assertThat(resultsPage.getAdPrices())
                .allMatch(price ->
                        price >= 200 && price <= 700
                );
        assertThat(resultsPage.getAdSizes())
                .allMatch(size ->
                        size >= 75 && size <= 150
                );
    }
}