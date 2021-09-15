package com.tds.shortener.url.dtos

data class CreateShortenedResponseDto (
        val link: String,
        val shortName: String,
        val redirectsTo: String
)