package com.tds.shortener.url

import com.fasterxml.jackson.databind.JsonNode
import com.tds.shortener.url.controllers.ShortenedController
import com.tds.shortener.url.dtos.CreateShortenedDto
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.RequestEntity
import org.springframework.http.ResponseEntity

@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
class ShortenerUrlApplicationTests @Autowired constructor(private val controller: ShortenedController,
														  private val restTemplate: TestRestTemplate,
														  @LocalServerPort val randomPort: Int) {

	@Test
	fun contextLoads() {
		assertThat(controller).isNotNull
	}

	@Test
	fun shouldReturnErrorOnInvalidUrl() {
		val response = registerNewShortenedUrl(CreateShortenedDto("teste", "invalid url"))
		val json = response.body!!

		assertThat(response.statusCode).isEqualTo(HttpStatus.BAD_REQUEST)
		assertThat((json.get("url").textValue())).isNotEmpty
	}

	@Test
	fun shouldRegisterNewShortenedUrl() {
		val url = "http://google.com"
		val response = registerNewShortenedUrl(CreateShortenedDto("test", url))
		val json = response.body!!

		assertThat(json.get("redirectsTo")?.textValue())
				.isEqualTo(url)
	}

	@Test
	fun testAccessIncrement(){
		val shortName = "test_increment"
		registerNewShortenedUrl(CreateShortenedDto(shortName, "http://google.com"))

		accessShortenedUrl(shortName)
		val response = getStatistics(shortName)
		val json = response.body!!

		assertThat(json.get("count").intValue()).isEqualTo(1)

		accessShortenedUrl(shortName)
		val response2 = getStatistics(shortName)
		val json2 = response2.body!!

		assertThat(json2.get("count").intValue()).isEqualTo(2)
	}

	@Test
	fun testRedirection() {
		val shortName = "test_redirection"
		val url = "http://google.com"
		registerNewShortenedUrl(CreateShortenedDto(shortName, url))

		val response = accessShortenedUrl(shortName)
		assertThat(response.statusCode).isEqualTo(HttpStatus.OK)
	}


	fun getStatistics(shortName: String): ResponseEntity<JsonNode?> {
		val request = RequestEntity.get("http://localhost:$randomPort/shortened/$shortName/statistics")
				.build()
		return restTemplate.exchange(request, JsonNode::class.java)
	}

	fun accessShortenedUrl(shortName: String): ResponseEntity<String?> {

		val request = RequestEntity.get("http://localhost:$randomPort/$shortName")
				.build()
		return restTemplate.exchange(request, String::class.java)
	}

	fun registerNewShortenedUrl(dto: CreateShortenedDto): ResponseEntity<JsonNode> {
		val request = RequestEntity.post("http://localhost:$randomPort/shortened/")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.body(dto)

		return restTemplate.exchange(request, JsonNode::class.java)
	}
}
