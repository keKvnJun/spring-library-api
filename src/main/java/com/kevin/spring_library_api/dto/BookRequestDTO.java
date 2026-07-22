package com.kevin.spring_library_api.dto;

import java.math.BigDecimal;
import java.util.Set;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Min;
import com.kevin.spring_library_api.model.Language;

import io.swagger.v3.oas.annotations.media.Schema;

import com.kevin.spring_library_api.model.Genre;

public record BookRequestDTO(

        @NotBlank
        @Schema(description = "Title of the book", example = "The Chronicles of Narnia")
        String title,

        @NotBlank
        @Schema(description = "Author of the book", example = "C. S. Lewis")
        String author,

        @NotBlank
        @Schema(description = "Publisher of the book", example = "HarperCollins")
        String publisher,

        @NotNull
        @Positive
        @Schema(description = "Price of the book", example = "39.99")
        BigDecimal price,

        @NotNull
        @Positive
        @Schema(description = "Publication year of the book", example = "2001")
        Integer publicationYear,

        @NotNull
        @Min(1)
        @Schema(description = "Number of pages in the book", example = "767")
        Integer numberOfPages,

        @NotBlank
        @Pattern(
                regexp = "^97[89]\\d{10}$",
                message = "ISBN must contain 13 digits and start with 978 or 979"
        )
        @Schema(description = "ISBN of the book", example = "9780066238500")
        String isbn,

        @NotNull
        @Schema(description = "Language of the book", example = "ENGLISH")
        Language language,

        @NotEmpty
        @Schema(description = "Genres of the book", example = "[\"FICTION\", \"CHRISTIAN_LITERATURE\"]")
        Set<@NotNull Genre> genres
) {}
