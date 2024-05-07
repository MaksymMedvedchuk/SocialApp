package com.socialapp.controller

import com.socialapp.core.converter.DataConverter
import com.socialapp.core.converter.types.PageDataConverter
import com.socialapp.core.domain.document.Comment
import com.socialapp.core.domain.dto.CommentDto
import com.socialapp.core.domain.dto.CommentPage
import com.socialapp.core.service.CommentService
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import spock.lang.Shared
import spock.lang.Specification

class CommentControllerSpec extends Specification{

	CommentService commentService = Mock()

	DataConverter converter = Mock()

	PageDataConverter pageDataConverter = Mock()

	CommentController controller = new CommentController(commentService, converter, pageDataConverter)

	@Shared
	CommentDto commentDto

	@Shared
	String postId

	def setupSpec(){
		commentDto = new CommentDto()
		postId = "postId"
	}

	def "should save comment and return status OK"() {
		given:
		String userId = "userId"
		Comment comment = new Comment()
		1 * converter.convertToDocument(commentDto) >> comment
		1 * commentService.saveCommentToPost(comment, userId, postId) >> comment
		1 * converter.convertToDto(comment) >> commentDto
		when:
		ResponseEntity<CommentDto> response = controller.saveComment(commentDto, userId, postId)
		then:
		response.statusCode == HttpStatus.OK
		response.body == commentDto
	}

	def "should get all comments by post id and return status OK"() {
		given:
		Pageable pageable = Pageable.unpaged()
		Page<Comment> commentPage = new PageImpl<>([])
		CommentPage<CommentDto> page = new CommentPage<>()
		1 * commentService.getCommentPageByPostId(postId, pageable) >> commentPage
		1 * pageDataConverter.convertToPage(commentPage) >> page
		when:
		ResponseEntity<CommentPage<CommentDto>> response = controller.getAllCommentsByPostId(postId, pageable)
		then:
		response.statusCode == HttpStatus.OK
		response.body == page
	}
}
