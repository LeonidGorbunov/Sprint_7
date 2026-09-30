package ru.praktikum_services.qa_scooter.tests;

import io.qameta.allure.*;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import java.util.List;
import static org.junit.Assert.*;

@Epic("Яндекс.Самокат")
@Feature("4. Получение списка заказов, эндпоинт get /api/v1/orders")
@Story("1. Позитивные сценарии")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class OrderGetListPositiveTest extends BaseTest{

    @Test
    @DisplayName("1. Успешное получение списка заказов")
    @Description("Тест проверяет, что метод get /api/v1/orders возвращает 200 и список заказов в теле ответа")
    public void step1_getListWithOrdersAndCheckResponseCodeAndBody () {

        Response response = orderClient.actionOrderGetList();
        Object orderBody = response.path("orders");

        Allure.step("Проверка контракта и наличия списка заказов в теле ответа", () -> {
            assertEquals("Статус-код ответа не 200!", 200, response.getStatusCode());
            assertTrue("Поле orders должно быть списком!", orderBody instanceof List);
            List<?> ordersList = (List<?>) orderBody;
            assertFalse("В теле ответа должен быть список заказов!", ordersList.isEmpty());
        });
    }
}
