package ru.praktikum_services.qa_scooter.client;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;

public class BaseClient {

    public BaseClient() {

        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";

        RestAssured.replaceFiltersWith(new AllureRestAssured());
    }
}