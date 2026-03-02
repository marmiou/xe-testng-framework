package gr.xe.tests;

import static org.assertj.core.api.Assertions.assertThat;

import io.qameta.allure.Allure;
import org.testng.annotations.Test;

import java.util.LinkedHashSet;
import java.util.Set;

import gr.xe.core.BaseTest;
import gr.xe.pages.HomePage;
import gr.xe.pages.SearchResultsPage;

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

        Set<Integer> uniquePrices=new LinkedHashSet<>();
        Set<Integer> uniqueSizes=new LinkedHashSet<>();
        Set<String> imageInfos=new LinkedHashSet<>();

        resultsPage.collectUniqueAds(uniquePrices,uniqueSizes,imageInfos);

        for(Integer price:uniquePrices){
            Allure.step("Verify price: "+price,
                    ()->assertThat(price).isBetween(200,700));
        }

        for(Integer size:uniqueSizes){
            Allure.step("Verify size: "+size,
                    ()->assertThat(size).isBetween(75,150));
        }

        for(String info:imageInfos){
            String[] parts=info.split("\\|");
            String uuid=parts[0].trim();
            int count=Integer.parseInt(parts[1].trim());
            Allure.step(
                    "Verify images ≤ 30 | Ad UUID: "+uuid+" | Images: "+count,
                    ()->assertThat(count).isLessThanOrEqualTo(30)
            );
        }
    }
}