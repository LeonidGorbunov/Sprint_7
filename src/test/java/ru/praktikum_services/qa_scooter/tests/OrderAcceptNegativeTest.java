package ru.praktikum_services.qa_scooter.tests;

import io.qameta.allure.*;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.*;
import ru.praktikum_services.qa_scooter.data.CourierDataGenerator;
import ru.praktikum_services.qa_scooter.data.OrderDataGenerator;
import ru.praktikum_services.qa_scooter.models.request.CourierCreateRequest;
import ru.praktikum_services.qa_scooter.models.request.OrderCreateRequest;
import java.util.List;
import static org.junit.Assert.assertEquals;

@Epic("Яндекс.Самокат")
@Feature("6. Принять заказ, эндпоинт put /api/v1/orders/accept/:id")
@Story("2. Негативные сценарии")
public class OrderAcceptNegativeTest extends BaseTest {

    private static Integer orderId;
    private static Integer orderTrack;
    private CourierCreateRequest courierRequest;
    private Integer courierId;

    @BeforeClass
    public static void setUpOrder() {
        OrderCreateRequest orderRequest = OrderDataGenerator.getDefaultOrder(List.of("BLACK"));
        Response orderResponse = orderClient.actionOrderCreate(orderRequest);
        orderTrack = orderResponse.path("track");
        Response getOrderResponse = orderClient.actionOrderGetByTrackNumber(orderTrack);
        orderId = getOrderResponse.path("order.id");
    }

    @Before
    public void setUpCourier() {
        courierRequest = CourierDataGenerator.getDefaultCourier();
        courierClient.actionCourierCreate(courierRequest);
        Response loginResponse = courierClient.actionCourierLogin(courierRequest);
        courierId = loginResponse.path("id");
    }

    @Test
    @DisplayName("1. Принятие заказа без передачи ID курьера")
    @Description("Тест проверяет, что метод возвращает 400 и тело ответа \"message\": \"Недостаточно данных для поиска\", " +
            "если в courierId передана пустая строка")
    public void acceptOrderWithoutCourierId() {

        Response response = orderClient.actionOrderAccept(orderId, "");

        Allure.step("Проверка контракта и тела ответа при отсутствии ID курьера", () -> {
            assertEquals("Статус-код ответа должен быть 400!", 400, response.getStatusCode());
            assertEquals("В теле ответа должно быть \"message\": \"Недостаточно данных для поиска\"",
                    "Недостаточно данных для поиска", response.path("message"));
        });
    }

    @Test
    @DisplayName("2. Принятие заказа с несуществующим ID курьера")
    @Description("Тест проверяет, что метод возвращает 404 и тело ответа \"message\": \"Курьера с таким id не существует\", " +
            "если в courierId передан номер несуществующего курьера")
    public void acceptOrderWithUnexistentCourierId() {

        courierClient.actionCourierDelete(courierId);

        Response response = orderClient.actionOrderAccept(orderId, courierId);

        Allure.step("Проверка контракта и тела ответа при передаче несуществующего ID курьера", () -> {
            assertEquals("Статус-код ответа должен быть 404!", 404, response.getStatusCode());
            assertEquals("В теле ответа должно быть \"message\": \"Курьера с таким id не существует\"",
                    "Курьера с таким id не существует", response.path("message"));
        });
    }

    @Test
    @DisplayName("3. Принятие заказа без передачи ID заказа")
    @Description("Тест проверяет, что метод возвращает 400 и тело ответа \"message\": \"Недостаточно данных для поиска\" " +
            "если вместо ID заказа в URL передана пустая строка")
    public void acceptOrderWithoutOrderId() {

        Response response = orderClient.actionOrderAccept("", courierId);

        Allure.step("Проверка контракта и тела ответа при отсутствии ID заказа", () -> {
            assertEquals("Статус-код ответа должен быть 400!", 400, response.getStatusCode());
            assertEquals("В теле ответа должно быть \"message\": \"Недостаточно данных для поиска\"",
                    "Недостаточно данных для поиска", response.path("message"));
        });
    }

    @Test
    @DisplayName("4. Принятие заказа с несуществующим ID заказа")
    @Description("Тест проверяет, что метод возвращает 404 и тело ответа \"message\": \"Заказа с таким id не существует\" " +
            "при передаче несуществующего id заказа")
    public void acceptOrderWithUnexistentOrderId() {

        Integer wrongOrderId = Integer.MAX_VALUE;

        Response response = orderClient.actionOrderAccept(wrongOrderId, courierId);

        Allure.step("Проверка контракта и тела ответа при передаче несуществующего ID заказа", () -> {
            assertEquals("Статус-код ответа должен быть 404!", 404, response.getStatusCode());
            assertEquals("В теле ответа должно быть \"message\": \"Заказа с таким id не существует\"",
                    "Заказа с таким id не существует", response.path("message"));
        });
    }

    @After
    public void cleanUpCourier() {
        courierClient.actionCourierDeleteIfCreated(courierRequest);
    }

    @AfterClass
    public static void cleanUpOrder() {
        orderClient.actionOrderCancelIfCreated(orderTrack);
    }
}
