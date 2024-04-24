package com.socialapp.core.converter.types

import com.socialapp.core.converter.DataConverter
import com.socialapp.core.domain.document.User
import com.socialapp.core.domain.dto.UserDto
import org.modelmapper.ModelMapper
import org.springframework.stereotype.Component

@Component
class UserDataConverter implements DataConverter<User, UserDto> {

	private final ModelMapper modelMapper

	UserDataConverter(final ModelMapper modelMapper) {
		this.modelMapper = modelMapper
	}

	@Override
	UserDto convertToDto(final User document) {
		return modelMapper.map(document, UserDto.class)
	}

	@Override
	User convertToDocument(final UserDto userDto) {
		User user = modelMapper.map(userDto, User.class)
		user.setMetaClass(null)
		return user;
	}
}
