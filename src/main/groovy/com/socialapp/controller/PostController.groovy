package com.socialapp.controller

import com.socialapp.core.converter.DataConverter
import com.socialapp.core.domain.document.Post
import com.socialapp.core.domain.dto.PostDto
import com.socialapp.core.service.PostService
import groovy.util.logging.Slf4j
import io.swagger.v3.oas.annotations.Operation
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@Slf4j
@RestController
@RequestMapping("/posts")
class PostController {

	private final DataConverter<Post, PostDto> converter

	private final PostService postService

	PostController(
			final DataConverter<Post, PostDto> converter,
			final PostService postService) {
		this.converter = converter
		this.postService = postService
	}

	@PostMapping("/save/{userId}")
	@Operation(summary = "Create a post")
	ResponseEntity<PostDto> savePost(
			@RequestBody @Valid final PostDto postDto,
			@PathVariable("userId") final String userId) {
		final Post post = converter.convertToDocument(postDto)
		final Post savedPost = postService.savePostToUser(post, userId)
		final PostDto result = converter.convertToDto(savedPost)
		log.info("In savePost successful save post for userId: [{}]", post.getUserId());
		return new ResponseEntity<>(result, HttpStatus.CREATED)
	}

	@PostMapping("/edit/{postId}")
	@Operation(summary = "Edit post")
	ResponseEntity<PostDto> editPost(
			@RequestBody @Valid final PostDto postDto,
			@PathVariable("postId") final String postId) {
		final Post post = converter.convertToDocument(postDto)
		post.setId(postId)
		final Post updatedPost = postService.updatedPost(post)
		final PostDto result = converter.convertToDto(updatedPost)
		log.info("In editPost successful update post for postId: [{}]", post.getId());
		return new ResponseEntity<>(result, HttpStatus.OK)
	}

	@DeleteMapping("delete/{postId}")
	@Operation(summary = "Delete post")
	ResponseEntity<String> deletePost(@PathVariable("postId") final String postId) {
		postService.deletePost(postId)
		log.info("In deletePost successful delete post for postId: [{}]", postId)
		return new ResponseEntity<>("Delete successful", HttpStatus.OK)
	}
}
