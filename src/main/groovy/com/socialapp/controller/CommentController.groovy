package com.socialapp.controller

import com.socialapp.core.converter.DataConverter
import com.socialapp.core.converter.types.PageDataConverter
import com.socialapp.core.domain.document.Comment
import com.socialapp.core.domain.dto.CommentDto
import com.socialapp.core.domain.dto.CommentPage
import com.socialapp.core.service.CommentService
import groovy.util.logging.Slf4j
import io.swagger.v3.oas.annotations.Operation
import jakarta.validation.Valid
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@Slf4j
@RestController
@RequestMapping("/comments")
class CommentController {

	private final CommentService commentService

	private final PageDataConverter pageDataConverter

	private final DataConverter<Comment, CommentDto> converter

	CommentController(
			final CommentService commentService,
			final DataConverter<Comment, CommentDto> converter,
			final PageDataConverter pageDataConverter
	) {
		this.commentService = commentService
		this.converter = converter
		this.pageDataConverter = pageDataConverter
	}

	@PostMapping("/save")
	@Operation(summary = "Commenting on a post")
	ResponseEntity<CommentDto> saveComment(
			@RequestBody @Valid final CommentDto commentDto,
			@RequestParam("userId") final String userId,
			@RequestParam("postId") final String postId) {
		final Comment comment = converter.convertToDocument(commentDto)
		final Comment savedComment = commentService.saveCommentToPost(comment, userId, postId)
		final CommentDto result = converter.convertToDto(savedComment)
		log.info("In saveComment successful save comment for userId: [{}]", comment.getUserId())
		return new ResponseEntity<>(result, HttpStatus.OK)
	}

	@GetMapping("/comments/{postId}")
	@Operation(summary = "Get post comments")
	ResponseEntity<CommentPage<CommentDto>> getAllCommentsByPostId(
			@PathVariable("postId") final String postId,
			@PageableDefault final Pageable pageable) {
		final Page<Comment> commentPage = commentService.getCommentPageByPostId(postId, pageable)
		final CommentPage<CommentDto> dtoCommentPage = pageDataConverter.convertToPage(commentPage)
		log.debug("In getAllCommentsByPostId get all comments for postId: [{}]", postId)
		return new ResponseEntity<>(dtoCommentPage, HttpStatus.OK);
	}
}
