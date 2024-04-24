package com.socialapp.core.repository

import com.socialapp.core.domain.document.User
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository extends MongoRepository<User, String>{

	Optional<User> findByEmail(String email)
}