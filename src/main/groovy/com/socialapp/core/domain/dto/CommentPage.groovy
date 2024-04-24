package com.socialapp.core.domain.dto

class CommentPage<CommentDto> {

	List<CommentDto> dto;
	int currentPage;
	long totalItems;
	int totalPages;
	int itemsPerPage;
}
