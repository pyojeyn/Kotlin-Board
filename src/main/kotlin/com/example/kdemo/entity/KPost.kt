package com.example.kdemo.entity

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity
data class KPost(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    val title: String,
    val content: String

    // title과 content는 반드시 값을 제공해야 하기 때문에 null이 아닌 값을 할당
)
