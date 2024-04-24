package com.socialapp.core.service.impl

import com.socialapp.core.domain.document.Comment
import com.socialapp.core.domain.document.Like
import com.socialapp.core.domain.document.Post
import com.socialapp.core.domain.document.User
import com.socialapp.core.domain.dto.LoginDto
import com.socialapp.core.domain.dto.PostDetailsDTO
import com.socialapp.core.repository.PostRepository
import com.socialapp.core.repository.UserRepository
import com.socialapp.core.service.UserService
import com.socialapp.core.service.exception.BadCredentialsException
import com.socialapp.core.service.exception.DuplicateEmailException
import com.socialapp.core.service.exception.ResourceNotfoundException
import groovy.util.logging.Slf4j
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Slf4j
@Service
class UserServiceImpl implements UserService {

	private final BCryptPasswordEncoder passwordEncoder

	private final PostRepository postRepository

	private final UserRepository userRepository

	UserServiceImpl(
			final BCryptPasswordEncoder passwordEncoder,
			final UserRepository userRepository,
			final PostRepository postRepository) {
		this.passwordEncoder = passwordEncoder
		this.userRepository = userRepository
		this.postRepository = postRepository
	}

	@Override
	User createUser(final User user) {
		final String email = user.getEmail()
		if (userRepository.findByEmail(email).isPresent()) {
			throw new DuplicateEmailException("User with email already exists: " + email)
		}
		if (user.getPassword() != null) {
			user.setPassword(passwordEncoder.encode(user.getPassword()))
		}
		log.debug("In createUser trying save user with userName: [{}]", user.getUserName())
		user.setIsRegister(true)
		user.setIsLogin(false)
		userRepository.insert(user)
	}

	@Override
	void deleteUser(final String userId) {
		final User user = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotfoundException("User wasn't found with id: " + userId))
		log.debug("In deleteUser trying to delete user with userName: [{}]", user.getUserName())
		userRepository.deleteById(user.getId())
	}

	@Override
	void loginUser(final LoginDto loginDto) {
		final String email = loginDto.getEmail()
		final User user = userRepository.findByEmail(email)
				.orElseThrow(() -> new ResourceNotfoundException("User wasn't found with email: " + email))

		final boolean unregistered = !user.getIsRegister()
		final boolean wrongPassword = !passwordEncoder.matches(loginDto.getPassword(), user.getPassword())

		if (unregistered || wrongPassword) {
			if (wrongPassword) {
				log.warn("Passwords don't match for email: [{}]", loginDto.getPassword());
			}
			if (unregistered) {
				log.warn("User doesn't register for email: [{}]", loginDto.getEmail());
			}
			throw new BadCredentialsException("Invalid password or unregistered user");
		}
		log.debug("In loginUser trying to login user with userName: [{}]", user.getUserName())
		user.setIsLogin(true)
		userRepository.save(user)
	}

	@Override
	void logoutUser(final String userId) {
		final User user = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotfoundException("User wasn't found with id: " + userId))
		log.debug("In logoutUser trying to logout user with userName: [{}]", user.getUserName())
		user.setIsLogin(false)
		userRepository.save(user)
	}

	@Override
	PostDetailsDTO getPostDetailsByUserIdAndPostId(final String postId) {
		final Post post = postRepository.findById(postId)
				.orElseThrow(() -> new ResourceNotfoundException("Post wasn't found with id: " + postId))
		PostDetailsDTO postDetails = new PostDetailsDTO();
		postDetails.setPost(post);
		return postDetails

	}
}
