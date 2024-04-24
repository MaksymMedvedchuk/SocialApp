package com.socialapp.core.converter.types

import com.socialapp.core.domain.document.Comment
import com.socialapp.core.domain.dto.CommentDto
import com.socialapp.core.domain.dto.CommentPage
import groovy.util.logging.Slf4j
import org.modelmapper.ModelMapper
import org.springframework.data.domain.Page
import org.springframework.stereotype.Component

@Slf4j
@Component
class PageDataConverter {

	private final ModelMapper modelMapper

	PageDataConverter(final ModelMapper modelMapper) {
		this.modelMapper = modelMapper
	}

	CommentPage<CommentDto> convertToPage(Page<Comment> page) {
		CommentPage<CommentDto> customPage = new CommentPage<>()
		List<CommentDto> list =  page.stream()
				.map(document -> convertToDto(document))
				.toList()
		customPage.setDto(list)
		customPage.setCurrentPage(page.getNumber() + 1)
		customPage.setTotalItems(page.getTotalElements())
		customPage.setTotalPages(page.getTotalPages())
		customPage.setItemsPerPage(page.getNumberOfElements())
		log.debug("In convertToPage convert page pf comments to custom page")
		return customPage
	}

	CommentDto convertToDto(final Comment document) {
		return modelMapper.map(document, CommentDto.class)
	}
}
