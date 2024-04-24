package com.socialapp.core.domain.document

import groovy.transform.builder.Builder
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Builder
@Document(collection = "subscriptions")
class Subscription {

	@Id
	String id
	String userId
	String subscriberId
	boolean isSubscription
}
