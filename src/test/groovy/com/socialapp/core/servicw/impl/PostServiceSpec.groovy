package com.socialapp.core.servicw.impl

import com.socialapp.core.domain.document.Post
import com.socialapp.core.domain.document.User
import com.socialapp.core.repository.CommentRepository
import com.socialapp.core.repository.LikeRepository
import com.socialapp.core.repository.PostRepository
import com.socialapp.core.repository.UserRepository
import com.socialapp.core.service.PostService
import com.socialapp.core.service.exception.ResourceNotfoundException
import com.socialapp.core.service.impl.PostServiceImpl
import spock.lang.Shared
import spock.lang.Specification

class PostServiceSpec extends Specification {

	PostRepository postRepository = Mock()

	UserRepository userRepository = Mock()

	LikeRepository likeRepository = Mock()

	CommentRepository commentRepository = Mock()

	PostService postService = new PostServiceImpl(postRepository, userRepository, commentRepository, likeRepository)

	@Shared
	String postId

	@Shared
	Post post

	@Shared
	User user

	@Shared
	Post updatedPost

	void setupSpec(){
		postId = "postId"
		post = new Post(id: "postId", userId: "userId")
		updatedPost = new Post(post: "updated post")
		user = new User()
	}

	def "should update post when updatedPost if post exists"() {
		given:
		1 * postRepository.existsById(updatedPost.getId()) >> true
		1 * postRepository.save(updatedPost) >> updatedPost
		when:
		Post result = postService.updatedPost(updatedPost)
		then:
		result == updatedPost
	}

	def "should throw ResourceNotFoundException when updatedPost if post does not exist"() {
		given:
		1 * postRepository.existsById(updatedPost.getId()) >> false
		0 * postRepository.save(updatedPost) >> updatedPost
		when:
		postService.updatedPost(updatedPost)
		then:
		thrown(ResourceNotfoundException)
	}

	def "should delete posts when deletePost if post exists"() {
		given:
		List<Post> posts = new ArrayList<>()
		user.setPosts(posts)
		1 * postRepository.findById(postId) >> Optional.of(post)
		1 * userRepository.findById(post.getUserId()) >> Optional.of(user)
		1 * commentRepository.deleteAll(post.getComments())
		1 * likeRepository.deleteAll(post.getLikes())
		1 * userRepository.save(user)
		when:
		postService.deletePost(postId)
		then:
		user.getPosts().isEmpty()
	}

	def "should throw ResourceNotFoundException when deletePost if post does not exist"() {
		given:
		String postId = "postId"
		1 * postRepository.findById(postId) >> Optional.empty()
		0 * userRepository.findById(post.getUserId()) >> Optional.of(user)
		0 * commentRepository.deleteAll(post.getComments())
		0 * likeRepository.deleteAll(post.getLikes())
		0 * userRepository.save(user)
		when:
		postService.deletePost(postId)
		then:
		thrown(ResourceNotfoundException)
	}

	def "should save post to user when savePostToUser"() {
		given:
		String userId = "userId"
		Post savedPost = new Post(post: "post", userId: "userId")
		Post post = new Post(post: "post")
		1 * userRepository.findById(userId) >> Optional.of(user)
		1 * postRepository.insert(post) >> savedPost
		when:
		Post result = postService.savePostToUser(post, userId)
		then:
		result == savedPost
	}
}

