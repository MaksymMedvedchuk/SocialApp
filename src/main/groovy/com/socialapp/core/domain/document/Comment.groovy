package com.socialapp.core.domain.document

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "comments")
class Comment {

	@Id
	String id
	String userId
	String postId
	String comment
}
