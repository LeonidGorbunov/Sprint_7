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
@Feature("2. Логин курьера, эндпоинт post /api/v1/courier/login")
@Story("2. Негативные сценарии")
public class CourierLoginNegativeTest extends BaseTest {

    private CourierCreateRequest courierRequestWithoutLogin;
    private CourierCreateRequest courierRequestWithoutPassword;
    private CourierCreateRequest courierRequestWithUnexistentLogin;
    private CourierCreateRequest courierRequest;
    private CourierCreateRequest unexistentPasswordCourierRequest;

    @Before
    public void setUp() {
        courierRequestWithoutLogin = CourierDataGenerator.getCourierWithoutLogin();

        courierRequestWithoutPassword = CourierDataGenerator.getCourierWithoutPassword();

        courierRequestWithUnexistentLogin = CourierDataGenerator.getCourierWithUnexistentLogin();

        courierRequest = CourierDataGenerator.getDefaultCourier();
        unexistentPasswordCourierRequest = new CourierCreateRequest(courierRequest.getLogin(),
                "wrong_pass_" + System.currentTimeMillis(), courierRequest.getFirstName());
    }

    @Test
    @DisplayName("1. Логин курьера без передачи обязательного ключа \"login\"")
    @Description("Тест проверяет, что метод post /api/v1/courier/login без передачи обязательного ключа \"login\" " +
            "возвращает 400 и тело ответа \"message\": \"Недостаточно данных для входа\"")
    public void loginCourierWithoutLogin() {

        Response response = courierClient.actionCourierLogin(courierRequestWithoutLogin);

        Allure.step("Проверка контракта и тела ответа без передачи логина", () -> {
            assertEquals("Статус-код ответа не 400!", 400, response.getStatusCode());
            assertEquals("В теле ответа должно быть \"message\": \"Недостаточно данных для входа\"",
                    "Недостаточно данных для входа", response.path("message"));
        });
    }

    @Test
    @DisplayName("2. Логин курьера без передачи обязательного ключа \"password\"")
    @Description("Тест проверяет, что метод post /api/v1/courier/login без передачи обязательного ключа \"password\" " +
            "возвращает 400 и тело ответа \"message\": \"Недостаточно данных для входа\"")
    public void loginCourierWithoutPassword() {

        Response response = courierClient.actionCourierLogin(courierRequestWithoutPassword);

        Allure.step("Проверка контракта и тела ответа без передачи пароля", () -> {
            assertEquals("Статус-код ответа не 400!", 400, response.getStatusCode());
            assertEquals("В теле ответа должно быть \"message\": \"Недостаточно данных для входа\"",
                    "Недостаточно данных для входа", response.path("message"));
        });
    }

    @Test
    @DisplayName("3. Логин курьера с несуществующем в БД логином")
    @Description("Тест проверяет, что метод post /api/v1/courier/login при передаче несуществующего в БД логина " +
            "возвращает 404 и тело ответа \"message\": \"Учетная запись не найдена\"")
    public void loginCourierWithUnexistentLogin() {

        Response response = courierClient.actionCourierLogin(courierRequestWithUnexistentLogin);

        Allure.step("Проверка контракта и тела ответа при передаче несуществующего в БД логина", () -> {
            assertEquals("Статус-код ответа не 404!", 404, response.getStatusCode());
            assertEquals("В теле ответа должно быть \"message\": \"Учетная запись не найдена\"",
                    "Учетная запись не найдена", response.path("message"));
        });
    }

    @Test
    @DisplayName("4. Логин курьера с существующем в БД логином и несуществующем в БД паролем")
    @Description("Тест проверяет, что метод post /api/v1/courier/login при передаче существующего в БД логина и " +
            "несуществующего в БД пароля возвращает 404 и тело ответа \"message\": \"Учетная запись не найдена\"")
    public void loginCourierWithUnexistentPassword() {

        courierClient.actionCourierCreate(courierRequest);

        Response response = courierClient.actionCourierLogin(unexistentPasswordCourierRequest);

        Allure.step("Проверка контракта и тела ответа при передаче несуществующего в БД пароля", () -> {
            assertEquals("Статус-код ответа не 404!", 404, response.getStatusCode());
            assertEquals("В теле ответа должно быть \"message\": \"Учетная запись не найдена\"",
                    "Учетная запись не найдена", response.path("message"));
        });
    }

    @After
    public void cleanUp() {
        courierClient.actionCourierDeleteIfCreated(courierRequest);
    }
}
