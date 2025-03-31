package com.example.kdemo.controller

import com.example.kdemo.dto.BaseResponse
import com.example.kdemo.dto.kPost.CreateKPostRequestDto
import com.example.kdemo.dto.kPost.PatchKPostRequestDto
import com.example.kdemo.service.KPostService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.persistence.EntityNotFoundException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import kotlin.Exception


@RestController
@RequestMapping("/api/posts")
@Tag(name = "게시판", description = "코틀린 CRUD")
class KPostController(private val kPostService: KPostService){

    @Operation(summary = "전체 조회", description = "전체 글 보기")
    @GetMapping
    fun getAllPosts(): ResponseEntity<Any> {
        return  try{
            val kPosts = kPostService.getAllPosts()
            ResponseEntity.ok(kPosts)
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

    @Operation(summary = "게시판 디테일", description = "게시판 보기")
    @GetMapping("/{id}")
    fun getPostById(@PathVariable id: Long): ResponseEntity<Any>? {
        return try{
            val post = kPostService.getPostById(id)
            ResponseEntity.ok(post)
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

    @Operation(summary = "게시판 글 등록", description = "등록")
    @PostMapping
    fun createPost(@RequestBody post: CreateKPostRequestDto): ResponseEntity<Any> {
        return try{
            val post = kPostService.createPost(post)
            ResponseEntity.ok(post)
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

    @Operation(summary = "게시판 수정", description = "부분 수정")
    @PatchMapping("/{id}")
    fun updatePost(@PathVariable id: Long, @RequestBody post: PatchKPostRequestDto): ResponseEntity<Any>? {
        return try {
            val post = kPostService.updatePost(id, post)
            ResponseEntity.ok(post)
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(e.message?.let {
                    BaseResponse(
                        code = 500,
                        message = it
                    )
                })
        }
    }



    @Operation(summary = "게시판 삭제", description = "삭제")
    @DeleteMapping("/{id}")
    fun deletePost(@PathVariable id: Long): ResponseEntity<String> {
        return try{
            kPostService.deletePost(id)
            ResponseEntity.ok("Post with ID $id has been deleted.")
        }catch (e: EntityNotFoundException){
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Post with ID $id not found.")
        }
    }
//    Kotlin 에서는 함수 본문이 한 줄로 끝날 경우, = 를 사용해서 간단하게 표현


}