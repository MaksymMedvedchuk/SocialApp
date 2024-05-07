package com.socialapp.core.servicw.impl

import com.socialapp.core.domain.document.Comment
import com.socialapp.core.domain.document.Post
import com.socialapp.core.repository.CommentRepository
import com.socialapp.core.repository.PostRepository
import com.socialapp.core.service.CommentService
import com.socialapp.core.service.exception.ResourceNotfoundException
import com.socialapp.core.service.impl.CommentServiceImpl
import spock.lang.Specification

class CommentServiceSpec extends Specification {

	PostRepository postRepository = Mock()

	CommentRepository commentRepository = Mock()

	CommentService commentService = new CommentServiceImpl(commentRepository, postRepository)

	def "should save comment to post when saveCommentToPost if post exists"() {
		given:
		String userId = "userId"
		String postId = "postId"
		Comment comment = new Comment(comment: "Test comment")
		Comment savedComment = new Comment(id: "commentId", comment: "Test comment", userId: userId, postId: postId)
		Post post = new Post(id: postId)
		1 * postRepository.findById(postId) >> Optional.of(post)
		1 * commentRepository.insert(comment) >> savedComment
		when:
		Comment result = commentService.saveCommentToPost(comment, userId, postId)
		then:
		result == savedComment
	}

	def "should throw ResourceNotfoundException when saveCommentToPost if post does not exist"() {
		given:
		String userId = "userId"
		String postId = "postId"
		Comment comment = new Comment(comment: "Test comment")
		Comment savedComment = new Comment(id: "commentId", comment: "Test comment", userId: userId, postId: postId)
		1 * postRepository.findById(postId) >> Optional.empty()
		0 * commentRepository.insert(comment) >> savedComment
		when:
		commentService.saveCommentToPost(comment, userId, postId)
		then:
		thrown(ResourceNotfoundException)
	}
}
