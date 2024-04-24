package com.socialapp.core.service

import com.socialapp.core.domain.document.Comment
import org.springframework.data.domain.Page

interface CommentService {

	Comment saveCommentToPost(Comment comment, String userId, String postId)

	Page<Comment> getCommentPageByPostId(String postId, Integer page, Integer limit)
}