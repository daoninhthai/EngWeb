package com.comittedpeople.englishlearningweb.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.comittedpeople.englishlearningweb.api.v1.mapper.ReminderConfigMapper;
import com.comittedpeople.englishlearningweb.api.v1.mapper.UserAccountMapper;
import com.comittedpeople.englishlearningweb.api.v1.model.ReminderConfigDTO;
import com.comittedpeople.englishlearningweb.api.v1.model.UserAccountDTO;
import com.comittedpeople.englishlearningweb.domain.AccountAuthority;
import com.comittedpeople.englishlearningweb.domain.UserAccount;
import com.comittedpeople.englishlearningweb.payload.RegisterRequestDTO;
import com.comittedpeople.englishlearningweb.repositories.AccountAuthorityRepository;
import com.comittedpeople.englishlearningweb.repositories.UserAccountRepository;

@Service
public class UserAccountServiceImpl implements UserAccountService {

	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	UserAccountRepository userAccountRepository;
	
	@Autowired
	AccountAuthorityRepository accountAuthorityRepository;
	
	@Autowired
	UserAccountMapper userMapper;
	
	@Autowired
	ReminderConfigMapper reminderConfigMapper;
	
	@Autowired
	PasswordEncoder passwordEncoder;

	public UserAccountServiceImpl(UserAccountRepository userAccountRepository) {
		super();
		this.userAccountRepository = userAccountRepository;
	}

	@Override
	public UserAccount createUserAccount(RegisterRequestDTO registerRequest) {
		//Tạo mới tài khoản.
		AccountAuthority user = accountAuthorityRepository.findByName("ROLE_USER");
		return createAccount(registerRequest, new HashSet<AccountAuthority>(Arrays.asList(user)));
	}
	
	@Override
	public UserAccount createAdminAccount(RegisterRequestDTO registerRequest) {
		AccountAuthority user = accountAuthorityRepository.findByName("ROLE_USER");
		AccountAuthority admin = accountAuthorityRepository.findByName("ROLE_ADMIN");
		
		return createAccount(registerRequest, new HashSet<AccountAuthority>(Arrays.asList(user,admin)));
	}
	
	private UserAccount createAccount(RegisterRequestDTO registerRequest, Set<AccountAuthority> authorities) {
		String username = registerRequest.getUsername();
		String password = registerRequest.getPassword();
		String email = registerRequest.getEmail();
		String displayname = registerRequest.getDisplayname();
		
		UserAccount account = userAccountRepository.findByUsernameOrEmail(username, email);
		
		//Tài khoản khác null tức là đã tồn tại tài khoản trong hệ thống.
		//Vì vậy không có tài khoản mới nào được tạo. Trực tiếp trả về null.
		if (account != null) 
			return null;
		
		account = new UserAccount();
		account.setUsername(username);
		account.setPassword(passwordEncoder.encode(password));
		account.setEmail(email);
		account.setDisplayname(displayname);
		account.setAuthorities(authorities);
		account.setEnabled(true);
		
		account = userAccountRepository.save(account);
		
		//Cập nhật hai chiều cho cả authority luôn.
		for (AccountAuthority auth : authorities) {
			auth.getUsers().add(account);
		}
		
		return account;
	}

	@Override
	public UserAccountDTO getUserByID(Long userID) {
		UserAccount user;
		try {
			user = userAccountRepository.findById(userID).get();
		} catch (Exception e) {
			return null;
		}
		
		UserAccountDTO result = userMapper.getDto(user);
		
		return result;
		
	}

