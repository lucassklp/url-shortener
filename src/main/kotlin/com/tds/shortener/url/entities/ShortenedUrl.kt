package com.tds.shortener.url.entities

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class ShortenedUrl(
    @Id
    val id: String,

    @Column
    val target: String,

    @Column
    var count: Int
)