package ru.praktikum_services.qa_scooter.tests;

import io.qameta.allure.*;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.praktikum_services.qa_scooter.data.CourierDataGenerator;
import ru.praktikum_services.qa_scooter.models.request.CourierCreateRequest;
import ru.praktikum_services.qa_scooter.models.request.CourierLoginRequest;
import static org.junit.Assert.assertEquals;

@Epic("Яндекс.Самокат")
@Feature("5. Удаление курьера, эндпоинт delete /api/v1/courier/:id")
@Story("2. Негативные сценарии")
public class CourierDeleteNegativeTest extends BaseTest {

    private CourierCreateRequest courierRequest;
    private Integer courierId;
    private Integer unexistentCourierId;

    @Before
    public void setUp() {
        courierRequest = CourierDataGenerator.getDefaultCourier();
        courierClient.actionCourierCreate(courierRequest);

        CourierLoginRequest loginRequest = new CourierLoginRequest(courierRequest.getLogin(), courierRequest.getPassword());
        Response loginResponse = courierClient.actionCourierLogin(loginRequest);
        courierId = loginResponse.path("id");

        unexistentCourierId = Integer.MAX_VALUE;
    }

    @Test
    @DisplayName("1. Удаление курьера с несуществующим в БД ID")
    @Description("Тест проверяет, что метод delete /api/v1/courier/:id при удалении курьера с несуществующим в БД ID " +
            "возвращает 404 и тело ответа \"message\": \"Курьера с таким id нет\"")
    public void deleteCourierWithUnexistentId() {

        courierClient.actionCourierDelete(courierId);

        Response response = courierClient.actionCourierDelete(courierId);

        Allure.step("Проверка контракта и тела ответа при удалении курьера с несуществующим в БД ID", () -> {
            assertEquals("Статус-код ответа не 404!", 404, response.getStatusCode());
            assertEquals("В теле ответа должно быть \"message\": \"Курьера с таким id нет\"",
                    "Курьера с таким id нет", response.path("message"));
        });
    }

    @Test
    @DisplayName("2. Удаление курьера без передачи ID: пустая строка в URL и в теле JSON")
    @Description("Тест проверяет, что метод delete /api/v1/courier/:id при передаче пустой строки вместо ID " +
            "возвращает 400 и тело ответа \"message\": \"Недостаточно данных для удаления курьера\"")
    public void deleteCourierWithEmptyUrlAndEmptyBody() {

        Response response = courierClient.actionCourierDelete("");

        Allure.step("Проверка контракта и тела ответа при удалении курьера без ID", () -> {
            assertEquals("Статус-код ответа не 400!", 400, response.getStatusCode());
            assertEquals("В теле ответа должно быть \"message\": \"Недостаточно данных для удаления курьера\"",
                    "Недостаточно данных для удаления курьера", response.path("message"));
        });
    }

    @Test
    @DisplayName("3. Удаление курьера: валидный ID в URL, пустая строка в теле JSON")
    @Description("Тест проверяет, что метод delete /api/v1/courier/:id при передаче в URL валидного ID, а в JSON теле пустой " +
            "строки возвращает 400 и тело ответа \"message\": \"Недостаточно данных для удаления курьера\"")
    public void deleteWithRealUrlAndEmptyBody() {

        Response response = courierClient.actionCourierDelete(courierId, "");

        Allure.step("Проверка контракта и тела ответа при удалении курьера при пустом id в теле JSON", () -> {
            assertEquals("Статус-код ответа не 400!", 400, response.getStatusCode());
            assertEquals("В теле ответа должно быть \"message\": \"Недостаточно данных для удаления курьера\"",
                    "Недостаточно данных для удаления курьера", response.path("message"));
        });
    }

    @Test
    @DisplayName("4. Удаление курьера: валидный ID в URL, пустое тело JSON (null)")
    @Description("Тест проверяет, что метод delete /api/v1/courier/:id при передаче в URL валидного ID и пустого " +
            "тела в JSON возвращает 400 и тело ответа \"message\": \"Недостаточно данных для удаления курьера\"")
    public void deleteWithRealUrlAndMissingBody() {

        Response response = courierClient.actionCourierDelete(courierId, null);

        Allure.step("Проверка контракта и тела ответа при удалении курьера при отсутствии тела JSON", () -> {
            assertEquals("Статус-код ответа не 400!", 400, response.getStatusCode());
            assertEquals("В теле ответа должно быть \"message\": \"Недостаточно данных для удаления курьера\"",
                    "Недостаточно данных для удаления курьера", response.path("message"));
        });
    }

    @Test
    @DisplayName("5. Удаление курьера: валидный ID в URL, несуществующий ID в теле JSON")
    @Description("Тест проверяет, что метод delete /api/v1/courier/:id при передаче в URL валидного ID и несуществующего " +
            "ID в теле JSON возвращает 404 и тело ответа \"message\": \"Курьера с таким id нет\"")
    public void deleteWithRealUrlAndUnexistentBody() {

        Response response = courierClient.actionCourierDelete(courierId, unexistentCourierId);

        Allure.step("Проверка контракта и тела ответа при удалении курьера с несуществующим ID в теле запроса", () -> {
            assertEquals("Статус-код ответа не 404!", 404, response.getStatusCode());
            assertEquals("В теле ответа должно быть \"message\": \"Курьера с таким id нет\"",
                    "Курьера с таким id нет", response.path("message"));
        });
    }

    @After
    public void cleanUp() {
        courierClient.actionCourierDeleteIfCreated(courierRequest);
    }
}
