package com.socialapp.core.servicw.impl

import com.socialapp.core.domain.document.Post
import com.socialapp.core.domain.document.Subscription
import com.socialapp.core.domain.document.User
import com.socialapp.core.domain.dto.LoginDto
import com.socialapp.core.domain.dto.PostDetailsDto
import com.socialapp.core.domain.dto.SubscriberPostsDto
import com.socialapp.core.repository.PostRepository
import com.socialapp.core.repository.UserRepository
import com.socialapp.core.service.UserService
import com.socialapp.core.service.exception.BadCredentialsException
import com.socialapp.core.service.exception.DuplicateEmailException
import com.socialapp.core.service.exception.PasswordEmptyOrNullException
import com.socialapp.core.service.exception.ResourceNotfoundException
import com.socialapp.core.service.impl.UserServiceImpl
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import spock.lang.Shared
import spock.lang.Specification

class UserServiceSpec extends Specification {

	BCryptPasswordEncoder passwordEncoder = Mock()

	UserRepository userRepository = Mock()

	PostRepository postRepository = Mock()

	UserService userService = new UserServiceImpl(passwordEncoder, userRepository, postRepository)

	@Shared
	User user

	@Shared
	String userId

	@Shared
	LoginDto loginDto

	def setupSpec(){
		user = new User(email: "test@example.com", password: "password")
		userId = "userId"
		loginDto = new LoginDto(email: "test@example.com", password: "password")

	}

	def "should create user when createUser if email doesn't exist and password not equal null"() {
		given:
		1 * userRepository.findByEmail(user.getEmail()) >> Optional.empty()
		1 * userRepository.insert(user) >> user
		when:
		User savedUser = userService.createUser(user)
		then:
		savedUser.isRegister
		!savedUser.isLogin
	}

	def "should throw DuplicateEmailException when createUser if email already exists"() {
		given:
		1 * userRepository.findByEmail(user.email) >> Optional.of(user)
		when:
		userService.createUser(user)
		then:
		thrown(DuplicateEmailException)
	}

	def "should throw PasswordEmptyOrNullException when createUser if password is empty"() {
		given:
		User user = new User(email: "test@example.com", password: "")
		1 * userRepository.findByEmail(user.email) >> Optional.empty()
		when:
		userService.createUser(user)
		then:
		thrown(PasswordEmptyOrNullException)
	}

	def "should throw PasswordEmptyOrNullException when createUser if password is null"() {
		given:
		User user = new User(email: "existing@example.com", password: null)
		1 * userRepository.findByEmail(user.email) >> Optional.empty()
		when:
		userService.createUser(user)
		then:
		thrown(PasswordEmptyOrNullException)
	}

	def "should delete user when userDelete if user exist"() {
		when:
		userService.deleteUser(userId)
		then:
		1 * userRepository.findById(userId) >> Optional.of(new User(id: userId))
	}

	def "should throw ResourceNotFoundException when deleteUser if user doesn't exist"() {
		given:
		1 * userRepository.findById(userId) >> Optional.empty()
		when:
		userService.deleteUser(userId)
		then:
		thrown(ResourceNotfoundException)
	}

	def "should login user when loginUser if credentials are correct"() {
		given:
		User user = new User(email: "test@example.com", password: "password", isRegister: true)
		LoginDto loginDto = new LoginDto(email: "test@example.com", password: "password")
		1 * userRepository.findByEmail(user.getEmail()) >> Optional.of(user)
		1 * passwordEncoder.matches(loginDto.password, user.password) >> true
		userRepository.save(user)
		when:
		userService.loginUser(loginDto)
		then:
		user.isLogin
	}

	def "should throw BadCredentialsException when loginUser if credentials are incorrect"() {
		given:
		User user = new User(email: "test@example.com", password: "password", isRegister: false)
		1 * userRepository.findByEmail(user.getEmail()) >> Optional.of(user)
		1 * passwordEncoder.matches(loginDto.password, user.password) >> false
		userRepository.save(user)
		when:
		userService.loginUser(loginDto)
		then:
		thrown(BadCredentialsException)
	}

	def "should throw ResourceNotFoundException when loginUser if email doesn't exist"() {
		given:
		userRepository.findByEmail(loginDto.getEmail()) >> Optional.empty()
		when:
		userService.loginUser(loginDto)
		then:
		thrown(ResourceNotfoundException)
	}

	def "should logout user successfully when logoutUser if user exist"() {
		given:
		User user = new User(id: "userId", isLogin: true)
		1 * userRepository.findById(user.getId()) >> Optional.of(user)
		1 * userRepository.save(user)
		when:
		userService.logoutUser(user.getId())
		then:
		!user.isLogin
	}

	def "should get post details when getPostDetailsByPostId if post exists"() {
		given:
		Post post = new Post(id: "postId")
		PostDetailsDto expectedPostDetails = new PostDetailsDto(post: post)
		1 * postRepository.findById(post.getId()) >> Optional.of(post)
		when:
		PostDetailsDto result = userService.getPostDetailsByPostId(post.getId())
		then:
		result.post == expectedPostDetails.post
	}

	def "should throw ResourceNotFoundException when getPostDetailsByPostId post is not found"() {
		given:
		String postId = "postId"
		postRepository.findById(postId) >> Optional.empty()
		when:
		userService.getPostDetailsByPostId(postId)
		then:
		thrown(ResourceNotfoundException)
	}

	def "should get subscriber posts successfully when getSubscriberPosts"() {
		given:
		Subscription firstSubscription = new Subscription(id: "1", subscriberId: "firstSubscriberId")
		Subscription secondSubscription = new Subscription(id: "2", subscriberId: "secondSubscriberId")
		User user = new User()
		Post firstPost = new Post(userId: "firstSubscriberId", post: "content post 1")
		Post secondPost = new Post(userId: "secondSubscriberId", post: "content post 2")
		user.setPosts([firstPost, secondPost])
		user.setSubscriptions([firstSubscription, secondSubscription])
		userRepository.findById(_) >> Optional.of(user)
		userRepository.findById(firstSubscription.getId()) >> Optional.of(firstSubscription)
		userRepository.findById(secondSubscription.getId()) >> Optional.of(secondSubscription)
		when:
		List<SubscriberPostsDto> result = userService.getSubscriberPosts(userId)
		then:
		result.size() == 4
	}
}
