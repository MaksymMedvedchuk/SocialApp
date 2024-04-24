package com.socialapp.core.service

import com.socialapp.core.domain.document.Post

interface PostService {

	Post savePostToUser(Post post, String userId)

	Post updatedPost(Post post)

	void deletePost(String postId)
}