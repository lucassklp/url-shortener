package com.tds.shortener.url.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(value= HttpStatus.BAD_REQUEST, reason="Short name already taken. Use a new one and try again")
class ShortenedAlreadyTakenException : Exception() {

}