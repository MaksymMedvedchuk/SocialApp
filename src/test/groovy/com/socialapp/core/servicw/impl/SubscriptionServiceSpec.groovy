package com.socialapp.core.servicw.impl

import com.socialapp.core.domain.document.Subscription
import com.socialapp.core.domain.document.User
import com.socialapp.core.repository.SubscriptionRepository
import com.socialapp.core.repository.UserRepository
import com.socialapp.core.service.SubscriptionService
import com.socialapp.core.service.impl.SubscriptionServiceImpl
import spock.lang.Shared
import spock.lang.Specification

class SubscriptionServiceSpec extends Specification {

	SubscriptionRepository subscriptionRepository = Mock()

	UserRepository userRepository = Mock()

	SubscriptionService subscriptionService = new SubscriptionServiceImpl(subscriptionRepository, userRepository)

	@Shared
	User user

	@Shared
	Subscription subscription

	def setupSpec() {
		user = new User(id: "userId")
		subscription = new Subscription(userId: user.getId(), subscriberId: "subscriberId")
	}

	def "should subscribe user to another user when subscribe"() {
		given:
		1 * userRepository.findById(user.getId()) >> Optional.of(user)
		1 * subscriptionRepository.insert(_) >> subscription
		1 * userRepository.save(user)
		when:
		subscriptionService.subscribe(subscription.userId, subscription.subscriberId)
		then:
		user.getSubscriptions().size() == 1
	}


	def "should unsubscribe user from another user when unsubscribe"() {
		given:
		user.setSubscriptions([subscription])
		1 * userRepository.findById(user.getId()) >> Optional.of(user)
		1 * subscriptionRepository.findSubscriptionByUserAndSubscriberIds(user.getId(), subscription.getId()) >> subscription
		when:
		subscriptionService.unsubscribe(user.getId(), subscription.getId())
		then:
		user.getSubscriptions().size() == 0
	}
}

