package com.example.kdemo.service

import com.example.kdemo.dto.foods.CreateFoodsResponseDto
import com.example.kdemo.dto.foods.SearchFoodsResponseDto
import org.springframework.web.bind.annotation.RequestParam

interface FoodsService {
    fun createFood(): CreateFoodsResponseDto?

    fun searchFoodByDB(requestParam: String): CreateFoodsResponseDto

    fun searchFoodByRedis(requestParam: String): SearchFoodsResponseDto
}