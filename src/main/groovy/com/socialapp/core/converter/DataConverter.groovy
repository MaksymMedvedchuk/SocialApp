package com.socialapp.core.converter

interface DataConverter<D, DTO> {

	DTO convertToDto(D document)

	D convertToDocument(DTO dto)
}