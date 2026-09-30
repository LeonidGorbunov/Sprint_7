package ru.praktikum_services.qa_scooter.client;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import ru.praktikum_services.qa_scooter.models.request.CourierCreateRequest;
import ru.praktikum_services.qa_scooter.models.request.CourierDeleteRequest;
import ru.praktikum_services.qa_scooter.models.request.CourierLoginRequest;
import static io.restassured.RestAssured.given;

public class CourierClient extends BaseClient {

    @Step("Действие: запрос на создание курьера, post /api/v1/courier")
    public Response actionCourierCreate (CourierCreateRequest courierRequest) {

        return given()
               .contentType(ContentType.JSON)
               .body(courierRequest)
               .when()
               .post("/api/v1/courier")
               .then()
               .extract().response();
    }

    @Step("Действие: запрос на логин курьера, post /api/v1/courier/login")
    public Response actionCourierLogin(CourierCreateRequest courierRequest) {
        CourierLoginRequest loginBody = new CourierLoginRequest();
        loginBody.setLogin(courierRequest.getLogin());
        loginBody.setPassword(courierRequest.getPassword());

        return given()
                .contentType(ContentType.JSON)
                .body(loginBody)
                .when()
                .post("/api/v1/courier/login")
                .then()
                .extract().response();
    }

    @Step("Действие: запрос на удаление курьера с ID в URL и в теле запроса: {courierId}, delete /api/v1/courier/:id")
    public Response actionCourierDelete(Object courierId) {
        CourierDeleteRequest deleteBody = new CourierDeleteRequest(courierId);

        return given()
                .contentType(ContentType.JSON)
                .body(deleteBody)
                .when()
                .delete("/api/v1/courier/{id}", courierId)
                .then()
                .extract().response();
    }

    @Step("Действие: запрос на удаление курьера с ID: {courierIdPath} и телом: {courierIdBody}, delete /api/v1/courier/:id")
    public Response actionCourierDelete(Object courierIdPath, Object courierIdBody) {
        CourierDeleteRequest deleteBody = new CourierDeleteRequest(courierIdBody);

        return given()
                .contentType(ContentType.JSON)
                .body(deleteBody)
                .when()
                .delete("/api/v1/courier/{id}", courierIdPath)
                .then()
                .extract().response();
    }

    @Step("Техническое действие: удаление курьера из БД (если он был там создан), delete /api/v1/courier/:id")
    public void actionCourierDeleteIfCreated(CourierCreateRequest courierRequest) {
           if (courierRequest == null) return;
        try {
            Response loginResponse = actionCourierLogin(courierRequest);
            if (loginResponse.getStatusCode() == 200) {
                Integer id = loginResponse.path("id");
                if (id != null) {
                    actionCourierDelete(id);
                }
            }
        } catch (RuntimeException e) {
            System.out.println("LOG: Не удалось удалить курьера в @After из-за сбоя: " + e.getMessage());
        }
    }
}
