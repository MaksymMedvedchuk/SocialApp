package com.socialapp.controller

import com.socialapp.core.converter.DataConverter
import com.socialapp.core.domain.document.User
import com.socialapp.core.domain.dto.LoginDto
import com.socialapp.core.domain.dto.PostDetailsDto
import com.socialapp.core.domain.dto.SubscriberPostsDto
import com.socialapp.core.domain.dto.UserDto
import com.socialapp.core.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import spock.lang.Shared
import spock.lang.Specification

class UserControllerSpec extends Specification {

	UserService userService = Mock(UserService.class)

	DataConverter converter = Mock(DataConverter.class)

	UserController controller = new UserController(converter, userService)

	@Shared
	String userId

	def setupSpec() {
		userId = "userId"
	}

	def "should register user and return status CREATE and user data"() {
		given:
		User user = new User()
		UserDto userDto = new UserDto()
		1 * converter.convertToDocument(userDto) >> user
		1 * userService.createUser(user) >> user
		1 * converter.convertToDto(user) >> userDto
		when:
		ResponseEntity<UserDto> response = controller.registerUser(userDto)
		then:
		response.statusCode == HttpStatus.CREATED
		response.body == userDto
	}

	def "should delete user and return status OK"() {
		given:
		1 * userService.deleteUser(userId)
		when:
		ResponseEntity<String> response = controller.deleteUser(userId)
		then:
		response.statusCode == HttpStatus.OK
		response.body == "Delete successful"
	}

	def "should login user and return status OK"() {
		given:
		LoginDto login = new LoginDto()
		1 * userService.loginUser(login)
		when:
		ResponseEntity<String> response = controller.loginUser(login)
		then:
		response.statusCode == HttpStatus.OK
	}

	def "should logout user and return status OK"() {
		given:
		1 * userService.logoutUser(userId)
		when:
		ResponseEntity<String> response = controller.logoutUser(userId)
		then:
		response.statusCode == HttpStatus.OK
	}

	def "should get a user's feed including likes and comments and return status OK"() {
		given:
		PostDetailsDto postDetail = new PostDetailsDto()
		String postId = "2"
		1 * userService.getPostDetailsByPostId(postId) >> postDetail
		when:
		ResponseEntity<PostDetailsDto> response = controller.getUserFeed(postId)
		then:
		response.statusCode == HttpStatus.OK
		response.body == postDetail
	}

	def "should add in subscriber's feed posts from the user to whom they subscribed and return status OK"(){
		given:
		List<SubscriberPostsDto> subPosts = new ArrayList<>()
		1 * userService.getSubscriberPosts(userId) >> subPosts
		when:
		ResponseEntity<List<SubscriberPostsDto>> response = controller.getSubscriberPosts(userId)
		then:
		response.statusCode == HttpStatus.OK
		response.body == subPosts
	}
}



