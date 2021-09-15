package com.tds.shortener.url.controllers

import com.tds.shortener.url.dtos.CreateShortenedDto
import com.tds.shortener.url.dtos.CreateShortenedResponseDto
import com.tds.shortener.url.entities.ShortenedUrl
import com.tds.shortener.url.services.ShortenedUrlServices
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import java.net.URI
import javax.validation.Valid

@Validated
@RestController
class ShortenedController(private val services: ShortenedUrlServices) {

    @GetMapping("/{shortName}")
    fun accessUrl(@PathVariable("shortName") id: String): ResponseEntity<ShortenedUrl> {
        val shortened = services.getAndAccessShortenedUrl(id)
        if(shortened.isPresent){
            val headers = HttpHeaders()
            headers.location = URI.create(shortened.get().target)
            return ResponseEntity(headers, HttpStatus.MOVED_PERMANENTLY)
        }
        return ResponseEntity.notFound().build()
    }

    @GetMapping("/shortened/{shortName}/statistics")
    fun getStatistics(@PathVariable shortName: String): ResponseEntity<ShortenedUrl> {
        val shortened = services.getShortenedUrl(shortName)
        if(shortened.isPresent){
            return ResponseEntity.ok(shortened.get())
        }
        return ResponseEntity.notFound().build()
    }

    @PostMapping("/shortened/")
    fun createShortenedUrl(@RequestBody @Valid body: CreateShortenedDto, @RequestHeader host: String): CreateShortenedResponseDto {
        val shortened = services.newShortenedUrl(body.shortName, body.url)
        return CreateShortenedResponseDto("$host/${shortened.id}", shortened.id, shortened.target)
    }
}