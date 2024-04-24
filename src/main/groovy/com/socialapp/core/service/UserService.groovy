package com.socialapp.core.service


import com.socialapp.core.domain.document.User
import com.socialapp.core.domain.dto.LoginDto
import com.socialapp.core.domain.dto.PostDetailsDTO

interface UserService {

	User createUser(User user)

	void deleteUser(String userId)

	void loginUser(LoginDto loginDto)

	void logoutUser(String userId)

	PostDetailsDTO getPostDetailsByUserIdAndPostId(String postId)
}