package ru.praktikum_services.qa_scooter.tests;

import io.qameta.allure.*;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.*;
import ru.praktikum_services.qa_scooter.data.CourierDataGenerator;
import ru.praktikum_services.qa_scooter.models.request.CourierCreateRequest;
import ru.praktikum_services.qa_scooter.models.request.CourierLoginRequest;
import static org.junit.Assert.*;

@Epic("Яндекс.Самокат")
@Feature("2. Логин курьера, эндпоинт post /api/v1/courier/login")
@Story("1. Позитивные сценарии")
public class CourierLoginPositiveTest extends BaseTest {

    private CourierCreateRequest courierRequest;

    @Before
    public void setUp() {
        courierRequest = CourierDataGenerator.getDefaultCourier();
        courierClient.actionCourierCreate(courierRequest);
    }

    @Test
    @DisplayName("1. Успешный логин курьера")
    @Description("Тест проверяет, что метод post /api/v1/courier/login возвращает 200 и тело ответа с id курьера")
    public void loginCourierAndCheckResponseCodeAndBody() {

        CourierLoginRequest loginRequest = new CourierLoginRequest(courierRequest.getLogin(), courierRequest.getPassword());

        Response response = courierClient.actionCourierLogin(loginRequest);

        Allure.step("Проверка контракта и тела успешного ответа", () -> {
            assertEquals("Статус-код ответа не 200!", 200, response.getStatusCode());
            assertTrue("В теле ответа должен быть числовой id курьера!", response.path("id") instanceof Integer);
        });
    }

    @After
    public void cleanUp() {
        courierClient.actionCourierDeleteIfCreated(courierRequest);
    }
}
