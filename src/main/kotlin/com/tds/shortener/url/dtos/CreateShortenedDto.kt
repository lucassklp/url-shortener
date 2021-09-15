package com.tds.shortener.url.dtos

import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Pattern

data class CreateShortenedDto(
    @field:NotNull
    @field:Pattern(
            regexp = "^[a-zA-Z0-9\\.\\-\\_]+\$",
            message = "Only letters, numbers and (., -, _) characters are allowed"
    )
    val shortName: String,

    @field:NotNull
    @field:NotBlank
    @field:Pattern(
        regexp = "https?:\\/\\/(www\\.)?[-a-zA-Z0-9@:%._\\+~#=]{1,256}\\.[a-zA-Z0-9()]{1,6}\\b([-a-zA-Z0-9()@:%_\\+.~#?&\\/\\/=]*)",
        message = "Invalid URL. Please, provide a valid URL"
    )
    val url: String
)