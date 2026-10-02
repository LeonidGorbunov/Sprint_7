package ru.praktikum_services.qa_scooter.tests;

import io.qameta.allure.*;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.praktikum_services.qa_scooter.models.request.OrderCreateRequest;
import ru.praktikum_services.qa_scooter.data.OrderDataGenerator;
import java.util.List;
import static org.junit.Assert.*;

@Epic("Яндекс.Самокат")
@Feature("3. Создание нового заказа, эндпоинт post /api/v1/orders")
@Story("1. Позитивные сценарии с параметризованной передачей цвета самоката")
@RunWith(Parameterized.class)
public class OrderCreatePositiveParameterizedTest extends BaseTest {

    private Integer orderTrack;
    private OrderCreateRequest orderRequest;

    @Parameterized.Parameter(0)
    public String testCaseName;

    @Parameterized.Parameter(1)
    public List<String> scooterColor;

    @Parameterized.Parameters(name = "Сценарий {0}")
    public static Object[][] getTestData() {
        return new Object[][] {
                {"Выбран цвет BLACK", List.of("BLACK")},
                {"Выбран цвет GREY",List.of("GREY")},
                {"Выбраны цвета BLACK и GREY",List.of("BLACK", "GREY")},
                {"Не выбран ни один цвет",List.of()},
        };
    }

    @Before
    public void setUp() {
        orderRequest = OrderDataGenerator.getDefaultOrder(scooterColor);
    }

    @Test
    @DisplayName("Успешное создание нового заказа (с параметризацией цвета самоката)")
    @Description("Тест проверяет, что метод post /api/v1/orders возвращает 201 и тело ответа с номером трэка (ключ \"track\"), " +
            "цвет самоката передается в виде параметризованного параметра")
    public void createOrderAndCheckResponseCodeAndBody() {

        Allure.parameter("Описание сценария", testCaseName);
        Allure.parameter("Цвет самоката", scooterColor.toString());

        Response response = orderClient.actionOrderCreate(orderRequest);

        orderTrack = response.path("track");

        Allure.step("Проверка контракта и тела успешного ответа", () -> {
            assertEquals("Статус-код ответа не 201!", 201, response.getStatusCode());
            assertTrue("В теле ответа должен быть числовой track заказа!", response.path("track") instanceof Integer);
        });
    }

    @After
    public void cleanUp() {
        orderClient.actionOrderCancelIfCreated(orderTrack);
    }
}
