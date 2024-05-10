package com.android.mitcontaskmanagement.ui.mainScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.mitcontaskmanagement.data.models.entity.TaskEntity
import com.android.mitcontaskmanagement.data.repo.AuthRepo
import com.android.mitcontaskmanagement.data.repo.PreferencesRepo
import com.android.mitcontaskmanagement.data.repo.TaskRepo
import com.android.mitcontaskmanagement.util.Resource
import com.android.mitcontaskmanagement.util.StopWatchFor
import com.android.mitcontaskmanagement.util.TopLevelScreens
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val authRepo: AuthRepo,
    private val taskRepo: TaskRepo,
    private val preferencesRepo: PreferencesRepo
) : ViewModel() {

    val user = authRepo.getUserData()

    private val _taskFetchState = MutableStateFlow<Resource<Unit>>(Resource.Empty())
    val taskFetchState: StateFlow<Resource<Unit>> = _taskFetchState

    private val _currentFragment = MutableStateFlow(TopLevelScreens.HOME)

    private val _runningTask = taskRepo.getRunningTask()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())
    val runningTask: StateFlow<List<TaskEntity>> = _runningTask

    fun isServiceRunning() = preferencesRepo.isServiceRunning()

    var stopWatchFor: StopWatchFor? = null
    var task: TaskEntity? = null

    private val _logout = MutableStateFlow(false)
    val shouldLogout: StateFlow<Boolean> = _logout

    fun fetchAllTasks() = viewModelScope.launch {
        Timber.d("fetching all tasks")
        _taskFetchState.emit(Resource.Loading())
        user?.let {
            val resource = taskRepo.fetchAllTasks(it.email)
            _taskFetchState.emit(resource)
        } ?: _taskFetchState.emit(Resource.Error(message = "Failed to get user information"))
    }

    fun saveServiceStarted() = saveServiceState(true)

    fun saveServiceStopped() = saveServiceState(false)

    private fun saveServiceState(running: Boolean) = viewModelScope.launch {
        preferencesRepo.setServiceRunning(running)
    }

    fun onLogoutPressed() = viewModelScope.launch {
        authRepo.logoutUser()
        _logout.emit(true)
    }
}
