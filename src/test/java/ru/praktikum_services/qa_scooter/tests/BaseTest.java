package ru.praktikum_services.qa_scooter.tests;

import org.junit.Before;
import ru.praktikum_services.qa_scooter.client.CourierClient;
import ru.praktikum_services.qa_scooter.client.OrderClient;
import ru.praktikum_services.qa_scooter.models.request.CourierCreateRequest;

public class BaseTest {

    protected CourierCreateRequest courierRequest;
    protected CourierClient courierClient;
    protected OrderClient orderClient;
    protected Integer orderTrack;

    @Before
    public void baseSetUp() {
        courierClient = new CourierClient();
        orderClient = new OrderClient();
    }
}
