package com.android.mitcontaskmanagement.data.repo

import com.android.mitcontaskmanagement.data.local.dataSource.PreferencesDataSource
import javax.inject.Inject
import javax.inject.Named

class PreferencesRepo @Inject constructor(
    @Named("sharedPref") private val preferencesDataSource: PreferencesDataSource
) {

    fun isServiceRunning() = preferencesDataSource.isServiceRunning()

    fun isOnBoardingComplete() = preferencesDataSource.isOnBoardingComplete()

    suspend fun setServiceRunning(running: Boolean) =
        preferencesDataSource.setServiceRunning(running)

    suspend fun setOnBoardingComplete() = preferencesDataSource.setOnBoardingComplete()
}
