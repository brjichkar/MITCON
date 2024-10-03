package com.android.mitcontaskmanagement.data.model_class_api
data class LoginResponse(
    val success: Boolean,
    val data: Data,
    val message: String,
)

data class Data(
    val phone: String,
    val name: String,
    val role: String,
)
