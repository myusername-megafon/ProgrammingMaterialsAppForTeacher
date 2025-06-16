package com.example.programmingmaterials.model

import com.google.gson.annotations.SerializedName

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

data class Author(
    @SerializedName("iD_Author") val id: Int,
    @SerializedName("name_Author") val name: String,
    @SerializedName("email_Author") val email: String,
    @SerializedName("password_Author") val password: String
)