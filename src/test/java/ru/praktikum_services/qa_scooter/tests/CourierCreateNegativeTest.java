package ru.praktikum_services.qa_scooter.tests;

import io.qameta.allure.*;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import ru.praktikum_services.qa_scooter.models.request.CourierCreateRequest;
import ru.praktikum_services.qa_scooter.data.CourierDataGenerator;
import static org.junit.Assert.assertEquals;

@Epic("Яндекс.Самокат")
@Feature("1. Создание нового курьера, эндпоинт post /api/v1/courier")
@Story("2. Негативные сценарии")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CourierCreateNegativeTest extends BaseTest {

    @Test
    @DisplayName("1. Создание нового курьера без передачи обязательного ключа \"login\"")
    @Description("Тест проверяет, что метод post /api/v1/courier без передачи обязательного ключа \"login\" " +
            "возвращает 400 и тело ответа \"message\": \"Недостаточно данных для создания учетной записи\"")
    public void step1_createCourierWithoutLogin() {

        courierRequest = CourierDataGenerator.getCourierWithoutLogin();

        Response response = courierClient.actionCourierCreate(courierRequest);

        Allure.step("Проверка контракта и тела ответа без передачи логина", () -> {
            assertEquals("Статус-код ответа не 400!", 400, response.getStatusCode());
            assertEquals("В теле ответа должно быть \"message\": \"Недостаточно данных для создания учетной записи\"",
                    "Недостаточно данных для создания учетной записи", response.path("message"));
        });
    }

    @Test
    @DisplayName("2. Создание нового курьера без передачи обязательного ключа \"password\"")
    @Description("Тест проверяет, что метод post /api/v1/courier без передачи обязательного ключа \"password\" " +
            "возвращает 400 и тело ответа \"message\": \"Недостаточно данных для создания учетной записи\"")
    public void step2_createCourierWithoutPassword() {

        courierRequest = CourierDataGenerator.getCourierWithoutPassword();

        Response response = courierClient.actionCourierCreate(courierRequest);

        Allure.step("Проверка контракта и тела ответа без передачи пароля", () -> {
            assertEquals("Статус-код ответа не 400!", 400, response.getStatusCode());
            assertEquals("В теле ответа должно быть \"message\": \"Недостаточно данных для создания учетной записи\"",
                    "Недостаточно данных для создания учетной записи", response.path("message"));
        });
    }

    @Test
    @DisplayName("3. Создание курьера с дубликатом ключа \"login\" другого курьера")
    @Description("Тест проверяет, что метод post /api/v1/courier при создании курьера с дубликатом ключа \"login\" другого " +
            "существующего в БД курьера возвращает 409 и тело ответа \"message\": \"Этот логин уже используется\"")
    public void step3_createCourierWithExistingLogin() {

        courierRequest = CourierDataGenerator.getDefaultCourier();
        CourierCreateRequest loginDuplicateCourierRequest = CourierDataGenerator.getDefaultCourier();
        loginDuplicateCourierRequest.setLogin(courierRequest.getLogin());

        courierClient.actionCourierCreate(courierRequest);
        Response response = courierClient.actionCourierCreate(loginDuplicateCourierRequest);

        Allure.step("Проверка контракта и тела ответа на передачу дубликата логина", () -> {
            assertEquals("Статус-код ответа не 409!", 409, response.getStatusCode());
            assertEquals("В теле ответа должно быть \"message\": \"Этот логин уже используется\"",
                    "Этот логин уже используется", response.path("message"));
        });
    }

    @Test
    @DisplayName("4. Создание дубликата существующего курьера")
    @Description("Тест проверяет, что метод post /api/v1/courier при создании полного дубликата существующего курьера " +
            "возвращает 409 и тело ответа \"message\": \"Этот логин уже используется\"")
    public void step4_createDuplicateCourier() {

        courierRequest = CourierDataGenerator.getDefaultCourier();

        courierClient.actionCourierCreate(courierRequest);
        Response response = courierClient.actionCourierCreate(courierRequest);

        Allure.step("Проверка контракта и тела ответа на передачу полного дубликата курьера", () -> {
            assertEquals("Статус-код ответа не 409!", 409, response.getStatusCode());
            assertEquals("В теле ответа должно быть \"message\": \"Этот логин уже используется\"",
                "Этот логин уже используется", response.path("message"));
        });
    }

    @After
    public void cleanUp() {
        courierClient.actionCourierDeleteIfCreated(courierRequest);
    }
}
