package com.comittedpeople.englishlearningweb.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.comittedpeople.englishlearningweb.domain.AccountAuthority;

public interface AccountAuthorityRepository extends JpaRepository<AccountAuthority, Long> {
	
	AccountAuthority findByName(String name);

}
