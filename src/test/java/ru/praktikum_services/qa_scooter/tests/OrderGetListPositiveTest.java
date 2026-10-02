package ru.praktikum_services.qa_scooter.tests;

import io.qameta.allure.*;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.praktikum_services.qa_scooter.data.OrderDataGenerator;
import ru.praktikum_services.qa_scooter.models.request.OrderCreateRequest;
import java.util.List;
import static org.junit.Assert.*;

@Epic("Яндекс.Самокат")
@Feature("4. Получение списка заказов, эндпоинт get /api/v1/orders")
@Story("1. Позитивные сценарии")
public class OrderGetListPositiveTest extends BaseTest{

    private Integer orderTrack;

    @Before
    public void setUp() {
        OrderCreateRequest orderRequest = OrderDataGenerator.getDefaultOrder(List.of("BLACK"));
        Response orderResponse = orderClient.actionOrderCreate(orderRequest);
        orderTrack = orderResponse.path("track");
    }

    @Test
    @DisplayName("1. Успешное получение списка заказов")
    @Description("Тест проверяет, что метод get /api/v1/orders возвращает 200 и список заказов в теле ответа")
    public void getListWithOrdersAndCheckResponseCodeAndBody () {

        Response response = orderClient.actionOrderGetList();
        List<?> ordersList = response.path("orders");

        Allure.step("Проверка контракта и наличия списка заказов в теле ответа", () -> {
            assertEquals("Статус-код ответа не 200!", 200, response.getStatusCode());
            assertNotNull("Поле orders должно быть списком!", ordersList);
            assertFalse("В теле ответа должен быть список заказов!", ordersList.isEmpty());
        });
    }

    @After
    public void cleanUp() {
        orderClient.actionOrderCancelIfCreated(orderTrack);
    }
}
