package ru.praktikum_services.qa_scooter.client;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import ru.praktikum_services.qa_scooter.models.request.OrderCreateRequest;
import static io.restassured.RestAssured.given;

public class OrderClient extends BaseClient {

    @Step("Действие: запрос на создание заказа, post /api/v1/orders")
    public Response actionOrderCreate (OrderCreateRequest orderCreateRequest) {
        return given(getBaseSpec())
                .body(orderCreateRequest)
                .when()
                .post("/api/v1/orders")
                .then()
                .extract().response();
    }

    @Step("Действие: запрос на получение списка заказов, get /api/v1/orders")
    public Response actionOrderGetList() {
        return given(getBaseSpec())
                .when()
                .get("/api/v1/orders")
                .then()
                .extract().response();
    }

    @Step("Действие: запрос на получение заказа по его номеру (track), get /api/v1/orders/track")
    public Response actionOrderGetByTrackNumber(Object trackNumber) {
        return given(getBaseSpec())
                .queryParam("t", trackNumber)
                .when()
                .get("/api/v1/orders/track")
                .then()
                .extract().response();
    }

    @Step("Действие: запрос на принятие заказа курьером, put /api/v1/orders/accept/:id")
    public Response actionOrderAccept(Object orderId, Object courierId) {
        return given(getBaseSpec())
                .queryParam("courierId", courierId)
                .when()
                .put("/api/v1/orders/accept/{id}", orderId)
                .then()
                .extract().response();
    }

    @Step("Техническое действие: отмена созданного заказа с track: {orderTrack}")
    public void actionOrderCancelIfCreated (Integer orderTrack) {
        if (orderTrack == null) return;
        try {
            String jsonBody = String.format("{\"track\": %d}", orderTrack);
            given(getBaseSpec())
                    .contentType(ContentType.JSON)
                    .body(jsonBody)
                    .when()
                    .put("/api/v1/orders/cancel");
        } catch (RuntimeException e) {
            System.out.println("LOG: Не удалось отменить заказ в @After из-за сбоя: " + e.getMessage());
        }
    }
}
