package ru.shestakov.api.tests;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.shestakov.api.models.StoreOrderRequest;
import ru.shestakov.api.models.StoreOrderResponse;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;
import static ru.shestakov.util.TestDataGenerator.randomId;

@DisplayName("API тесты")
public class ApiStoreTests {

    private static final String BASE_URL = "https://petstore.swagger.io/v2";
    private static final String API_KEY = "special-key";

    @Test
    @DisplayName("Создание заказа: успешная отправка")
    void createOrderTest() {
            StoreOrderRequest order = new StoreOrderRequest(
                    randomId(), 1, 1, "2023-10-10T12:00:00.000+0000", "placed", true
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

    @Test
    @DisplayName("Успешное получение заказа по ID")
    void getOrderTest() {
        StoreOrderRequest order = new StoreOrderRequest(
                randomId(), 1, 1, "2023-10-10T12:00:00.000+0000", "placed", true
        );

        StoreOrderResponse store = given()
                .contentType(ContentType.JSON)
                .body(order).log().all()
                .when()
                .post(BASE_URL + "/store/order")
                .then().log().all()
                .statusCode(200)
                .extract().as(StoreOrderResponse.class);

        int orderId = store.getId();

            given()
                    .contentType(ContentType.JSON)
                    .pathParam("orderId", orderId)
                    .log().all()
                    .when()
                    .get(BASE_URL + "/store/order/{orderId}")
                    .then().log().all()
                    .statusCode(200)
                    .body("id", equalTo(store.getId()))
                    .body("petId", notNullValue())
                    .body("quantity", notNullValue())
                    .body("shipDate", notNullValue())
                    .body("status", notNullValue())
                    .body("complete", is(true));
    }

    @Test
    @DisplayName("Успешное удаление заказа")
    void deleteOrderTest() {
        // 1. Создание заказа
        StoreOrderRequest order = new StoreOrderRequest(
                879,
                1,
                2,
                "2025-07-26T12:00:00.000+0000",
                "placed",
                true
        );

        given()
                .header("api_key", API_KEY)
                .contentType(ContentType.JSON)
                .body(order)
                .when()
                .post(BASE_URL + "/store/order")
                .then()
                .statusCode(200);

        // 2. Удаление заказа
        given()
                .header("api_key", API_KEY)
                .pathParam("orderId", order.getId())
                .when()
                .delete(BASE_URL + "/store/order/{orderId}")
                .then()
                .statusCode(200)
                .body("message", equalTo(String.valueOf(order.getId()))); // проверка по сообщению

        // 3. Проверка, что заказ удалён
        given()
                .header("api_key", API_KEY)
                .pathParam("orderId", order.getId())
                .when()
                .get(BASE_URL + "/store/order/{orderId}")
                .then()
                .statusCode(404);
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