package com.socialapp.core.service

import com.socialapp.core.domain.document.Comment
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface CommentService {

	Comment saveCommentToPost(Comment comment, String userId, String postId)

	Page<Comment> getCommentPageByPostId(String postId, Pageable pageable)
}