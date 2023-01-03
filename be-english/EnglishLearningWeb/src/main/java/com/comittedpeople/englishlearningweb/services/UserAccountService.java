package com.comittedpeople.englishlearningweb.services;

import java.util.List;

import com.comittedpeople.englishlearningweb.api.v1.model.ReminderConfigDTO;
import com.comittedpeople.englishlearningweb.api.v1.model.UserAccountDTO;
import com.comittedpeople.englishlearningweb.domain.UserAccount;
import com.comittedpeople.englishlearningweb.payload.RegisterRequestDTO;

public interface UserAccountService {

	UserAccount createUserAccount(RegisterRequestDTO registerRequest);

	UserAccount createAdminAccount(RegisterRequestDTO registerRequest);
	
	UserAccountDTO getUserByID (Long userID);
	
	UserAccountDTO patchUserByID (Long userID, UserAccountDTO userAccount);
		
	ReminderConfigDTO getReminderConfigDTO (Long userID);
	
	ReminderConfigDTO putReminderConfigDTO (Long userID, ReminderConfigDTO reminderConfigDTO);
	
	Boolean setBanUserID (Long userID, Boolean setStatus);
		
	List<UserAccountDTO> getAllUsers();
	
	Boolean isUserIDAdmin (Long userID);

}