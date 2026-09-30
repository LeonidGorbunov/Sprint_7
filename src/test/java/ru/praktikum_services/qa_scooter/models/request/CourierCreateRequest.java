package ru.praktikum_services.qa_scooter.models.request;

import com.google.gson.annotations.SerializedName;

public class CourierCreateRequest{

	@SerializedName("login")
	private String login;

	@SerializedName("password")
	private String password;

	@SerializedName("firstName")
	private String firstName;

	public CourierCreateRequest(String login, String password, String firstName) {
		this.login = login;
		this.password = password;
		this.firstName = firstName;
	}

	public CourierCreateRequest() {
	}

	public String getLogin(){
		return login;
	}

	public String getPassword(){
		return password;
	}

	public String getFirstName(){
		return firstName;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
}