package com.socialapp.core.domain.document

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "comments")
class Comment {

	@Id
	private String id
	private String userId
	private String postId
	private String comment
}
