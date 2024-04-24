package com.socialapp.core.service

interface NotificationMessageProvider {

	void sendNotification(String userId, String subscriberId)

}