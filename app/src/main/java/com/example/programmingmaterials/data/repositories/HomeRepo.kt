package com.example.programmingmaterials.data.repositories

import com.example.programmingmaterials.RetrofitClient
import com.example.programmingmaterials.model.Author
import javax.inject.Inject

class HomeRepo @Inject constructor() {

    suspend fun getAuthor(authorId: Int): Author {
        val author = RetrofitClient.apiService.getAuthor(authorId)
        return author
    }

    suspend fun getCreatedMaterials(authorId: Int): Int {
        val createdMaterials = RetrofitClient.apiService.getNumberOfCreatedMaterials(authorId)
        return createdMaterials
    }
}