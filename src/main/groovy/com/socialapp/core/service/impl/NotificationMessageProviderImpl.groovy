package com.socialapp.core.service.impl

import com.socialapp.core.domain.document.User
import com.socialapp.core.repository.UserRepository
import com.socialapp.core.service.NotificationService
import groovy.util.logging.Slf4j
import jakarta.mail.MessagingException
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.stereotype.Component

@Slf4j
@Component
class NotificationMessageProviderImpl implements NotificationService {

	private final UserRepository userRepository

	private final MimeMessageHelper mimeMessageHelper

	private final static String FROM_ADDRESS = "notificationsciapapp@gmail.com"

	private final JavaMailSender javaMailSender

	private static final String SENDER_NAME = "SocialApp"

	NotificationMessageProviderImpl(
			final UserRepository userRepository,
			final MimeMessageHelper mimeMessageHelper,
			final JavaMailSender javaMailSender) {
		this.userRepository = userRepository
		this.mimeMessageHelper = mimeMessageHelper
		this.javaMailSender = javaMailSender
	}

	private void createNotificationMessage(final String userId, final String subscriberId) {
		final User targetUser = userRepository.findById(userId).orElseThrow()
		final User subscriber = userRepository.findById(subscriberId).orElseThrow()
		final String subject = "Subscription notification"
		final String toAddress = targetUser.getEmail()
		final String content = "<html><body>" +
				"<p>User with name " + subscriber.getUserName() + ",</p>" +
				"<p>subscribed to your page</p>" + "</body></html>"
		try {
			mimeMessageHelper.setFrom(FROM_ADDRESS, SENDER_NAME)
			mimeMessageHelper.setSubject(subject)
			mimeMessageHelper.setTo(toAddress)
			mimeMessageHelper.setText(content, true)
		} catch (MessagingException e) {
			log.warn("In createNotificationMessage exception: [{}]", e.getMessage())
		}
	}

	void sendNotification(final String userId, final String subscriberId) {
		createNotificationMessage(userId, subscriberId)
		log.info("In sendNotification sent notification about subscription to user with id: [{}]", userId)
		javaMailSender.send(mimeMessageHelper.getMimeMessage())
	}
}
