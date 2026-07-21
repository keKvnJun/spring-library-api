package com.kevin.spring_library_api.dto;

import java.math.BigDecimal;

import com.kevin.spring_library_api.model.Genre;
import com.kevin.spring_library_api.model.Language;
import java.util.Set;

public record BookResponseDTO(
        Long id,
        String title,
        String author,
        String publisher,
        BigDecimal price,
        Integer publicationYear,
        Integer numberOfPages,
        String isbn,
        Language language,
        Set<Genre> genres
) {}
