package com.example.kdemo.service

import com.example.kdemo.dto.foods.CreateFoodsResponseDto

interface FoodsService {
    fun createFood(): CreateFoodsResponseDto?
}