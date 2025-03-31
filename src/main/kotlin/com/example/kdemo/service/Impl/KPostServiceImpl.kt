package com.example.kdemo.service.Impl

import com.example.kdemo.dto.kPost.*
import com.example.kdemo.entity.KPost
import com.example.kdemo.repository.KPostRepository
import com.example.kdemo.service.KPostService
import jakarta.persistence.EntityNotFoundException
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
@Transactional
class KPostServiceImpl(private val kPostRepository: KPostRepository) : KPostService {
                    // ↑ 생성자 주입                                    ↑ implements(지정)

/*
    필드 주입 -> 권장하지 않음 X
    @Autowired
    private lateinit var kPostRepository: KPostRepository
*/

    override fun getAllPosts(): KPostListResponseDto {
        val allPost: List<KPost> = kPostRepository.findAll()

        val kpList = allPost.map {
            KPostWrapper(
                id = it.id,
                title = it.title,
                content = it.content
            )
        }

        return KPostListResponseDto(kPostList = kpList)
    }

    override fun getPostById(id: Long): KPostResponseDto = kPostRepository.findById(id)
        .orElseThrow { EntityNotFoundException("Post with ID $id not found") }
        .let {
            KPostResponseDto(
                kPost = KPostWrapper(
                    id = it.id,
                    title = it.title,
                    content = it.content
                )
            )
        }

    override fun createPost(reqBody: CreateKPostRequestDto): KPostResponseDto {
        val post = KPost(
            title = reqBody.title,
            content = reqBody.content
        )

        val savedPost = kPostRepository.save(post)
            .let {
                KPostResponseDto(
                    kPost = KPostWrapper(
                        id = it.id,
                        title = it.title,
                        content = it.content
                    )
                )
            }

    return savedPost
}

    override fun updatePost(id: Long, reqBody: PatchKPostRequestDto): KPostResponseDto? {
        val findExistingPostById = kPostRepository.findById(id).orElseThrow{
            throw EntityNotFoundException("Post with ID $id not found")
        }

        // 수정된 필드만 업데이트 (title 과 content 가 null 인 경우 기존 값을 그대로 사용)
        val updatedPost: KPost = findExistingPostById.copy(
            title = reqBody.title ?: findExistingPostById.title,
            content = reqBody.content ?: findExistingPostById.content
        )

        val savedPost = kPostRepository.save(updatedPost)

        return KPostResponseDto(
            kPost = KPostWrapper(
                id = savedPost.id,
                title = savedPost.title,
                content = savedPost.content
            )
        )
    }

    override fun deletePost(id: Long) {
        val post = kPostRepository.findById(id).orElseThrow{
            EntityNotFoundException("Post with ID $id not found")
        }
        kPostRepository.delete(post)
    }
}