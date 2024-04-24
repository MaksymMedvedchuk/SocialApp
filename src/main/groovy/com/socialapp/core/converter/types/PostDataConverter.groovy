package com.socialapp.core.converter.types

import com.socialapp.core.converter.DataConverter
import com.socialapp.core.domain.document.Post
import com.socialapp.core.domain.dto.PostDto
import org.modelmapper.ModelMapper
import org.springframework.stereotype.Component

@Component
class PostDataDataConverter implements DataConverter<Post, PostDto>{

	private final ModelMapper modelMapper

	PostDataDataConverter(final ModelMapper modelMapper) {
		this.modelMapper = modelMapper
	}

	@Override
	PostDto convertToDto(final Post document) {
		return modelMapper.map(document, PostDto.class)
	}

	@Override
	Post convertToDocument(final PostDto postDto) {
		final Post post = modelMapper.map(postDto, Post.class)
		post.setMetaClass(null)
		return post
	}
}
