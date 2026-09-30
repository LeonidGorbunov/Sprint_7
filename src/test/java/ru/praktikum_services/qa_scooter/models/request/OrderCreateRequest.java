package ru.praktikum_services.qa_scooter.models.request;

import java.util.List;
import com.google.gson.annotations.SerializedName;

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

	public OrderCreateRequest(String firstName, String lastName, String address, int metroStation, String phone,
							  int rentTime, String deliveryDate, String comment, List<String> color) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.metroStation = metroStation;
		this.phone = phone;
		this.rentTime = rentTime;
		this.deliveryDate = deliveryDate;
		this.comment = comment;
		this.color = color;
	}

	public OrderCreateRequest() {
	}

	public void setFirstName(String firstName){
		this.firstName = firstName;
	}

	public String getFirstName(){
		return firstName;
	}

	public void setLastName(String lastName){
		this.lastName = lastName;
	}

	public String getLastName(){
		return lastName;
	}

	public void setAddress(String address){
		this.address = address;
	}

	public String getAddress(){
		return address;
	}

	public void setMetroStation(int metroStation){
		this.metroStation = metroStation;
	}

	public int getMetroStation(){
		return metroStation;
	}

	public void setPhone(String phone){
		this.phone = phone;
	}

	public String getPhone(){
		return phone;
	}

	public void setRentTime(int rentTime){
		this.rentTime = rentTime;
	}

	public int getRentTime(){
		return rentTime;
	}

	public void setDeliveryDate(String deliveryDate){
		this.deliveryDate = deliveryDate;
	}

	public String getDeliveryDate(){
		return deliveryDate;
	}

	public void setComment(String comment){
		this.comment = comment;
	}

	public String getComment(){
		return comment;
	}

	public void setColor(List<String> color){
		this.color = color;
	}

	public List<String> getColor(){
		return color;
	}
}