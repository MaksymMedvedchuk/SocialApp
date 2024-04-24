package com.socialapp.core.converter

interface Converter<D, DTO> {

	DTO convertToDto(D document);

	//boolean isSupport(Class<?> documentClass, Class<?> dtoClass);

	D convertToDocument(DTO dto);

}