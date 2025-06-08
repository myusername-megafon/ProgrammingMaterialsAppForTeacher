package com.example.programmingmaterials.model

import com.google.gson.annotations.SerializedName

data class Material(
    @SerializedName("iD_Material") val id: Int,
    @SerializedName("name_Material") val name: String,
    @SerializedName("iD_Type") val type: Int,
    @SerializedName("content_Material") val content: String,
    @SerializedName("iD_Author") val author: Int,
    @SerializedName("iD_Category") val category: Int
)

data class Category(
    @SerializedName("iD_Category")val id: Int,
    @SerializedName("name_Category")val name: String,
    @SerializedName("difficulty")val difficulty: Int,
    @SerializedName("description")val description: String
)

data class User(
    @SerializedName("iD_User") val id: Int,
    @SerializedName("name_User") val name: String,
    @SerializedName("email_User") val email: String,
    @SerializedName("password_User") val password: String
)