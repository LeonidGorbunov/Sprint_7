package ru.praktikum_services.qa_scooter.tests;

import io.qameta.allure.*;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import ru.praktikum_services.qa_scooter.data.CourierDataGenerator;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@Epic("Яндекс.Самокат")
@Feature("5. Удаление курьера, эндпоинт delete /api/v1/courier/:id")
@Story("1. Позитивные сценарии")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CourierDeletePositiveTest extends BaseTest {

    @Test
    @DisplayName("1. Успешное удаление курьера")
    @Description("Тест проверяет, что метод delete /api/v1/courier/:id возвращает 200 и тело ответа \"ok\": \"true\"")
    public void step1_deleteCourierAndCheckResponseCodeAndBody() {

        courierRequest = CourierDataGenerator.getDefaultCourier();

        courierClient.actionCourierCreate(courierRequest);
        Response loginResponse = courierClient.actionCourierLogin(courierRequest);
        int courierId = loginResponse.path("id");

        Response response = courierClient.actionCourierDelete(courierId);

                Allure.step("Проверка контракта и тела успешного ответа", () -> {
            assertEquals("Статус-код ответа не 200!", 200, response.getStatusCode());
            assertTrue("В теле ответа должно быть \"ok\": \"true\"", response.path("ok"));
        });
    }

    @After
    public void cleanUp() {
        courierClient.actionCourierDeleteIfCreated(courierRequest);
    }
}
