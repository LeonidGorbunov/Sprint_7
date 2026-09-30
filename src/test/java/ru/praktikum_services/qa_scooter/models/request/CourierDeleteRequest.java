package ru.praktikum_services.qa_scooter.models.request;

import com.google.gson.annotations.SerializedName;

public class CourierDeleteRequest {

    @SerializedName("id")
    private String id;

    public CourierDeleteRequest(Object id) {
        this.id = (id == null) ? null : String.valueOf(id);
    }

    public CourierDeleteRequest() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
