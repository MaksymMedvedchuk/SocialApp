package com.socialapp.core.converter.types

import com.socialapp.core.converter.DataConverter
import com.socialapp.core.domain.document.Comment
import com.socialapp.core.domain.dto.CommentDto
import org.modelmapper.ModelMapper
import org.springframework.stereotype.Component

@Component
class CommentDataConverter implements DataConverter<Comment, CommentDto> {

	private final ModelMapper modelMapper

	CommentDataConverter(final ModelMapper modelMapper) {
		this.modelMapper = modelMapper
	}

	@Override
	CommentDto convertToDto(final Comment document) {
		return modelMapper.map(document, CommentDto.class)
	}

	@Override
	Comment convertToDocument(final CommentDto commentDto) {
		final Comment comment = modelMapper.map(commentDto, Comment.class)
		comment.setMetaClass(null)
		return comment
	}
}
