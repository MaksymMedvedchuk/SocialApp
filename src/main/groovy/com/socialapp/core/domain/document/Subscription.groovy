package com.socialapp.core.domain.document

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "Subscriptions")
class Subscription {

	@Id
	private String id
	private String targetUser
	private String subscriber
}
