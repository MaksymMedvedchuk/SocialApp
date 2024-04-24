package com.socialapp.core.repository

import com.socialapp.core.domain.document.Post
import org.springframework.data.mongodb.repository.MongoRepository

interface PostRepository extends MongoRepository<Post, String> {

}