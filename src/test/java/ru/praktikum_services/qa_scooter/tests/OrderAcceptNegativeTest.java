package ru.praktikum_services.qa_scooter.tests;

import io.qameta.allure.*;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import ru.praktikum_services.qa_scooter.data.CourierDataGenerator;
import ru.praktikum_services.qa_scooter.data.OrderDataGenerator;
import ru.praktikum_services.qa_scooter.models.request.OrderCreateRequest;
import java.util.List;
import static org.junit.Assert.assertEquals;

@Epic("Яндекс.Самокат")
@Feature("6. Принять заказ, эндпоинт put /api/v1/orders/accept/:id")
@Story("2. Негативные сценарии")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class OrderAcceptNegativeTest extends BaseTest {

    @Test
    @DisplayName("1. Принятие заказа без передачи ID курьера")
    @Description("Тест проверяет, что метод возвращает 400 и тело ответа \"message\": \"Недостаточно данных для поиска\", " +
            "если в courierId передана пустая строка")
    public void step1_acceptOrderWithoutCourierId() {

        OrderCreateRequest orderRequest = OrderDataGenerator.getDefaultOrder(List.of("BLACK"));
        Response orderResponse = orderClient.actionOrderCreate(orderRequest);
        this.orderTrack = orderResponse.path("track");

        Response getOrderResponse = orderClient.actionOrderGetByTrackNumber(orderTrack);
        int orderId = getOrderResponse.path("order.id");

        Response response = orderClient.actionOrderAccept(orderId, "");

        Allure.step("Проверка контракта и тела ответа при отсутствии ID курьера", () -> {
            assertEquals("Статус-код ответа должен быть 400!", 400, response.getStatusCode());
            assertEquals("В теле ответа должно быть \"message\": \"Недостаточно данных для поиска\"",
                    "Недостаточно данных для поиска", response.path("message"));
        });
    }

    @Test
    @DisplayName("2. Принятие заказа с несуществующим ID курьера")
    @Description("Тест проверяет, что метод возвращает 404 и тело ответа \"message\": \"Курьера с таким id не существует\", " +
            "если в courierId передан номер несуществующего курьера")
    public void step2_acceptOrderWithUnexistentCourierId() {

        OrderCreateRequest orderRequest = OrderDataGenerator.getDefaultOrder(List.of("BLACK"));
        Response orderResponse = orderClient.actionOrderCreate(orderRequest);
        this.orderTrack = orderResponse.path("track");

        Response getOrderResponse = orderClient.actionOrderGetByTrackNumber(orderTrack);
        int orderId = getOrderResponse.path("order.id");

        courierRequest = CourierDataGenerator.getDefaultCourier();
        courierClient.actionCourierCreate(courierRequest);
        Response loginResponse = courierClient.actionCourierLogin(courierRequest);
        int unexistentCourierId = loginResponse.path("id");
        courierClient.actionCourierDelete(unexistentCourierId);

        Response response = orderClient.actionOrderAccept(orderId, unexistentCourierId);

        Allure.step("Проверка контракта и тела ответа при передаче несуществующего ID курьера", () -> {
            assertEquals("Статус-код ответа должен быть 404!", 404, response.getStatusCode());
            assertEquals("В теле ответа должно быть \"message\": \"Курьера с таким id не существует\"",
                    "Курьера с таким id не существует", response.path("message"));
        });
    }

    @Test
    @DisplayName("3. Принятие заказа без передачи ID заказа")
    @Description("Тест проверяет, что метод возвращает 400 и тело ответа \"message\": \"Недостаточно данных для поиска\" " +
            "если вместо ID заказа в URL передана пустая строка")
    public void step3_acceptOrderWithoutOrderId() {

        courierRequest = CourierDataGenerator.getDefaultCourier();
        courierClient.actionCourierCreate(courierRequest);
        Response loginResponse = courierClient.actionCourierLogin(courierRequest);
        int courierId = loginResponse.path("id");

        Response response = orderClient.actionOrderAccept("", courierId);

        Allure.step("Проверка контракта и тела ответа при отсутствии ID заказа", () -> {
            assertEquals("Статус-код ответа должен быть 400!", 400, response.getStatusCode());
            assertEquals("В теле ответа должно быть \"message\": \"Недостаточно данных для поиска\"",
                    "Недостаточно данных для поиска", response.path("message"));
        });
    }

    @Test
    @DisplayName("4. Принятие заказа с несуществующим ID заказа")
    @Description("Тест проверяет, что метод возвращает 404 и тело ответа \"message\": \"Заказа с таким id не существует\" " +
            "при передаче несуществующего id заказа")
    public void step4_acceptOrderWithUnexistentOrderId() {

        courierRequest = CourierDataGenerator.getDefaultCourier();
        courierClient.actionCourierCreate(courierRequest);
        Response loginResponse = courierClient.actionCourierLogin(courierRequest);
        int courierId = loginResponse.path("id");
        int wrongOrderId = Integer.MAX_VALUE;

        Response response = orderClient.actionOrderAccept(wrongOrderId, courierId);

        Allure.step("Проверка контракта и тела ответа при передаче несуществующего ID заказа", () -> {
            assertEquals("Статус-код ответа должен быть 404!", 404, response.getStatusCode());
            assertEquals("В теле ответа должно быть \"message\": \"Заказа с таким id не существует\"",
                    "Заказа с таким id не существует", response.path("message"));
        });
    }

    @After
    public void cleanUp() {
        orderClient.actionOrderCancelIfCreated(orderTrack);
        courierClient.actionCourierDeleteIfCreated(courierRequest);
    }
}
