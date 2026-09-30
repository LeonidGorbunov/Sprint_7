package ru.praktikum_services.qa_scooter.models.request;

import com.google.gson.annotations.SerializedName;

public class CourierLoginRequest {

    @SerializedName("login")
    private String login;

    @SerializedName("password")
    private String password;

    public CourierLoginRequest(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public CourierLoginRequest() {
    }

    public String getPassword(){
        return password;
    }

    public String getLogin(){
        return login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setLogin(String login) {
        this.login = login;
    }
}
