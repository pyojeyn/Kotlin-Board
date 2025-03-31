package com.example.kdemo.dto.kPost

import com.example.kdemo.dto.BaseResponse

data class KPostResponseDto(
    override val message: String = "Success",
    override val code: Int = 200,

    val kPost: KPostWrapper

): BaseResponse(message, code)


data class KPostListResponseDto(
    override val code: Int = 200,
    override val message: String = "Success",

    val kPostList: List<KPostWrapper>
): BaseResponse(message, code)

data class KPostWrapper(
    val id: Long? = null,
    val title: String,
    val content: String
)

