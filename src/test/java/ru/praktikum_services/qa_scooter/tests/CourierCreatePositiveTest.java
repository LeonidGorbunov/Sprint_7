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
@Feature("1. Создание нового курьера, эндпоинт post /api/v1/courier")
@Story("1. Позитивные сценарии")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CourierCreatePositiveTest extends BaseTest {

    @Test
    @DisplayName("1. Успешное создание нового курьера")
    @Description("Тест проверяет, что метод post /api/v1/courier возвращает 201 и тело ответа \"ok\": \"true\"")
    public void step1_createCourierAndCheckResponseCodeAndBody() {

        courierRequest = CourierDataGenerator.getDefaultCourier();

        Response response = courierClient.actionCourierCreate(courierRequest);

        Allure.step("Проверка контракта и тела успешного ответа", () -> {
            assertEquals("Статус-код ответа не 201!", 201, response.getStatusCode());
            assertTrue("В теле ответа должно быть \"ok\": \"true\"", response.path("ok"));
        });
    }

    @Test
    @DisplayName("2. Успешное создание курьера без передачи необязательного ключа \"firstName\"")
    @Description("Тест проверяет, что метод post /api/v1/courier возвращает 201 и \"ok\": \"true\" при создании курьера " +
            "без передачи необязательного ключа \"firstName\"")
    public void step2_createCourierWithoutFirstName() {

        courierRequest = CourierDataGenerator.getCourierWithoutFirstName();

        Response response = courierClient.actionCourierCreate(courierRequest);

        Allure.step("Проверка контракта и тела успешного ответа", () -> {
            assertEquals("Статус-код ответа не 201!", 201, response.getStatusCode());
            assertTrue("В теле ответа должно быть \"ok\": \"true\"", response.path("ok"));
        });
    }

    @After
    public void cleanUp() {
        courierClient.actionCourierDeleteIfCreated(courierRequest);
    }
}
