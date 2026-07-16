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
import com.kevin.spring_library_api.model.Genre;

public record BookRequestDTO(
        @NotBlank String title,
        @NotBlank String author,
        @NotBlank String publisher,
        @NotNull @Positive BigDecimal price,
        @NotNull @Positive Integer publicationYear,
        @NotNull @Min(1) Integer numberOfPages,

        @NotBlank
        @Pattern(
                regexp = "^97[89]\\d{10}$",
                message = "ISBN must contain 13 digits and start with 978 or 979"
        )
        String isbn,

        @NotNull Language language,
        @NotEmpty Set<Genre> genres
) {}
