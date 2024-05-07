package com.socialapp.controller

import com.socialapp.core.service.LikeService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import spock.lang.Shared
import spock.lang.Specification

class LikeControllerSpec extends Specification {

	LikeService likeService = Mock()

	LikeController controller = new LikeController(likeService)

	@Shared
	String userId

	@Shared
	String postId

	def setupSpec(){
		userId = "userId"
		postId = "postId"
	}

	def "should add like to post and return status OK"() {
		given:
		1 * likeService.saveLike(userId, postId)
		when:
		ResponseEntity<String> response = controller.likeOrDislike(true, userId, postId)
		then:
		response.statusCode == HttpStatus.OK
		response.body == "Like was added"
	}

	def "should delete like from post and return status OK"() {
		given:
		1 * likeService.deleteLike(userId, postId)
		when:
		ResponseEntity<String> response = controller.likeOrDislike(false, userId, postId)
		then:
		response.statusCode == HttpStatus.OK
		response.body == "Like was deleted"
	}
}
