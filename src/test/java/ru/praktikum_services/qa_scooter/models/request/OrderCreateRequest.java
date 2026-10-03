package ru.praktikum_services.qa_scooter.models.request;

import java.util.List;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderCreateRequest {

	@SerializedName("firstName")
	private String firstName;

	@SerializedName("lastName")
	private String lastName;

	@SerializedName("address")
	private String address;

	@SerializedName("metroStation")
	private int metroStation;

	@SerializedName("phone")
	private String phone;

	@SerializedName("rentTime")
	private int rentTime;

	@SerializedName("deliveryDate")
	private String deliveryDate;

	@SerializedName("comment")
	private String comment;

	@SerializedName("color")
	private List<String> color;
}