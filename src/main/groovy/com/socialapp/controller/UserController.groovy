package com.socialapp.controller

import com.socialapp.core.converter.DataConverter
import com.socialapp.core.domain.document.User
import com.socialapp.core.domain.dto.LoginDto
import com.socialapp.core.domain.dto.PostDetailsDTO
import com.socialapp.core.domain.dto.SubscriberPostsDto
import com.socialapp.core.domain.dto.UserDto
import com.socialapp.core.service.UserService
import groovy.util.logging.Slf4j
import io.swagger.v3.oas.annotations.Operation
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@Slf4j
@RestController
@RequestMapping("/users")
class UserController {

	private final DataConverter<User, UserDto> converter

	private final UserService userService

	UserController(
			final DataConverter<User, UserDto> converter,
			final UserService userService
	) {
		this.converter = converter
		this.userService = userService
	}

	@PostMapping("/register")
	@Operation(summary = "Register new user")
	ResponseEntity<UserDto> registerUser(@RequestBody @Valid final UserDto userDto) {
		final User user = converter.convertToDocument(userDto)
		final User savedUser = userService.createUser(user)
		log.info("In saveUser successful save new user with email: [{}]", userDto.getEmail())
		return new ResponseEntity<>(converter.convertToDto(savedUser), HttpStatus.CREATED)
	}

	@DeleteMapping("/delete/{userId}")
	@Operation(summary = "Delete user by id")
	ResponseEntity<String> deleteUser(@PathVariable("userId") final String userId) {
		userService.deleteUser(userId)
		log.info("In deleteUser successful delete user with email: [{}]", userId);
		return new ResponseEntity<>("Delete successful", HttpStatus.OK)
	}

	@PostMapping("/login")
	@Operation(summary = "Login for user")
	ResponseEntity<String> loginUser(@RequestBody LoginDto loginDto) {
		userService.loginUser(loginDto)
		log.info("In loginUser successful login for user with email: [{}]", loginDto.getEmail())
		return new ResponseEntity<>("Login successful", HttpStatus.OK)
	}

	@PostMapping("/logout/{userId}")
	@Operation(summary = "Logout for user")
	ResponseEntity<String> logoutUser(@PathVariable("userId") final String userId) {
		userService.logoutUser(userId)
		log.info("In logoutUser successful logout for user with id: [{}]", userId)
		return new ResponseEntity<>("Logout successful", HttpStatus.OK)
	}

	@GetMapping("/feeds/{postId}")
	@Operation(summary = "Get a user's feed including likes and comments")
	ResponseEntity<PostDetailsDTO> getUserFeed(@PathVariable("postId") final String postId) {
		PostDetailsDTO postDetails = userService.getPostDetailsByUserIdAndPostId(postId);
		log.info("In getUserFeed successful got a user's feed including likes and comments of postId: [{}]", postId)
		return new ResponseEntity<>(postDetails, HttpStatus.OK)
	}

	@GetMapping("/subscriber_p/osts{userId}")
	@Operation(summary = "Subscriber's feed will receive posts from the user to whom he/she subscribed)")
	ResponseEntity<SubscriberPostsDto> getSubscriberPosts(@PathVariable("userId") final String userId){
		final List<SubscriberPostsDto> subscriberPosts = userService.getSubscriberPosts(userId)
		log.info("In getSubscriberPosts successful got subscriber posts of userId: [{}]", userId)
		return new ResponseEntity<SubscriberPostsDto>(subscriberPosts, HttpStatus.OK)
	}
}
