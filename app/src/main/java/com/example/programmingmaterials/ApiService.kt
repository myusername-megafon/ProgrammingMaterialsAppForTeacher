package com.example.programmingmaterials

import com.example.programmingmaterials.data.DTOClasses.CreateMaterialRequest
import com.example.programmingmaterials.data.DTOClasses.CreateMaterialResponse
import com.example.programmingmaterials.model.Category
import com.example.programmingmaterials.data.DTOClasses.FeedbackDTO
import com.example.programmingmaterials.data.DTOClasses.ProgressRequestDTO
import com.example.programmingmaterials.data.DTOClasses.MaterialDTO
import com.example.programmingmaterials.data.DTOClasses.ProgressDTO
import com.example.programmingmaterials.model.Author
import com.example.programmingmaterials.model.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService {

    //MaterialController

    @GET("api/Material/new/{userId}")
    suspend fun getNewMaterials(@Path("userId") userId: Int): List<MaterialDTO>

    @GET("api/Material/User/{userId}")
    suspend fun getAllMaterials(@Path("userId") userId: Int): List<MaterialDTO>

    @GET("api/Material/{userId}/{materialId}")
    suspend fun getMaterialById(
        @Path("userId") userId: Int,
        @Path("materialId") materialId: Int
    ): MaterialDTO

    @POST("api/Material/create")
    suspend fun createMaterial(@Body request: CreateMaterialRequest): Response<Unit>

    //ProgressController

    @GET("api/Progress/getUserProgress/{userEmail}")
    suspend fun getProgress(@Path("userEmail") userEmail: String): ProgressDTO

    //FeedbackController

    @GET("api/Feedback/{materialId}")
    suspend fun getMaterialFeedbacks(@Path("materialId") materialId: Int): List<FeedbackDTO>

    //CategoryController

    @GET("api/Category")
    suspend fun getAllCategories(): List<Category>

    //AuthorController

    @POST("api/Author/auth/login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>

    @GET("api/Author/{authorId}")
    suspend fun getAuthor(@Path("authorId") authorId: Int): Author

    @GET("api/Author/CreatedMaterials/{authorId}")
    suspend fun getNumberOfCreatedMaterials(@Path("authorId") authorId: Int): Int

}