package com.android.mitcontaskmanagement.data.models

import androidx.room.ColumnInfo
import com.android.mitcontaskmanagement.util.TaskType

data class TaskCount(
    @ColumnInfo(name = "task_category")
    val taskType: TaskType,
    val count: Int
)
