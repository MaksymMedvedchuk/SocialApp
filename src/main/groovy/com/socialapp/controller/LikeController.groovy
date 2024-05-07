package com.socialapp.controller


import com.socialapp.core.service.LikeService
import groovy.util.logging.Slf4j
import io.swagger.v3.oas.annotations.Operation
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@Slf4j
@RestController
@RequestMapping("/likes")
class LikeController {

	private final LikeService likeService

	LikeController(final LikeService likeService) {
		this.likeService = likeService
	}

	@PostMapping("/saveOrDelete")
	@Operation(summary = "If isLike is true like will add to post, if isLike is false like will delete from post")
	ResponseEntity<String> likeOrDislike(
			@RequestParam("isLike") final Boolean isLike,
			@RequestParam("userId") final String userId,
			@RequestParam("postId") final String postId) {
		if (isLike == true) {
			likeService.saveLike(userId, postId)
			log.info("In likeOrDislike successful save like to post: [{}]", postId)
			return new ResponseEntity<>("Like was added", HttpStatus.OK)
		}
		likeService.deleteLike(userId, postId)
		log.info("In likeOrDislike successful delete like from post: [{}]", postId)
		return new ResponseEntity<>("Like was deleted", HttpStatus.OK)
	}
}
