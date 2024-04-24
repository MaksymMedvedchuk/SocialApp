package com.socialapp.core.domain.document

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.DBRef
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "posts")
class Post {

	@Id
	private String id
	private String userId
	private String post
	@DBRef
	private List<Comment> comments;
	@DBRef
	private List<Like> likes;



}
