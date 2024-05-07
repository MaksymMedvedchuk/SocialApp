package com.socialapp.core.servicw.impl

import com.socialapp.core.domain.document.Like
import com.socialapp.core.domain.document.Post
import com.socialapp.core.repository.LikeRepository
import com.socialapp.core.repository.PostRepository
import com.socialapp.core.service.LikeService
import com.socialapp.core.service.exception.ResourceNotfoundException
import com.socialapp.core.service.impl.LikeServiceImpl
import spock.lang.Shared
import spock.lang.Specification

class LikeServiceSpec extends Specification{

	PostRepository postRepository = Mock()

	LikeRepository likeRepository  = Mock()

	LikeService likeService = new LikeServiceImpl(likeRepository, postRepository)

	@Shared
	String userId

	@Shared
	String postId

	@Shared
	Like like

	void setupSpec(){
		userId = "userId"
		postId = "postId"
		like = new Like(isLike: true, postId: postId, userId: userId)
	}

	def "should save like to post when saveLike is post exists"() {
		given:
		Post post = new Post(id: postId)
		1 * postRepository.findById(postId) >> Optional.of(post)
		1 * likeRepository.insert(_) >> like
		when:
		likeService.saveLike(userId, postId)
		then:
		like.isIsLike()
	}

	def "should throw ResourceNotFoundException when saveLike if post does not exist"() {
		given:
		1 * postRepository.findById(postId) >> Optional.empty()
		0 * likeRepository.insert(_)
		when:
		likeService.saveLike(userId, postId)
		then:
		thrown(ResourceNotfoundException)
	}

	def "should delete like from post when deleteById"() {
		given:
		Post post = new Post(id: postId, likes: [like])
		1 * likeRepository.findLikeByUserAndPostId(userId, postId) >> like
		1 * postRepository.findById(postId) >> Optional.of(post)
		1 * postRepository.save(post)
		1 * likeRepository.deleteById(like.getId())
		when:
		likeService.deleteLike(userId, postId)
		then:
		post.getLikes().isEmpty()
	}
}
