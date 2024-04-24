package com.socialapp.core.service

interface LikeService {

	void saveLike(final String userId, final String postId)

	void deleteLike(final String userId, final String postId)

}