package ru.praktikum_services.qa_scooter.models.request;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CourierDeleteRequest {

    @SerializedName("id")
    private String id;

    public CourierDeleteRequest(Object id) {
        this.id = (id == null) ? null : String.valueOf(id);
    }
}