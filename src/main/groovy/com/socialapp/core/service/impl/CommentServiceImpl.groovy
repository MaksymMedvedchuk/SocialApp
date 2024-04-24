package com.socialapp.core.service.impl

import com.socialapp.core.domain.document.Comment
import com.socialapp.core.domain.document.Post
import com.socialapp.core.repository.CommentRepository
import com.socialapp.core.repository.PostRepository
import com.socialapp.core.service.CommentService
import com.socialapp.core.service.exception.ResourceNotfoundException
import groovy.util.logging.Slf4j
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service


@Slf4j
@Service
class CommentServiceImpl implements CommentService {

	private final PostRepository postRepository

	private final CommentRepository commentRepository

	private static final Integer PAGE = 1

	CommentServiceImpl(final CommentRepository commentRepository, final PostRepository postRepository) {
		this.commentRepository = commentRepository
		this.postRepository = postRepository
	}

	@Override
	Comment saveCommentToPost(final Comment comment, final String userId, final String postId) {
		final Post post = postRepository.findById(postId)
				.orElseThrow(() -> new ResourceNotfoundException("Post wasn't found with id: " + postId))
		List<Comment> comments = post.getComments()
		if (comments == null) comments = new ArrayList<>()
		comment.setUserId(userId)
		comment.setPostId(postId)
		final Comment savedComment = commentRepository.insert(comment)
		comments.add(savedComment)
		post.setComments(comments)
		postRepository.save(post)
		log.debug("In saveCommentToPost saved comment with id [{}] to user with id [{}]", savedComment.getId(), userId)
		return savedComment
	}

	@Override
	Page<Comment> getCommentPageByPostId(final String postId, final Integer page, final Integer limit) {
		Pageable pageable = PageRequest.of(page - PAGE, limit)
		log.debug("In getCommentPageByPostId found all comments for postId: [{}]", postId)
		return commentRepository.findCommentsByPostId(postId, pageable)
	}
}
