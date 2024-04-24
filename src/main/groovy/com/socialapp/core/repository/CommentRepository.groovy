package com.socialapp.core.repository

import com.socialapp.core.domain.document.Comment
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query

interface CommentRepository extends MongoRepository<Comment, String>{

	@Query("{ 'postId' : ?0 }")
	Page<Comment> findCommentsByPostId(String postId, Pageable pageable)
}
