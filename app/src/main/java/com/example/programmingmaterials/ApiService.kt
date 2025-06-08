package com.example.programmingmaterials

import com.example.programmingmaterials.data.DTOClasses.AddFeedbackRequest
import com.example.programmingmaterials.model.Category
import com.example.programmingmaterials.data.DTOClasses.FeedbackDTO
import com.example.programmingmaterials.data.DTOClasses.ProgressRequestDTO
import com.example.programmingmaterials.data.DTOClasses.MaterialDTO
import com.example.programmingmaterials.data.DTOClasses.ProgressDTO
import com.example.programmingmaterials.data.DTOClasses.SaveResultDTO
import com.example.programmingmaterials.data.DTOClasses.TestDTO
import com.example.programmingmaterials.data.DTOClasses.UpdateProgressRequestDTO
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

    @GET("api/Material/in-progress/{userId}")
    suspend fun getStartedMaterials(@Path("userId") userId: Int): List<MaterialDTO>

    @GET("api/Material/User/{userId}")
    suspend fun getAllMaterials(@Path("userId") userId: Int): List<MaterialDTO>

    @GET("api/Material/{userId}/{materialId}")
    suspend fun getMaterialById(
        @Path("userId") userId: Int,
        @Path("materialId") materialId: Int
    ): MaterialDTO

    //ProgressController

    @POST("api/Progress/create-progress")
    suspend fun createProgressEntry(
        @Body request: ProgressRequestDTO
    ): Response<Unit>

    @PUT("api/Progress/update-status")
    suspend fun updateProgressStatus(
        @Body request: UpdateProgressRequestDTO
    ): Response<Unit>

    //FeedbackController

    @GET("api/Feedback/{materialId}")
    suspend fun getMaterialFeedbacks(@Path("materialId") materialId: Int): List<FeedbackDTO>

    @POST("api/Feedback/add-feedback")
    suspend fun createMaterialFeedback(@Body request: AddFeedbackRequest): Response<Unit>

    @GET("api/Feedback/User/{userId}")
    suspend fun getUserFeedbacks(@Path("userId") userId: Int): List<FeedbackDTO>

    @DELETE("api/Feedback/delete/{feedbackId}")
    suspend fun deleteFeedback(@Path("feedbackId") feedbackId: Int): Response<Unit>

    //UserController

    @GET("api/User/{userId}")
    suspend fun getUser(@Path("userId") userId: Int): User

    @GET("api/User/progressInNumbers/{userId}")
    suspend fun getUserProgressInNumbers(@Path("userId") userId: Int): ProgressDTO

    @POST("api/User/auth/login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>

    @POST("api/User/register")
    suspend fun register(@Body request: RegisterRequest): Response<RegisterResponse>

    //TestController

    @GET("api/Test/{userId}/{categoryName}")
    suspend fun getTest(@Path("userId") userId: Int, @Path("categoryName") categoryName: String): TestDTO

    @POST("api/Test/SaveResult")
    suspend fun saveResult(@Body request: SaveResultDTO): Response<Unit>

    //CategoryController

    @GET("api/Category")
    suspend fun getAllCategories(): List<Category>

}