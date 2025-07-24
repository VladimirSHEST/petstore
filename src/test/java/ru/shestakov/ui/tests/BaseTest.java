package ru.shestakov.ui.tests;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import ru.shestakov.ui.pages.SwaggerMainPage;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.title;
import static org.assertj.core.api.Assertions.assertThat;

public class BaseTest {
    private static final Properties properties = new Properties();

    @BeforeAll
    public static void setUp() throws IOException {
        InputStream input = BaseTest.class.getClassLoader().getResourceAsStream("config-ui.properties");
        properties.load(input);

        Configuration.browser = properties.getProperty("browser");
        Configuration.browserSize = properties.getProperty("browser.size");
        Configuration.holdBrowserOpen = Boolean.parseBoolean(properties.getProperty("holdBrowserOpen"));
        Configuration.headless = Boolean.parseBoolean(properties.getProperty("headless"));
    }

    @BeforeEach
    void setUpEach(){
        open(SwaggerMainPage.url());
    }
}
