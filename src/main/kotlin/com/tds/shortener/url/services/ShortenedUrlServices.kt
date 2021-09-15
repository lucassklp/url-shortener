package com.tds.shortener.url.services

import com.tds.shortener.url.entities.ShortenedUrl
import com.tds.shortener.url.exceptions.ShortenedAlreadyTakenException
import com.tds.shortener.url.repository.ShortenerRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class ShortenedUrlServices(private val repository: ShortenerRepository) {

    fun newShortenedUrl(shortName: String, url: String): ShortenedUrl {
        val shortenedUrl = repository.findById(shortName)
        if(shortenedUrl.isPresent){
            throw ShortenedAlreadyTakenException()
        }
        return repository.save(ShortenedUrl(shortName, url, 0))
    }

    fun getShortenedUrl(shortName: String): Optional<ShortenedUrl> {
        return repository.findById(shortName)
    }

    fun getAndAccessShortenedUrl(shortName: String): Optional<ShortenedUrl> {
        val optionalShortenedUrl = repository.findById(shortName)
        if(optionalShortenedUrl.isPresent){
            val shortenedUrl = optionalShortenedUrl.get()
            shortenedUrl.count++
            repository.save(shortenedUrl)
        }
        return optionalShortenedUrl
    }
}