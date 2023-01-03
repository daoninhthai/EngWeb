package com.comittedpeople.englishlearningweb.api.v1.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import com.comittedpeople.englishlearningweb.api.v1.model.UserAccountDTO;
import com.comittedpeople.englishlearningweb.domain.AccountAuthority;
import com.comittedpeople.englishlearningweb.domain.UserAccount;

@Mapper
public interface UserAccountMapper {
	
	UserAccountMapper INSTANCE = Mappers.getMapper(UserAccountMapper.class);
	
	@Mapping(source = "id", target = "userID")
	@Mapping(source = "username", target = "userName")
	@Mapping(source = "displayname", target = "displayName")
	@Mapping(source = "enabled", target = "isAccountEnabled")
	@Mapping(source = "password", target = "passwordLength", qualifiedByName = "passwordToPasswordLength")
	@Mapping(source = "authorities", target = "roles", qualifiedByName = "authoritiesToRoles")	
	UserAccountDTO getDto (UserAccount userAccount);
	
	@Named("passwordToPasswordLength")
	public static Integer getPasswordLength(String password) {
		if (password == null)
			return 0;
		return password.length();
	}
	
	@Named("authoritiesToRoles")
	public static List<String> getRoles(Set<AccountAuthority> authorities) {
		if (authorities == null || authorities.size() == 0)
			return new ArrayList<String>();
		List<String> roles = new ArrayList<String>();
		
		for (AccountAuthority authority : authorities) {
			roles.add(authority.getName());
		}
		return roles;
	}
}
