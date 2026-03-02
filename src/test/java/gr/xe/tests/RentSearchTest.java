package gr.xe.tests;

import static org.assertj.core.api.Assertions.assertThat;

import io.qameta.allure.Allure;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import gr.xe.core.BaseTest;
import gr.xe.pages.HomePage;
import gr.xe.pages.SearchResultsPage;
import gr.xe.pages.SearchResultsPage.AdData;

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

        Map<String, AdData> ads =
                resultsPage.collectAllAds();
        ads.forEach((uuid, ad) -> {

            Allure.step(
                    "Verify price is between 200 and 700 | Ad: " + uuid + " | Actual price: " + ad.price,
                    () -> assertThat(ad.price)
                            .isBetween(200, 700)
            );
            Allure.step(
                    "Verify size is between 75 and 150 | Ad: " + uuid + " | Actual size: " + ad.size,
                    () -> assertThat(ad.size)
                            .isBetween(75, 150)
            );
            Allure.step(
                    "Verify images ≤ 30 | Ad: " + uuid + " | Actual images: " + ad.images,
                    () -> assertThat(ad.images)
                            .isLessThanOrEqualTo(30)
            );
        });
    }

    @Test
    public void shouldSortResultsByPriceDescending(){

        SearchResultsPage resultsPage=
                new HomePage(driver)
                        .open()
                        .acceptCookies()
                        .selectAllAutocompleteAreas("Παγκράτι")
                        .clickSearch()
                        .setPriceFilter(200,700)
                        .setSizeFilter(75,150)
                        .sortByPriceDesc();

        Map<String,AdData> ads=
                resultsPage.collectAllAds();
        Allure.step("Total ads collected: "+ads.size());

        List<Map.Entry<String,AdData>> entries=
                new ArrayList<>(ads.entrySet());

        for(int i=0;i<entries.size()-1;i++){
            String uuid=entries.get(i).getKey();
            int current=entries.get(i).getValue().price;
            int next=entries.get(i+1).getValue().price;

            Allure.step(
                    "Verify descending | Ad: "
                            +uuid
                            +" | price: "
                            +current
                            +" >= next price: "
                            +next,
                    ()->assertThat(current)
                            .isGreaterThanOrEqualTo(next)
            );
        }
    }
}