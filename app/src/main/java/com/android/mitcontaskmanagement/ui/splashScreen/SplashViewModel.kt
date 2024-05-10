package com.android.mitcontaskmanagement.ui.splashScreen

import androidx.lifecycle.ViewModel
import com.android.mitcontaskmanagement.data.repo.AuthRepo
import com.android.mitcontaskmanagement.data.repo.PreferencesRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val authRepo: AuthRepo,
    private val preferencesRepo: PreferencesRepo
) : ViewModel() {

    fun isOnBoardingComplete() = preferencesRepo.isOnBoardingComplete()

    fun isUserLogged() = authRepo.isUserLoggedIn()
}
