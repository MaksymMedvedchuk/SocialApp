package com.socialapp.core.domain.document

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.DBRef
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "posts")
class Post {

	@Id
	String id
	String userId
	String post
	@DBRef
	List<Comment> comments
	@DBRef
	List<Like> likes
}
