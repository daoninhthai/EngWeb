package com.comittedpeople.englishlearningweb.controllers.v1;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.hibernate.stat.internal.StatsHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.comittedpeople.englishlearningweb.api.v1.model.ReminderConfigDTO;
import com.comittedpeople.englishlearningweb.api.v1.model.UserAccountDTO;
import com.comittedpeople.englishlearningweb.domain.UserDetailsCustom;
import com.comittedpeople.englishlearningweb.services.UserAccountService;

@Controller
@RequestMapping("/api/v1/users")
public class UserAccountController {

	@Autowired
	private UserAccountService userAccountService;

	@GetMapping
	public ResponseEntity<List<UserAccountDTO>> getAllUserAccount() {
		
		//Đầu tiên phải kiểm tra user là admin thì mới cho phép lấy danh sách User về.
		if (!isCurrentUserAdmin())
			return new ResponseEntity<List<UserAccountDTO>>(new ArrayList<UserAccountDTO>(), HttpStatus.FORBIDDEN);
		
		List<UserAccountDTO> returnDTOs = userAccountService.getAllUsers();
		
		if (returnDTOs == null)
			return new ResponseEntity<List<UserAccountDTO>> (returnDTOs, HttpStatus.NOT_FOUND);
		else
			return new ResponseEntity<List<UserAccountDTO>> (returnDTOs, HttpStatus.OK);
	}
	
	@GetMapping("{userID}")
	public ResponseEntity<UserAccountDTO> getUserAccount(@PathVariable Long userID) {
		UserAccountDTO returnDTO = userAccountService.getUserByID(userID);
		
		//Nếu người dùng hiện tại đang request đến thông tin tài khoản khác thì hạn chế thôi.
		if (!isCurrentUserHaveEditPermission(userID)) {
			returnDTO.setCurrentPassword(null);
			returnDTO.setIsAccountEnabled(null);
			returnDTO.setNewPassword(null);
			returnDTO.setPasswordLength(null);
			returnDTO.setReminder(null);
			returnDTO.setRoles(null);
		}
		
		if (returnDTO == null)
			return new ResponseEntity<UserAccountDTO>(returnDTO, HttpStatus.NOT_FOUND);
		else
			return new ResponseEntity<UserAccountDTO>(returnDTO, HttpStatus.OK);
	}

	@GetMapping("{userID}/reminders")
	public ResponseEntity<ReminderConfigDTO> getUserReminders(@PathVariable Long userID) {
		// Nếu thông tin reminder mà user yêu cầu là của người khác, không cho xem.
		if (!isCurrentUserHaveEditPermission(userID)) {
			return new ResponseEntity<ReminderConfigDTO>(new ReminderConfigDTO(), HttpStatus.FORBIDDEN);
		}

		ReminderConfigDTO reminderConfigDTO = userAccountService.getReminderConfigDTO(userID);
		
		if (reminderConfigDTO == null)
			return new ResponseEntity<ReminderConfigDTO>(new ReminderConfigDTO(), HttpStatus.NOT_FOUND);
		else
			return new ResponseEntity<ReminderConfigDTO>(reminderConfigDTO, HttpStatus.OK);
	}

	@PutMapping("{userID}/ban")
	public ResponseEntity setUserBanStatus(@PathVariable Long userID, @RequestParam("status")Integer status) {
		Boolean isBanned = false;
		if (status == 1)
			isBanned = true;
		if (status == null)
			return new ResponseEntity(HttpStatus.FORBIDDEN);
		//Chỉ admin mới được ban nick của User khác.
		if (!isCurrentUserAdmin())
			return new ResponseEntity(HttpStatus.FORBIDDEN);
		Boolean success = userAccountService.setBanUserID(userID, isBanned);
		if (success)
			return new ResponseEntity(HttpStatus.OK);
		else {
			return new ResponseEntity(HttpStatus.FORBIDDEN);
		}
	}
	
	@PutMapping("{userID}/reminders")
	public ResponseEntity<ReminderConfigDTO> putUserReminders(@PathVariable Long userID,
			@Valid @RequestBody ReminderConfigDTO reminderConfigDTO) {
		// Nếu thông tin reminder mà user yêu cầu là của người khác, không cho xem.
		if (!isCurrentUserHaveEditPermission(userID)) {
			return new ResponseEntity<ReminderConfigDTO>(new ReminderConfigDTO(), HttpStatus.FORBIDDEN);
		}
		
		ReminderConfigDTO result = userAccountService.putReminderConfigDTO(userID, reminderConfigDTO);
		
		if (result == null)
			return new ResponseEntity<ReminderConfigDTO>(new ReminderConfigDTO(), HttpStatus.NOT_FOUND);
		else
			return new ResponseEntity<ReminderConfigDTO>(result, HttpStatus.OK);
	}

	@PatchMapping("{userID}")
	public ResponseEntity<UserAccountDTO> patchUserAccount(@PathVariable Long userID,
			@Valid @RequestBody UserAccountDTO accountDTO) {
		// Nếu thông tin reminder mà user yêu cầu là của người khác, không cho xem.
		if (!isCurrentUserHaveEditPermission(userID)) {
			return new ResponseEntity<UserAccountDTO>(new UserAccountDTO(), HttpStatus.FORBIDDEN);
		}

		UserAccountDTO returnDTO = userAccountService.patchUserByID(userID, accountDTO);

		if (returnDTO == null)
			return new ResponseEntity<UserAccountDTO>(returnDTO, HttpStatus.NOT_FOUND);
		else
			return new ResponseEntity<UserAccountDTO>(returnDTO, HttpStatus.OK);
	}

	private Boolean matchCurrentUsername(String username) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String currentUsername;
		// Kiểm tra xem principle hiện tại có đúng dạng chưa.
		if (principal instanceof UserDetails) {
			currentUsername = ((UserDetails) principal).getUsername();
		} else {
			currentUsername = principal.toString();
		}
		
		if (currentUsername.equals(username))
			return true;
		return false;	
	}
	
	private Boolean isCurrentUserHaveEditPermission(Long currentUserID) {
		//Để có thể chỉnh được thông tin của User, thì có 2 TH được cho phép :
		//1. Admin chỉnh thông tin của User.
		//2. User tự chỉnh thông tin của mình.

		//Debug.
//		if (isCurrentUserAdmin())
//			System.out.println("Current user is admin !");
//		if (matchCurrentUserID(currentUserID))
//			System.out.println("Match current User ID !");

		return (isCurrentUserAdmin() || matchCurrentUserID(currentUserID));
	}

	private Boolean isCurrentUserAdmin() {
		Object principle = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Long currentUserID;
		if (principle instanceof UserDetailsCustom) {
			currentUserID = ((UserDetailsCustom)principle).getUseraccount().getId();
		}else {
			currentUserID = -1L;
			return false;
		}
		return userAccountService.isUserIDAdmin(currentUserID);
	}
	
	private Boolean matchCurrentUserID(Long userID) {
		Object principle = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Long currentUserID;
		// Kiểm tra xem principle hiện tại có đúng dạng chưa.
		if (principle instanceof UserDetailsCustom) {
			currentUserID = ((UserDetailsCustom) principle).getUseraccount().getId();
		} else {
			currentUserID = -1L;
		}
		if (currentUserID == userID)
			return true;
		return false;
	}
}
