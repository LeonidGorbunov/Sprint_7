package ru.praktikum_services.qa_scooter.tests;

import org.junit.BeforeClass;
import ru.praktikum_services.qa_scooter.client.CourierClient;
import ru.praktikum_services.qa_scooter.client.OrderClient;

public class BaseTest {

    protected static CourierClient courierClient;
    protected static OrderClient orderClient;

    @BeforeClass
    public static void baseSetUp() {
        courierClient = new CourierClient();
        orderClient = new OrderClient();
    }
}
