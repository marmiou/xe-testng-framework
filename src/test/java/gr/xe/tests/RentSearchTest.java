package gr.xe.tests;

import static org.assertj.core.api.Assertions.assertThat;

import io.qameta.allure.Allure;
import org.testng.annotations.Test;

import java.util.Map;

import gr.xe.core.BaseTest;
import gr.xe.pages.HomePage;
import gr.xe.pages.SearchResultsPage;
import gr.xe.pages.SearchResultsPage.AdData;

public class RentSearchTest extends BaseTest {

    @Test
    public void shouldFilterResultsCorrectly(){

        SearchResultsPage resultsPage=
                new HomePage(driver)
                        .open()
                        .acceptCookies()
                        .selectAllAutocompleteAreas("Παγκράτι")
                        .clickSearch()
                        .setPriceFilter(200,700)
                        .setSizeFilter(75,150);

        Map<String,AdData> ads=
                resultsPage.collectUniqueAds();
        ads.forEach((uuid,ad)->{

            Allure.step(
                    "Verify price is between 200 and 700 | Ad: "+uuid+" | Actual price: "+ad.price,
                    ()->assertThat(ad.price).isBetween(200,700)
            );

            Allure.step(
                    "Verify size is between 75 and 150 | Ad: "+uuid+" | Actual size: "+ad.size,
                    ()->assertThat(ad.size).isBetween(75,150)
            );

            Allure.step(
                    "Verify images ≤ 30 | Ad: "+uuid+" | Actual images: "+ad.images,
                    ()->assertThat(ad.images).isLessThanOrEqualTo(30)
            );

        });

    }
}