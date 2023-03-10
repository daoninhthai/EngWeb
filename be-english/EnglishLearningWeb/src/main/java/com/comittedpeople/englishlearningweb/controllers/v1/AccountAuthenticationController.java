package com.comittedpeople.englishlearningweb.controllers.v1;

import javax.security.sasl.AuthenticationException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.comittedpeople.englishlearningweb.domain.UserAccount;
import com.comittedpeople.englishlearningweb.domain.UserDetailsCustom;
import com.comittedpeople.englishlearningweb.jwt.JwtTokenProvider;
import com.comittedpeople.englishlearningweb.payload.LoginRequestDTO;
import com.comittedpeople.englishlearningweb.payload.LoginResponseDTO;
import com.comittedpeople.englishlearningweb.payload.RegisterRequestDTO;
import com.comittedpeople.englishlearningweb.services.UserAccountService;

@RestController
@RequestMapping("/api/v1/auth")
public class AccountAuthenticationController {

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenProvider tokenProvider;

	@Autowired
	private UserAccountService userAccountService;

	@PostMapping("/login")
	public LoginResponseDTO authenticateUser(@Valid @RequestBody LoginRequestDTO loginRequest) {
		// X??c th???c username v?? password.
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

		// N???u kh??ng x???y ra exception t???c l?? th??ng tin h???p l???.
		// Set th??ng tin authentication v??o Security Context.
		SecurityContextHolder.getContext().setAuthentication(authentication);

		// Tr??? v??? JWT cho ng?????i d??ng.
		String jwt = tokenProvider.generateToken((UserDetailsCustom) authentication.getPrincipal());

		return new LoginResponseDTO(jwt);
	}
	
	// Khi ????ng k??, n???u th??nh c??ng s??? tr??? v??? lu??n token n??n tr??? v??? lu??n
	// LoginResponseDTO.
	@PostMapping("/register")
	public ResponseEntity<LoginResponseDTO> registerUser(@Valid @RequestBody RegisterRequestDTO registerRequest,
			HttpServletRequest request, HttpServletResponse response) {

		// Ti???n h??nh ????ng k?? user m???i.
		UserAccount account = userAccountService.createUserAccount(registerRequest);

		// T???o t??i kho???n kh??ng th??nh c??ng.
		if (account == null)
			return new ResponseEntity<LoginResponseDTO>(new LoginResponseDTO(), HttpStatus.CONFLICT);

		//????ng k?? th??nh c??ng.
		//Tr??? v??? token cho User.
		Authentication authToken = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(registerRequest.getUsername(), registerRequest.getPassword()));
		
		
		SecurityContextHolder.getContext().setAuthentication(authToken);
		
		String jwt= tokenProvider.generateToken((UserDetailsCustom) authToken.getPrincipal());
		
		return new ResponseEntity<LoginResponseDTO>(new LoginResponseDTO(jwt), HttpStatus.OK);
		
		// Tr??? v??? ????ng k?? th??nh c??ng.
		//return new ResponseEntity<LoginResponseDTO>(new LoginResponseDTO(), HttpStatus.OK);
	}
}
