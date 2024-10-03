package com.android.mitcontaskmanagement.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.android.mitcontaskmanagement.data.models.entity.TaskEntity

@Database(entities = [TaskEntity::class], version = 2, exportSchema = false)
@TypeConverters(TaskCategoryTypeConverter::class, TaskStateTypeConverter::class)
abstract class MitconDatabase : RoomDatabase() {

    abstract fun getTaskDao(): TaskDAO
}
