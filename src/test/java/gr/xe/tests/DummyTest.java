package gr.xe.tests;

import gr.xe.core.BaseTest;

import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class DummyTest extends BaseTest {

    @Test
    public void openXeHomePage() {

        driver.get("https://www.xe.gr");

        String title = driver.getTitle();

        System.out.println("Page title is: " + title);

        assertThat(title)
                .isNotEmpty();

    }

}