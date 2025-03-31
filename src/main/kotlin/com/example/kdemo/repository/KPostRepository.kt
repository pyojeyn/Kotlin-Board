package com.example.kdemo.repository

import com.example.kdemo.entity.KPost
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

// 코틀린에서는 extends 대신 : 를 사용하여 상속을 표현.
// 구현을 하지 않으면 {} 를 생략할 수 있다.
@Repository
interface KPostRepository : JpaRepository<KPost, Long>