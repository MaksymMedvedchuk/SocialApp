package com.socialapp.core.domain.document

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "likes")
class Like {

	@Id
	String id
	String userId
	String postId
	boolean IsLike
}
