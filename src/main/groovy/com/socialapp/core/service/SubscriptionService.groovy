package com.socialapp.core.service

interface SubscriptionService {

	void subscribe(String userId, String subscriberId)

	void unsubscribe(String userId, String subscriberId)
}