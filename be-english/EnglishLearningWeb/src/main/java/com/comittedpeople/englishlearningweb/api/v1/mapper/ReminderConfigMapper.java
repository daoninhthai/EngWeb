package com.comittedpeople.englishlearningweb.api.v1.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.comittedpeople.englishlearningweb.api.v1.model.ReminderConfigDTO;
import com.comittedpeople.englishlearningweb.domain.UserAccount;

@Mapper
public interface ReminderConfigMapper {

	@Mapping(source = "reminderMonday", target = "monday")
	@Mapping(source = "reminderTuesday", target = "tuesday")
	@Mapping(source = "reminderWednesday", target = "wednesday")
	@Mapping(source = "reminderThursday", target = "thursday")
	@Mapping(source = "reminderFriday", target = "friday")
	@Mapping(source = "reminderSaturday", target = "saturday")
	@Mapping(source = "reminderSunday", target = "sunday")
	ReminderConfigDTO getDto (UserAccount account);
	
}
