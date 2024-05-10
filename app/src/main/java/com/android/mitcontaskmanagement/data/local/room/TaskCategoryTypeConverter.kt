package com.android.mitcontaskmanagement.data.local.room

import androidx.room.TypeConverter
import com.android.mitcontaskmanagement.util.TaskState
import com.android.mitcontaskmanagement.util.TaskType

class TaskCategoryTypeConverter {

    @TypeConverter
    fun fromTaskType(taskType: TaskType?): String? {
        return taskType?.name
    }

    @TypeConverter
    fun toTaskType(task: String?): TaskType? {
        return task?.let { TaskType.valueOf(it) }
    }
}

class TaskStateTypeConverter {

    @TypeConverter
    fun fromTaskState(state: TaskState?): String? {
        return state?.name
    }

    @TypeConverter
    fun toTaskState(state: String?): TaskState? {
        return state?.let { TaskState.valueOf(it) }
    }
}
