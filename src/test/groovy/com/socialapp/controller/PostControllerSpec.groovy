package com.socialapp.controller

import com.socialapp.core.converter.DataConverter
import com.socialapp.core.domain.document.Post
import com.socialapp.core.domain.dto.PostDto
import com.socialapp.core.service.PostService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import spock.lang.Shared
import spock.lang.Specification

class PostControllerSpec extends Specification {

	DataConverter converter = Mock(DataConverter.class)

	PostService postService = Mock(PostService.class)

	PostController controller = new PostController(converter, postService)

	@Shared
	Post post

	@Shared
	PostDto postDto

	def setupSpec() {
		post = new Post()
		postDto = new PostDto()
	}

	def "should create post and return status CREATE"() {
		given:
		1 * converter.convertToDocument(postDto) >> post
		1 * postService.savePostToUser(post, post.getUserId()) >> post
		1 * converter.convertToDto(post) >> postDto
		when:
		ResponseEntity<PostDto> response = controller.savePost(postDto, post.getUserId())
		then:
		response.statusCode == HttpStatus.CREATED
		response.body == postDto
	}

	def "should edit post and return status OK"() {
		given:
		1 * converter.convertToDocument(postDto) >> post
		1 * postService.updatedPost(post) >> post
		1 * converter.convertToDto(post) >> postDto
		when:
		ResponseEntity<PostDto> response = controller.editPost(postDto, post.getId())
		then:
		response.statusCode == HttpStatus.OK
		response.body == postDto
	}

	def "should delete post and return status OK"() {
		given:
		1 * postService.deletePost(post.getId())
		when:
		ResponseEntity<String> response = controller.deletePost(post.getId())
		then:
		response.statusCode == HttpStatus.OK
	}
}
