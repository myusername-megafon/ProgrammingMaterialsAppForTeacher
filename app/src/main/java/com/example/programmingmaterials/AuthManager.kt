package com.example.programmingmaterials

import android.content.Context
import android.provider.Settings.Global.putInt
import android.util.Log
import androidx.core.content.edit
import com.google.gson.annotations.SerializedName
import kotlinx.coroutines.delay
import retrofit2.Retrofit
import javax.inject.Inject

class AuthManager @Inject constructor(
    private val context: Context
) {
    private val prefs = context.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)

    fun isLoggedIn(): Boolean {
        return prefs.getInt("user_id", -1) != -1
    }

    suspend fun logIn(email: String, password: String): Boolean {
        return try {
            val response = RetrofitClient.apiService.login(LoginRequest(email, password))
            if (response.isSuccessful && response.body()?.success == true) {
                prefs.edit {
                    putInt("user_id", response.body()?.userId ?: -1)
                }
                true
            } else {
                false
            }
        } catch (e: Exception) {
            false
        }
    }

    fun getUserId(): Int {
        return prefs.getInt("user_id", -1)
    }

    fun logout() {
        prefs.edit {
            remove("user_id")
        }

    }

    suspend fun register(
        fullName: String,
        email: String,
        password: String
    ): Boolean {
        return try {
            val response =
                RetrofitClient.apiService.register(RegisterRequest(fullName, email, password))
            Log.e("registerLog", response.toString())
            if (response.isSuccessful && response.body()?.success == true) {
                true
            } else {
                false
            }
        } catch (e: Exception) {
            false
        }
    }
}

data class LoginRequest(
    @SerializedName("email") val email: String,
    @SerializedName("password") val password: String
)

data class LoginResponse(
    @SerializedName("success") val success: Boolean,
    @SerializedName("userId") val userId: Int,
    @SerializedName("error") val error: String?
)

data class RegisterRequest(
    @SerializedName("fullName") val fullName: String,
    @SerializedName("email") val email: String,
    @SerializedName("password") val password: String
)

data class RegisterResponse(
    @SerializedName("success") val success: Boolean,
    @SerializedName("message") val message: String?,
    @SerializedName("user_id") val userId: Int?
)
