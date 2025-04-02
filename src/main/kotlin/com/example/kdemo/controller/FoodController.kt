package com.example.kdemo.controller

import com.example.kdemo.dto.BaseResponse
import com.example.kdemo.service.FoodsService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/foods")
@Tag(name = "Food")
class FoodController(private val foodsService: FoodsService) {

    @Operation(summary = "음식 적재")
    @GetMapping
    fun getFoods(): ResponseEntity<BaseResponse> {
        return try{
            foodsService.createFood()
            ResponseEntity.ok(BaseResponse(
                code = 200,
                message = "Success"
            ))
        }catch (e: Exception){
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(e.message?.let {
                    BaseResponse(
                        code = 500,
                        message = it
                    )
                })
        }
    }

}