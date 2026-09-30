package ru.praktikum_services.qa_scooter.data;

import ru.praktikum_services.qa_scooter.models.request.CourierCreateRequest;

public class CourierDataGenerator {

    public static CourierCreateRequest getDefaultCourier() {
        String uniqueLogin = "simpson" + System.currentTimeMillis();
        String uniquePassword = "password" + System.currentTimeMillis();
        String uniqueFirstName = "homer" + System.currentTimeMillis();
        return new CourierCreateRequest(uniqueLogin, uniquePassword, uniqueFirstName);
    }

    public static CourierCreateRequest getCourierWithoutLogin() {
        CourierCreateRequest courier = getDefaultCourier();
        courier.setLogin(null);
        return courier;
    }

    public static CourierCreateRequest getCourierWithoutPassword() {
        CourierCreateRequest courier = getDefaultCourier();
        courier.setPassword(null);
        return courier;
    }

    public static CourierCreateRequest getCourierWithoutFirstName() {
        CourierCreateRequest courier = getDefaultCourier();
        courier.setFirstName(null);
        return courier;
    }

    public static CourierCreateRequest getCourierWithUnexistentLogin() {
        CourierCreateRequest courier = getDefaultCourier();
        courier.setLogin("unregistered_" + System.currentTimeMillis());
        return courier;
    }
}