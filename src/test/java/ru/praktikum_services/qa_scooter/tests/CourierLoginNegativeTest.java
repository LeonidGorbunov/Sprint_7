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
@Feature("2. Логин курьера, эндпоинт post /api/v1/courier/login")
@Story("2. Негативные сценарии")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CourierLoginNegativeTest extends BaseTest {

    @Test
    @DisplayName("1. Логин курьера без передачи обязательного ключа \"login\"")
    @Description("Тест проверяет, что метод post /api/v1/courier/login без передачи обязательного ключа \"login\" " +
            "возвращает 400 и тело ответа \"message\": \"Недостаточно данных для входа\"")
    public void step1_loginCourierWithoutLogin() {

        courierRequest = CourierDataGenerator.getCourierWithoutLogin();

        Response response = courierClient.actionCourierLogin(courierRequest);

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
    public void step2_loginCourierWithoutPassword() {

        courierRequest = CourierDataGenerator.getCourierWithoutPassword();

        Response response = courierClient.actionCourierLogin(courierRequest);

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
    public void step3_loginCourierWithUnexistentLogin() {

        courierRequest = CourierDataGenerator.getCourierWithUnexistentLogin();

        Response response = courierClient.actionCourierLogin(courierRequest);

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
    public void step4_loginCourierWithUnexistentPassword() {

        courierRequest = CourierDataGenerator.getDefaultCourier();
        CourierCreateRequest unexistentPasswordCourierRequest = new CourierCreateRequest(courierRequest.getLogin(),
                "wrong_pass_" + System.currentTimeMillis(), courierRequest.getFirstName());

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
