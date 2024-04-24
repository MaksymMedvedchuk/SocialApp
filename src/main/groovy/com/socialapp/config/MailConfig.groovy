package com.socialapp.config


import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper

@Configuration
class MailConfig {

	private final JavaMailSender javaMailSender

	MailConfig(final JavaMailSender javaMailSender) {
		this.javaMailSender = javaMailSender
	}

	@Bean
	MimeMessageHelper mimeMessageHelper() {
		return new MimeMessageHelper(javaMailSender.createMimeMessage())
	}
}
