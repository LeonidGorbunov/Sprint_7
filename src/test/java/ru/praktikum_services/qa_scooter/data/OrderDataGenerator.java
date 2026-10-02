package ru.praktikum_services.qa_scooter.data;

import ru.praktikum_services.qa_scooter.models.request.OrderCreateRequest;

import java.time.LocalDate;
import java.util.List;

public class OrderDataGenerator {

    public static OrderCreateRequest getDefaultOrder(List<String> scooterColor) {

        String uniqueSuffix = String.valueOf(System.currentTimeMillis()).substring(6);

        return new OrderCreateRequest(
                "Homer" + uniqueSuffix,
                "Simpson" + uniqueSuffix,
                "Evergreen Terrace " + uniqueSuffix,
                10,
                "+7999" + uniqueSuffix,
                4,
                LocalDate.now().plusDays(1).toString(),
                "D'oh! " + uniqueSuffix,
                scooterColor
        );
    }
}
