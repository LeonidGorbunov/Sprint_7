package ru.praktikum_services.qa_scooter.models.request;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourierLoginRequest {

    @SerializedName("login")
    private String login;

    @SerializedName("password")
    private String password;
}