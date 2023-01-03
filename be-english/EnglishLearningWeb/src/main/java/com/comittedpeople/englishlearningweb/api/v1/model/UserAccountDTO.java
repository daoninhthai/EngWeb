package com.comittedpeople.englishlearningweb.api.v1.model;

import java.util.List;

public class UserAccountDTO {
	
	private Integer userID; 
	
	private String userName;
	
	private String displayName;
	
	private String email;
	
	private Boolean isAccountEnabled;
	
	private Integer passwordLength;
	
	//Mặc định sẽ là null khi chỉ lấy thông tin.
	//Trường này dành cho patching.
	private String currentPassword;
	
	//Mặc định sẽ là null khi chỉ lấy thông tin.
	//Trường này dành cho patching.
	private String newPassword;
	
	private Integer reminder;
	
	private List<String> roles;
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Boolean getIsAccountEnabled() {
		return isAccountEnabled;
	}

	public void setIsAccountEnabled(Boolean isAccountEnabled) {
		this.isAccountEnabled = isAccountEnabled;
	}

	public Integer getPasswordLength() {
		return passwordLength;
	}

	public void setPasswordLength(Integer passwordLength) {
		this.passwordLength = passwordLength;
	}

	public Integer getReminder() {
		return reminder;
	}

	public void setReminder(Integer reminder) {
		this.reminder = reminder;
	}

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

	public String getCurrentPassword() {
		return currentPassword;
	}

	public void setCurrentPassword(String currentPassword) {
		this.currentPassword = currentPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public Integer getUserID() {
		return userID;
	}

	public void setUserID(Integer userID) {
		this.userID = userID;
	}
}
