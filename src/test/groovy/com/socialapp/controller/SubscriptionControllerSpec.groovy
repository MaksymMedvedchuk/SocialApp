package com.socialapp.controller

import com.socialapp.core.service.NotificationService
import com.socialapp.core.service.SubscriptionService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import spock.lang.Shared
import spock.lang.Specification

class SubscriptionControllerSpec extends Specification {

	SubscriptionService subscriptionService = Mock()

	NotificationService notificationService = Mock()

	SubscriptionController controller = new SubscriptionController(subscriptionService, notificationService)

	@Shared
	String userId

	@Shared
	String subscriberId

	def setupSpec() {
		userId = "userId"
		subscriberId = "subscriberId"

	}

	def "should subscribe and return status OK"() {
		given:
		1 * subscriptionService.subscribe(userId, subscriberId)
		1 * notificationService.sendNotification(userId, subscriberId)
		when:
		ResponseEntity<String> response = controller.subscribeOrUnsubscribe(true, userId, subscriberId)
		then:
		response.statusCode == HttpStatus.OK
		response.body == "Successful subscription"
	}

	def "should unsubscribe and return status OK"() {
		given:
		1 * subscriptionService.unsubscribe(userId, subscriberId)
		when:
		ResponseEntity<String> response = controller.subscribeOrUnsubscribe(false, userId, subscriberId)
		then:
		response.statusCode == HttpStatus.OK
		response.body == "Successful unsubscription"
	}
}