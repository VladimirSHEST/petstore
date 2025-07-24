package ru.shestakov.ui.tests;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.shestakov.ui.pages.SwaggerMainPage;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.cssValue;
import static com.codeborne.selenide.Condition.text;
import static io.qameta.allure.Allure.step;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("UI тесты")
public class SwaggerPetstoreTest extends BaseTest {
    private final SwaggerMainPage swaggerMainPage = new SwaggerMainPage();

    @Test
    @DisplayName("Проверка заголовка на странице Swagger Petstore")
    public void checkTitleInPageSwaggerPetstoreTest() {
        step("Открываем страницу Swagger Petstore и проверяем заголовок", () -> {
            swaggerMainPage.mainDiv().shouldBe(Condition.visible);
            assertThat(title()).contains("Swagger UI");
            swaggerMainPage.titleH2().shouldHave(text("Swagger Petstore"));
        });
    }

    @Test
    @DisplayName("Проверка открытия и закрытия секций (листов)")
    public void checkOpenAndCloseTegTest() {
        step("Закрытие листа 'store'", () -> {
            swaggerMainPage.listStore().click();
            swaggerMainPage.blockGetInStore().shouldNotBe(Condition.visible);
        });
        step("Открытие листа 'store'", () -> {
            swaggerMainPage.listStore().click();
            swaggerMainPage.blockGetInStore().shouldBe(Condition.visible);
        });

        step("Закрытие листа 'pet'", () -> {
            swaggerMainPage.listPet().click();
            swaggerMainPage.blockGetInPet().shouldNotBe(Condition.visible);
        });
        step("Открытие листа 'pet'", () -> {
            swaggerMainPage.listPet().click();
            swaggerMainPage.blockGetInPet().shouldBe(Condition.visible);
        });

        step("Закрытие листа 'user'", () -> {
            swaggerMainPage.listUser().click();
            swaggerMainPage.blockGetInUser().shouldNotBe(Condition.visible);
        });
        step("Открытие листа 'user'", () -> {
            swaggerMainPage.listUser().click();
            swaggerMainPage.blockGetInUser().shouldBe(Condition.visible);
        });
    }

    @Test
    @DisplayName("Проверяем Deprecated отображение в ручке pet/findPetsByStatus")
    public void checkVisibleDeprecatedTest() {
        step("Проверка отображения Deprecated", () -> {
            swaggerMainPage.listPetMethodDeprecated().click();
            swaggerMainPage.deprecatedMethodGet().shouldBe(Condition.visible);
        });
    }

    @Test
    @DisplayName("Проверка цвета кнопок DELETE")
    public void checkColorDeleteTest() {
            swaggerMainPage.blockListOfPet().
                    forEach(element ->element.shouldHave(cssValue("background-color",
                            "rgba(249, 62, 62, 1)")));
    }
}


