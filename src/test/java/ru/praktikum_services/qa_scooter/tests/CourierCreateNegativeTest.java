package ru.praktikum_services.qa_scooter.tests;

import io.qameta.allure.*;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.praktikum_services.qa_scooter.models.request.CourierCreateRequest;
import ru.praktikum_services.qa_scooter.data.CourierDataGenerator;
import static org.junit.Assert.assertEquals;

@Epic("Яндекс.Самокат")
@Feature("1. Создание нового курьера, эндпоинт post /api/v1/courier")
@Story("2. Негативные сценарии")
public class CourierCreateNegativeTest extends BaseTest {

    private CourierCreateRequest courierRequestWithoutLogin;
    private CourierCreateRequest courierRequestWithoutPassword;
    private CourierCreateRequest courierForLoginDuplicateTest;
    private CourierCreateRequest loginDuplicateCourierRequest;
    private CourierCreateRequest courierForFullDuplicateTest;

    @Before
    public void setUp() {
        courierRequestWithoutLogin = CourierDataGenerator.getCourierWithoutLogin();

        courierRequestWithoutPassword = CourierDataGenerator.getCourierWithoutPassword();

        courierForLoginDuplicateTest = CourierDataGenerator.getDefaultCourier();
        loginDuplicateCourierRequest = CourierDataGenerator.getDefaultCourier();
        loginDuplicateCourierRequest.setLogin(courierForLoginDuplicateTest.getLogin());

        courierForFullDuplicateTest = CourierDataGenerator.getDefaultCourier();
    }

    @Test
    @DisplayName("1. Создание нового курьера без передачи обязательного ключа \"login\"")
    @Description("Тест проверяет, что метод post /api/v1/courier без передачи обязательного ключа \"login\" " +
            "возвращает 400 и тело ответа \"message\": \"Недостаточно данных для создания учетной записи\"")
    public void createCourierWithoutLogin() {

        Response response = courierClient.actionCourierCreate(courierRequestWithoutLogin);

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
    public void createCourierWithoutPassword() {

        Response response = courierClient.actionCourierCreate(courierRequestWithoutPassword);

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
    public void createCourierWithExistingLogin() {

        courierClient.actionCourierCreate(courierForLoginDuplicateTest);

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
    public void createDuplicateCourier() {

        courierClient.actionCourierCreate(courierForFullDuplicateTest);

        Response response = courierClient.actionCourierCreate(courierForFullDuplicateTest);

        Allure.step("Проверка контракта и тела ответа на передачу полного дубликата курьера", () -> {
            assertEquals("Статус-код ответа не 409!", 409, response.getStatusCode());
            assertEquals("В теле ответа должно быть \"message\": \"Этот логин уже используется\"",
                "Этот логин уже используется", response.path("message"));
        });
    }

    @After
    public void cleanUp() {
            courierClient.actionCourierDeleteIfCreated(courierRequestWithoutLogin);
            courierClient.actionCourierDeleteIfCreated(courierRequestWithoutPassword);
            courierClient.actionCourierDeleteIfCreated(courierForLoginDuplicateTest);
            courierClient.actionCourierDeleteIfCreated(loginDuplicateCourierRequest);
            courierClient.actionCourierDeleteIfCreated(courierForFullDuplicateTest);
    }
}
