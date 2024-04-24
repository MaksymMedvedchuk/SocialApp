package com.socialapp.core.domain.service.impl


import com.socialapp.core.domain.service.UserService
import com.socialapp.core.domain.repository.UserRepository
import groovy.util.logging.Slf4j
import org.springframework.stereotype.Service

@Slf4j
@Service
class UserServiceImpl implements UserService {

	private final UserRepository userRepository


	UserServiceImpl(final UserRepository userRepository) {
		this.userRepository = userRepository
	}

	@Override
	com.socialapp.core.domain.document.User createUser(final com.socialapp.core.domain.document.User user) {
		final String email = user.getEmail();
		if (userRepository.findByEmail(email).isPresent())
			throw new DuplicateEmailException("User with email already exists: " + email);

	}
}
