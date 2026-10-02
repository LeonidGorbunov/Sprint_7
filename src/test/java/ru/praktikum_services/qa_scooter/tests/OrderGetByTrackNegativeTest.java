package ru.praktikum_services.qa_scooter.tests;

import io.qameta.allure.*;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

@Epic("Яндекс.Самокат")
@Feature("7. Получить заказ по его номеру, эндпоинт get /api/v1/orders/track")
@Story("2. Негативные сценарии")
public class OrderGetByTrackNegativeTest extends BaseTest {

    @Test
    @DisplayName("1. Получение заказа без передачи номера трека")
    @Description("Тест проверяет, что метод возвращает 400 и тело ответа \"message\": \"Недостаточно данных для поиска\", " +
            "если передан трек без номера (пустая строка)")
    public void getOrderByTrackWithoutNumber() {

        Response response = orderClient.actionOrderGetByTrackNumber("");

        Allure.step("Проверка контракта и тела ответа при отсутствии номера трека", () -> {
            assertEquals("Статус-код ответа должен быть 400!", 400, response.getStatusCode());
            assertEquals("В теле ответа должно быть \"message\": \"Недостаточно данных для поиска\"",
                    "Недостаточно данных для поиска", response.path("message"));
        });
    }

    @Test
    @DisplayName("2. Получение заказа при передаче несуществующего номера трека")
    @Description("Тест проверяет, что метод возвращает 404 и тело ответа \"message\": \"Заказ не найден\", " +
            "при передаче несуществующего номера трека")
    public void getOrderByTrackWithUnexistentNumber() {

        Integer wrongTrackNumber = Integer.MAX_VALUE;

        Response response = orderClient.actionOrderGetByTrackNumber(wrongTrackNumber);

        Allure.step("Проверка контракта ошибки при неверном номере трека", () -> {
            assertEquals("Статус-код ответа должен быть 404!", 404, response.getStatusCode());
            assertEquals("В теле ответа должно быть \"message\": \"Заказ не найден\"",
                    "Заказ не найден", response.path("message"));
        });
    }
}