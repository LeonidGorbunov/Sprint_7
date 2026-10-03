package ru.praktikum_services.qa_scooter.data;

import ru.praktikum_services.qa_scooter.models.request.CourierCreateRequest;
import ru.praktikum_services.qa_scooter.models.request.CourierLoginRequest;

public class CourierDataGenerator {

    public static CourierCreateRequest getDefaultCourier() {
        String uniqueLogin = "simpson" + System.currentTimeMillis();
        String uniquePassword = "password" + System.currentTimeMillis();
        String uniqueFirstName = "homer" + System.currentTimeMillis();
        return new CourierCreateRequest(uniqueLogin, uniquePassword, uniqueFirstName);
    }

    public static CourierLoginRequest getDefaultLoginRequest() {
        CourierCreateRequest defaultCourier = getDefaultCourier();
        return new CourierLoginRequest(defaultCourier.getLogin(), defaultCourier.getPassword());
    }
}