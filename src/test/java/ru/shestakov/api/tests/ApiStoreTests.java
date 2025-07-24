package ru.shestakov.api.tests;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import ru.shestakov.api.models.StoreDeleteResponse;
import ru.shestakov.api.models.StoreOrderRequest;
import ru.shestakov.api.models.StoreOrderResponse;
import ru.shestakov.util.TestDataGenerator;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;

@DisplayName("API тесты")
public class ApiStoreTests {

    private static final String BASE_URL = "https://petstore.swagger.io/v2";
    private static final String API_KEY = "special-key";

    @Test
    @DisplayName("Создание заказа: успешная отправка")
    void createOrderTest() {
            StoreOrderRequest order = new StoreOrderRequest(
                    6, 1, 1, "2023-10-10T12:00:00.000+0000", "placed", true
            );

            StoreOrderResponse store = given()
                    .contentType(ContentType.JSON)
                    .body(order).log().all()
                    .when()
                    .post(BASE_URL + "/store/order")
                    .then().log().all()
                    .statusCode(200)
                    .extract().as(StoreOrderResponse.class);

            // Проверки тела ответа
            assertThat(order.getId()).as("Проверка id").isEqualTo(store.getId());
            assertThat(order.getPetId()).as("Проверка PetId").isEqualTo(store.getPetId());
            assertThat(order.getQuantity()).as("Проверка Quantity").isEqualTo(store.getQuantity());
            assertThat(order.getShipDate()).as("Проверка ShipDate").isEqualTo(store.getShipDate());
            assertThat(order.getStatus()).as("Проверка Status").isEqualTo(store.getStatus());
            assertThat(order.getComplete()).as("Проверка Complete").isEqualTo(store.getComplete());
    }

    @RepeatedTest(2)
    @DisplayName("Успешное получение заказа по ID")
    void getOrderTest() {
            int orderId = 9;

            given()
                    .contentType(ContentType.JSON)
                    .pathParam("orderId", orderId)
                    .log().all()
                    .when()
                    .get(BASE_URL + "/store/order/{orderId}")
                    .then().log().all()
                    .statusCode(200)
                    .body("id", equalTo(orderId))
                    .body("petId", notNullValue())
                    .body("quantity", notNullValue())
                    .body("shipDate", notNullValue())
                    .body("status", notNullValue())
                    .body("complete", is(true));
    }

    @Test
    @DisplayName("Успешное удаление заказа")
    void deleteOrderTest() {
        int orderId = TestDataGenerator.randomId();

        StoreDeleteResponse response = given()
                .contentType(ContentType.JSON)
                .pathParam("orderId", orderId).log().all()
                .when()
                .delete(BASE_URL + "/store/order/{orderId}")
                .then().log().all()
                .extract().as(StoreDeleteResponse.class);

        // Проверяем поля ответа
        assertThat(response.getCode()).as("Проверка Code").isEqualTo(200);
        assertThat(response.getType()).as("Проверка Type").isEqualTo("unknown");
        assertThat(response.getMessage()).as("Проверка Message").isEqualTo(String.valueOf(orderId));
    }

    @Test
    @DisplayName("Успешное получение инвентаря")
    void getInventoryTest() {

        given()
                .header("api_key", API_KEY)
                .contentType(ContentType.JSON)
                .log().all()
                .when()
                .get(BASE_URL + "/store/inventory")
                .then().log().all()
                .statusCode(200)
                .body("sold", notNullValue());
    }
}