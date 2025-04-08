package com.example.kdemo.repository

import com.example.kdemo.entity.Foods
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.math.BigInteger

@Repository
interface FoodsRepository : JpaRepository<Foods, BigInteger>{
    fun findAllByFoodNmContaining(keyword: String): List<Foods>
}