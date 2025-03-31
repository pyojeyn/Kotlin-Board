package com.example.kdemo.service

import com.example.kdemo.dto.kPost.CreateKPostRequestDto
import com.example.kdemo.dto.kPost.KPostListResponseDto
import com.example.kdemo.dto.kPost.KPostResponseDto
import com.example.kdemo.dto.kPost.PatchKPostRequestDto

interface KPostService {

    fun getAllPosts(): KPostListResponseDto

    fun getPostById(id: Long): KPostResponseDto?

    fun createPost(reqBody: CreateKPostRequestDto): KPostResponseDto

    fun updatePost(id: Long, reqBody: PatchKPostRequestDto): KPostResponseDto?

    fun deletePost(id: Long)

}