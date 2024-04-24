package com.socialapp.core.domain.document

import groovy.transform.builder.Builder
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.DBRef
import org.springframework.data.mongodb.core.mapping.Document

@Builder
@Document(collection = "users")
class User {
	@Id
	String id
	@Indexed(unique = true)
	String userName
	String email
	String password
	boolean isRegister
	boolean isLogin
	@DBRef
	List<Post> posts
	@DBRef
	List<Subscription> subscriptions
}
