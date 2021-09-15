package com.tds.shortener.url

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ShortenerUrlApplication

fun main(args: Array<String>) {
	runApplication<ShortenerUrlApplication>(*args)
}
