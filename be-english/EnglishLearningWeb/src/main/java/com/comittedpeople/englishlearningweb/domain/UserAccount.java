package com.comittedpeople.englishlearningweb.domain;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
public class UserAccount {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false, unique = true)
	private String username;
	
	private String displayname;
	
	private String email;
	
	private String password;

	private Boolean enabled;
	
	//Deprecated.
	private Integer reminder;
	
	private Byte reminderMonday;
	
	private Byte reminderTuesday;
	
	private Byte reminderWednesday;
	
	private Byte reminderThursday;
	
	private Byte reminderFriday;
	
	private Byte reminderSaturday;
	
	private Byte reminderSunday;

	@ManyToMany(cascade = {
			CascadeType.PERSIST,
			CascadeType.MERGE
	}, fetch = FetchType.EAGER)
	@JoinTable(name = "user_account_authorities",
		joinColumns = @JoinColumn(name = "user_id"),
		inverseJoinColumns = @JoinColumn(name = "authority_id")
	)
	private Set<AccountAuthority> authorities = new HashSet<AccountAuthority>();
	
	@OneToMany(mappedBy = "userSentAccount")
	@JsonManagedReference
	private Set<ChatRoomMessage> chatRoomMessages = new HashSet<ChatRoomMessage>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getDisplayname() {
		return displayname;
	}

	public void setDisplayname(String displayname) {
		this.displayname = displayname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public Set<ChatRoomMessage> getChatRoomMessages() {
		return chatRoomMessages;
	}

	public void setChatRoomMessages(Set<ChatRoomMessage> chatRoomMessages) {
		this.chatRoomMessages = chatRoomMessages;
	}

	public Set<AccountAuthority> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(Set<AccountAuthority> authorities) {
		this.authorities = authorities;
	}
	
	public Integer getReminder() {
		return reminder;
	}

	public void setReminder(Integer reminder) {
		this.reminder = reminder;
	}

	public Byte getReminderMonday() {
		return reminderMonday;
	}

	public void setReminderMonday(Byte reminderMonday) {
		this.reminderMonday = reminderMonday;
	}

	public Byte getReminderTuesday() {
		return reminderTuesday;
	}

	public void setReminderTuesday(Byte reminderTuesday) {
		this.reminderTuesday = reminderTuesday;
	}

	public Byte getReminderWednesday() {
		return reminderWednesday;
	}

	public void setReminderWednesday(Byte reminderWednesday) {
		this.reminderWednesday = reminderWednesday;
	}

	public Byte getReminderThursday() {
		return reminderThursday;
	}

	public void setReminderThursday(Byte reminderThursday) {
		this.reminderThursday = reminderThursday;
	}

	public Byte getReminderFriday() {
		return reminderFriday;
	}

	public void setReminderFriday(Byte reminderFriday) {
		this.reminderFriday = reminderFriday;
	}

	public Byte getReminderSaturday() {
		return reminderSaturday;
	}

	public void setReminderSaturday(Byte reminderSaturday) {
		this.reminderSaturday = reminderSaturday;
	}

	public Byte getReminderSunday() {
		return reminderSunday;
	}

	public void setReminderSunday(Byte reminderSunday) {
		this.reminderSunday = reminderSunday;
	}
}
