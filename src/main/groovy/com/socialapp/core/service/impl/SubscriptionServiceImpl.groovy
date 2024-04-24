package com.socialapp.core.service.impl

import com.socialapp.core.domain.document.Subscription
import com.socialapp.core.domain.document.User
import com.socialapp.core.repository.SubscriptionRepository
import com.socialapp.core.repository.UserRepository
import com.socialapp.core.service.SubscriptionService
import com.socialapp.core.service.exception.ResourceNotfoundException
import groovy.util.logging.Slf4j
import org.springframework.stereotype.Service

@Slf4j
@Service
class SubscriptionServiceImpl implements SubscriptionService {

	private final SubscriptionRepository subscriptionRepository

	private final UserRepository userRepository

	SubscriptionServiceImpl(final SubscriptionRepository subscriptionRepository, final UserRepository userRepository) {
		this.subscriptionRepository = subscriptionRepository
		this.userRepository = userRepository
	}

	@Override
	void subscribe(final String userId, final String subscriberId) {
		final User user = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotfoundException("User wasn't found with id: " + userId))
		List<Subscription> subscriptions = user.getSubscriptions()
		if (subscriptions == null) subscriptions = new ArrayList<>()
		final Subscription subscription = createSubscription(userId, subscriberId)
		subscriptionRepository.insert(subscription)
		subscriptions.add(subscription)
		user.setSubscriptions(subscriptions)
		userRepository.save(user)
		log.debug("In subscribe user with id [{}] subscribed to user with id [{}]", subscriberId, userId)
	}

	private Subscription createSubscription(String userId, String subscriberId) {
		final Subscription subscription = Subscription.builder()
				.userId(userId)
				.subscriberId(subscriberId)
				.isSubscription(true)
				.build()
		return subscription
	}

	@Override
	void unsubscribe(final String userId, final String subscriberId) {
		final User user = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotfoundException("User wasn't found with id: " + userId))
		final Subscription subscription = subscriptionRepository.findSubscriptionByUserAndSubscriberIds(userId, subscriberId)
		subscriptionRepository.deleteById(subscription.getId())
		user.getSubscriptions().removeIf(sub -> sub.getId().equals(subscription.getId()))
		userRepository.save(user)
	}
}
