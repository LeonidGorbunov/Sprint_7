package ru.praktikum_services.qa_scooter.tests;

import io.qameta.allure.*;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.praktikum_services.qa_scooter.data.CourierDataGenerator;
import ru.praktikum_services.qa_scooter.data.OrderDataGenerator;
import ru.praktikum_services.qa_scooter.models.request.CourierCreateRequest;
import ru.praktikum_services.qa_scooter.models.request.OrderCreateRequest;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@Epic("Яндекс.Самокат")
@Feature("6. Принять заказ, эндпоинт put /api/v1/orders/accept/:id")
@Story("1. Позитивные сценарии")
public class OrderAcceptPositiveTest extends BaseTest {

    private CourierCreateRequest courierRequest;
    private Integer courierId;
    private Integer orderTrack;
    private Integer orderId;

    @Before
    public void setUp() {
        BaseTest.baseSetUp();
        courierRequest = CourierDataGenerator.getDefaultCourier();
        courierClient.actionCourierCreate(courierRequest);
        Response loginResponse = courierClient.actionCourierLogin(courierRequest);
        courierId = loginResponse.path("id");

        OrderCreateRequest orderRequest = OrderDataGenerator.getDefaultOrder(List.of("BLACK"));
        Response orderResponse = orderClient.actionOrderCreate(orderRequest);
        orderTrack = orderResponse.path("track");

        Response getOrderResponse = orderClient.actionOrderGetByTrackNumber(orderTrack);
        orderId = getOrderResponse.path("order.id");
    }

    @Test
    @DisplayName("1. Успешное принятие заказа")
    @Description("Тест проверяет, что метод put /api/v1/orders/accept/:id возвращает 200 и тело ответа \"ok\": \"true\"")
    public void acceptOrderAndCheckResponseCodeAndBody() {

        Response orderAcceptResponse = orderClient.actionOrderAccept(orderId, courierId);

        Allure.step("Проверка контракта и тела ответа при успешном принятии заказа", () -> {
            assertEquals("Статус-код ответа не 200!", 200, orderAcceptResponse.getStatusCode());
            assertTrue("В теле ответа должно быть \"ok\": \"true\"", orderAcceptResponse.path("ok"));
        });
    }

    @After
    public void cleanUp() {
        orderClient.actionOrderCancelIfCreated(orderTrack);
        courierClient.actionCourierDeleteIfCreated(courierRequest);
    }
}