	@Override
	public UserAccountDTO patchUserByID(Long userID, UserAccountDTO userAccount) {
		
		return userAccountRepository.findById(userID).map(account -> {
			
			Boolean isAccountCorrect = false;
			
			if (userAccount.getIsAccountEnabled() != null) {
				account.setEnabled(userAccount.getIsAccountEnabled());
			}
			
			if (userAccount.getDisplayName() != null) {
				account.setDisplayname(userAccount.getDisplayName());
			}
			
			if (userAccount.getEmail() != null) {
				account.setEmail(userAccount.getEmail());
			}
			
			if (userAccount.getNewPassword() != null) {
//				System.out.println("Into getNewPasword");
				isAccountCorrect = verifyAccount(account.getUsername(), userAccount.getCurrentPassword());
				
				//Chỉ khi nào người dùng nhập vào mật khẩu cũ hợp lệ thì ta mới cho người dùng đổi mật khẩu.
				if (isAccountCorrect) {
//					System.out.println("Verify password succeed");
					//Ta cập nhật mật khẩu mới cho tài khoản sau khi xác minh tính hợp lệ của nó.
					account.setPassword(passwordEncoder.encode(userAccount.getNewPassword()));
//					System.out.println("Change password succeed");
				}
			}
			if (userAccount.getReminder() != null) {
				account.setReminder(userAccount.getReminder());
			}
			if (userAccount.getRoles() != null) {
				//Chuyển tất cả tên Authority từ input của người dùng sang đối tượng Authority.
				Set<AccountAuthority> accountAuthorities = new HashSet<>();
				for (String role : userAccount.getRoles()) {
					AccountAuthority search ;
					try{
						search = accountAuthorityRepository.findByName(role);
					}catch (Exception e) {
						// TODO: handle exception
						search = null;
					}
					if (search != null)
						accountAuthorities.add(search);
				}
				account.setAuthorities(accountAuthorities);
			}
			
			UserAccountDTO returnDTO = userMapper.getDto(userAccountRepository.save(account));
			
			if (isAccountCorrect)
				returnDTO.setCurrentPassword("UPDATE_SUCCESS");
			
			return returnDTO;
		}).orElseThrow(RuntimeException::new);
	}
	
	private Boolean verifyAccount (String username, String password) {
		
//		System.out.println("Username : " + username);
//		System.out.println("Password : " + password);
		try {
			 authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(username, password));
		}catch (Exception e) {
			return false;
		}
		return true;
	}

	@Override
	public List<UserAccountDTO> getAllUsers() {
		// TODO Auto-generated method stub
		List<UserAccountDTO> userAccountDTOs = userAccountRepository.findAll()
				.stream()
				.map(userMapper::getDto)
				.collect(Collectors.toList());
		return userAccountDTOs;
	}

	@Override
	public Boolean setBanUserID(Long userID, Boolean setStatus) {
		UserAccount user = userAccountRepository.findById(userID).get();
		if (user == null)
			return false;
		user.setEnabled(!setStatus);
		user = userAccountRepository.save(user);
		if (user.getEnabled() == setStatus)
			return true;
		return false;
	}

	@Override
	public Boolean isUserIDAdmin(Long userID) {
		
		UserAccount user = userAccountRepository.findById(userID).get();
		if (user == null)
			return false;
		
		//Lấy danh sách tất cả các roles, nếu có chứa admin thì user là admin.
		Set<AccountAuthority> authorities = user.getAuthorities();
		for (AccountAuthority authority : authorities) {
			if (authority.getName() == "ROLE_ADMIN")
				return true;
		}
		return false;
	}

	@Override
	public ReminderConfigDTO getReminderConfigDTO(Long userID) {
		// TODO Auto-generated method stub
		UserAccount user = userAccountRepository.findById(userID).get();
		
		if (user == null)
			return null;
		
		ReminderConfigDTO dto = reminderConfigMapper.getDto(user);
		
		return dto;
	}

	@Override
	public ReminderConfigDTO putReminderConfigDTO(Long userID, ReminderConfigDTO reminderConfigDTO) {
		// TODO Auto-generated method stub
		
		UserAccount currentUser = userAccountRepository.findById(userID).get();
		
		if (currentUser == null)
			return null;
		
		//Gán các ngày vào lại user.
		currentUser.setReminderMonday(reminderConfigDTO.getMonday());
		currentUser.setReminderTuesday(reminderConfigDTO.getTuesday());
		currentUser.setReminderWednesday(reminderConfigDTO.getWednesday());
		currentUser.setReminderThursday(reminderConfigDTO.getThursday());
		currentUser.setReminderFriday(reminderConfigDTO.getFriday());
		currentUser.setReminderSaturday(reminderConfigDTO.getSaturday());
		currentUser.setReminderSunday(reminderConfigDTO.getSunday());
		
		currentUser = userAccountRepository.save(currentUser);
		
		return reminderConfigMapper.getDto(currentUser);
	}
}
