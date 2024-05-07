package com.socialapp.controller

import com.socialapp.core.service.NotificationService
import com.socialapp.core.service.SubscriptionService
import groovy.util.logging.Slf4j
import io.swagger.v3.oas.annotations.Operation
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@Slf4j
@RestController
@RequestMapping("/subscriptions")
class SubscriptionController {

	private final SubscriptionService subscriptionService

	private final NotificationService notificationService

	SubscriptionController(
			final SubscriptionService subscriptionService,
			final NotificationService notificationService
	) {
		this.subscriptionService = subscriptionService
		this.notificationService = notificationService
	}

	@PostMapping("/subscribeOrUnsubscribe")
	@Operation(summary = "If isSubscription is true the user will subscribes, if isSubscription is false the user will unsubscribes")
	ResponseEntity<String> subscribeOrUnsubscribe(
			@RequestParam("isSubscription") final Boolean isSubscription,
			@RequestParam("userId") final String userId,
			@RequestParam("subscriberId") final String subscriberId) {
		if (isSubscription == true) {
			subscriptionService.subscribe(userId, subscriberId)
			log.info("In subscribeOrUnsubscribe user with id [{}] subscribed to user with id: [{}]", subscriberId, userId)

			notificationService.sendNotification(userId, subscriberId)
			log.info("In subscribeOrUnsubscribe subscriberId [{}] subscribes to userId [{}]", subscriberId, userId)

			return new ResponseEntity<>("Successful subscription", HttpStatus.OK)
		}
		subscriptionService.unsubscribe(userId, subscriberId)
		log.info("In subscribeOrUnsubscribe user with id [{}] unsubscribed from user with id: [{}]", subscriberId, userId)
		return new ResponseEntity<>("Successful unsubscription", HttpStatus.OK)
	}
}
