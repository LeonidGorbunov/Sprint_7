package ru.praktikum_services.qa_scooter.client;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public class BaseClient {

        private static final String BASE_URL = "https://qa-scooter.praktikum-services.ru";

        protected RequestSpecification getBaseSpec() {
            return new RequestSpecBuilder()
                    .setBaseUri(BASE_URL)
                    .setContentType(ContentType.JSON)
                    .addFilter(new AllureRestAssured())
                    .build();
        }
}