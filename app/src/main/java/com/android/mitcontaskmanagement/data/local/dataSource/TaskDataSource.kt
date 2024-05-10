package com.android.mitcontaskmanagement.data.local.dataSource

import com.android.mitcontaskmanagement.data.local.room.TaskDAO
import com.android.mitcontaskmanagement.data.models.entity.TaskEntity
import javax.inject.Inject

class TaskDataSource @Inject constructor(private val taskDAO: TaskDAO) {

    fun getTaskAfterGivenTime(time: Long) = taskDAO.getTaskAfterGivenTime(time)

    fun getAllTasksOfToday(state: String, todaysTime: Long) =
        taskDAO.getAllTasksOfToday(state, todaysTime)

    fun getTaskCounts() = taskDAO.getTasksByCount()

    fun getTaskStates() = taskDAO.getTaskStates()

//    fun getTotalTaskCount() = taskDAO.getTotalTaskCount()
//
//    fun getCompletedTaskCount() = taskDAO.getCompletedTaskCount()

    suspend fun insertTask(taskEntity: List<TaskEntity>) = taskDAO.insertTask(taskEntity)

    suspend fun updateTask(taskEntity: TaskEntity) = taskDAO.updateTask(taskEntity)

    suspend fun deleteTask(taskEntity: TaskEntity) = taskDAO.deleteTask(taskEntity)

    suspend fun deleteAllTasks() = taskDAO.deleteAllTasks()
}
