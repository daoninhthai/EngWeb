package com.comittedpeople.englishlearningweb.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.comittedpeople.englishlearningweb.domain.UserAccount;

public interface UserAccountRepository extends JpaRepository<UserAccount, Long> {

	UserAccount findByUsername (String username);
	
	UserAccount findByUsernameOrEmail (String username, String email);
	
	//List<UserAccount> findAll();
}
