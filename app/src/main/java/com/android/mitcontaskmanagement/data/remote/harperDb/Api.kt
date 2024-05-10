package com.android.mitcontaskmanagement.data.remote.harperDb

import com.android.mitcontaskmanagement.data.models.remote.SQLModel
import com.android.mitcontaskmanagement.data.models.remote.TaskDTO
import com.android.mitcontaskmanagement.data.models.remote.UserDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface Api {

    @POST("/")
    suspend fun saveUserInDb(
        @Body body: SQLModel
    ): Response<Any>

    @POST("/")
    suspend fun getUserInfo(
        @Body sqlModel: SQLModel
    ): Response<List<UserDTO>>

    // tasks
    @POST("/")
    suspend fun insertTask(
        @Body task: SQLModel
    ): Response<Any>

    @POST("/")
    suspend fun getAllTasksOfUser(
        @Body task: SQLModel
    ): Response<List<TaskDTO>>

    @POST("/")
    suspend fun updateTask(
        @Body task: SQLModel
    ): Response<Any>

    @POST("/")
    suspend fun deleteTask(
        @Body task: SQLModel
    ): Response<Any>
}
