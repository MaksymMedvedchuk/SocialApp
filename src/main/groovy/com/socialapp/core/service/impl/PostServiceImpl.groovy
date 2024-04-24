package com.socialapp.core.service.impl

import com.socialapp.core.domain.document.Post
import com.socialapp.core.domain.document.User
import com.socialapp.core.repository.CommentRepository
import com.socialapp.core.repository.LikeRepository
import com.socialapp.core.repository.PostRepository
import com.socialapp.core.repository.UserRepository
import com.socialapp.core.service.PostService
import com.socialapp.core.service.exception.ResourceNotfoundException
import groovy.util.logging.Slf4j
import org.springframework.stereotype.Service

@Slf4j
@Service
class PostServiceImpl implements PostService {

	private final PostRepository postRepository

	private final UserRepository userRepository

	private final LikeRepository likeRepository

	private final CommentRepository commentRepository

	PostServiceImpl(final PostRepository postRepository,
					final UserRepository userRepository,
					final CommentRepository commentRepository,
					final LikeRepository likeRepository) {
		this.postRepository = postRepository
		this.userRepository = userRepository
		this.commentRepository = commentRepository
		this.likeRepository = likeRepository
	}

	@Override
	Post updatedPost(final Post post) {
		if (!postRepository.existsById(post.getId())) {
			throw new ResourceNotfoundException("Post wasn't found with id: " + post.getId())
		}
		log.debug("In updatedPost trying to update post with id: [{}]", post.getId())
		return postRepository.save(post)
	}

	@Override
	void deletePost(final String postId) {
		final Post post = postRepository.findById(postId)
				.orElseThrow(() -> new ResourceNotfoundException("Post wasn't found with id: " + postId))
		postRepository.deleteById(postId)
		final User user = userRepository.findById(post.getUserId())
				.orElseThrow(() -> new ResourceNotfoundException("User wasn't found with id: " + userId))
		List<Post> posts = user.getPosts()
		posts.remove(postId)
		commentRepository.deleteAll(post.getComments())
		likeRepository.deleteAll(post.getLikes())
		userRepository.save(user)
	}

	@Override
	Post savePostToUser(final Post post, final String userId) {
		final User user = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotfoundException("User wasn't found with id: " + userId))
		List<Post> posts = user.getPosts()
		if (posts == null) {
			posts = new ArrayList<>()
		}
		post.setUserId(userId)
		final Post savedPost = postRepository.insert(post)
		posts.add(savedPost)
		user.setPosts(posts)
		userRepository.save(user)
		log.debug("In savePostToUser saved post with id [{}] to user with id [{}]", post.getId(), userId)
		return savedPost
	}
}
