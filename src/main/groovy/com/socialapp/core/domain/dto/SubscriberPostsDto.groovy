package com.socialapp.core.domain.dto

class SubscriberPostsDto {

	 String post
	 String userId

	SubscriberPostsDto(final String post, final String userId) {
		this.post = post
		this.userId = userId
	}
}
