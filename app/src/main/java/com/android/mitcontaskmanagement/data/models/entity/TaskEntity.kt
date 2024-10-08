package com.android.mitcontaskmanagement.data.models.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.android.mitcontaskmanagement.util.TaskState
import com.android.mitcontaskmanagement.util.TaskType
import java.io.Serializable

@Entity(tableName = "task_table")
data class
TaskEntity(
    val email: String,
    val task_title: String,
    val task_description: String,
    val task_category: TaskType,
    val duration: Long,
    var state: TaskState = TaskState.NOT_STARTED,
    var timeLeft: Long = duration,
    val created_time: Long = System.currentTimeMillis(),
    @PrimaryKey
    val task_id: String = "$email.$created_time"
) : Serializable
