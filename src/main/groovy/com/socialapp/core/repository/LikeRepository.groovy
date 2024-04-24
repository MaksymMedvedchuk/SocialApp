package com.socialapp.core.repository

import com.socialapp.core.domain.document.Like
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query

interface LikeRepository extends MongoRepository<Like, String> {

	@Query("{ 'userId' : ?0, 'postId' : ?1 }")
	Like findLikeByUserAndPostId(String userId, String postId)
}