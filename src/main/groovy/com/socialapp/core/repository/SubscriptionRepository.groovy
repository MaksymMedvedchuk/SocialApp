package com.socialapp.core.repository


import com.socialapp.core.domain.document.Subscription
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query

interface SubscriptionRepository extends MongoRepository<Subscription, String> {

	@Query("{ 'userId' : ?0, 'subscriberId' : ?1 }")
	Subscription findSubscriptionByUserAndSubscriberIds(String userId, String subscriberId)

}