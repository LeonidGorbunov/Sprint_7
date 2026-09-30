package ru.praktikum_services.qa_scooter.tests;

import io.qameta.allure.*;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import ru.praktikum_services.qa_scooter.data.OrderDataGenerator;
import ru.praktikum_services.qa_scooter.models.request.OrderCreateRequest;
import java.util.List;
import static org.junit.Assert.*;

@Epic("Яндекс.Самокат")
@Feature("7. Получить заказ по его номеру, эндпоинт get /api/v1/orders/track")
@Story("1. Позитивные сценарии")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class OrderGetByTrackPositiveTest extends BaseTest {

    @Test
    @DisplayName("1. Успешное получение заказа")
    @Description("Тест проверяет, что метод get /api/v1/orders/track возвращает 200 и тело ответа с заказом")
    public void step1_getOrderAndCheckResponseCodeAndBody() {

        OrderCreateRequest orderRequest = OrderDataGenerator.getDefaultOrder(List.of("BLACK"));
        Response orderResponse = orderClient.actionOrderCreate(orderRequest);
        this.orderTrack = orderResponse.path("track");

        Response response = orderClient.actionOrderGetByTrackNumber(orderTrack);
        Object orderBody = response.path("order");

        Allure.step("Проверка контракта и тела успешного ответа получения заказа по номеру его трека", () -> {
            assertEquals("Статус-код ответа не 200!", 200, response.getStatusCode());
            assertNotNull("В теле ответа должен быть объект заказа в ключе \"order\"", orderBody);
        });
    }

    @After
    public void cleanUp() {
        orderClient.actionOrderCancelIfCreated(orderTrack);
    }
}
