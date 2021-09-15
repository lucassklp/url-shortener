package com.tds.shortener.url.repository

import com.tds.shortener.url.entities.ShortenedUrl
import org.springframework.data.repository.CrudRepository

interface ShortenerRepository : CrudRepository<ShortenedUrl, String> {
}
