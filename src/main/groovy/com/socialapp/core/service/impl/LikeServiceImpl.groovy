package com.socialapp.core.service.impl

import com.socialapp.core.domain.document.Like
import com.socialapp.core.domain.document.Post
import com.socialapp.core.repository.LikeRepository
import com.socialapp.core.repository.PostRepository
import com.socialapp.core.service.LikeService
import com.socialapp.core.service.exception.ResourceNotfoundException
import groovy.util.logging.Slf4j
import org.springframework.stereotype.Service

@Slf4j
@Service
class LikeServiceImpl implements LikeService {

	private final PostRepository postRepository

	private final LikeRepository likeRepository

	LikeServiceImpl(
			final LikeRepository likeRepository,
			final PostRepository postRepository
	) {
		this.likeRepository = likeRepository
		this.postRepository = postRepository
	}

	void saveLike(final String userId, final String postId) {
		final Post post = postRepository.findById(postId)
				.orElseThrow(() -> new ResourceNotfoundException("Post wasn't found with id: " + postId))
		List<Like> likes = post.getLikes()
		if (likes == null) likes = new ArrayList<>()
		final Like like = new Like()
		like.setIsLike(true)
		like.setPostId(postId)
		like.setUserId(userId)
		likeRepository.insert(like)
		likes.add(like)
		post.setLikes(likes)
		postRepository.save(post)
		log.debug("In saveLike saved like with id [{}] to post with id [{}]", like.getId(), postId)
	}

	void deleteLike(final String userId, final String postId) {
		Like like = likeRepository.findLikeByUserAndPostId(userId, postId)
		final Post post = postRepository.findById(postId)
				.orElseThrow(() -> new ResourceNotfoundException("Post wasn't found with id: " + postId))
		post.getLikes().removeIf(lik -> lik.getId().equals(like.getId()))
		postRepository.save(post)
		likeRepository.deleteById(like.getId())
		log.debug("In deleteLike deleted like with id [{}] to post with id [{}]", like.getId(), postId)
	}
}
