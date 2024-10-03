package com.android.mitcontaskmanagement.data.repo

import android.content.Intent
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.android.mitcontaskmanagement.data.local.dataSource.PreferencesDataSource
import com.android.mitcontaskmanagement.data.models.mappper.UserMapper
import com.android.mitcontaskmanagement.data.models.remote.UserDTO
import com.android.mitcontaskmanagement.data.remote.FirebaseAuthDataSource
import com.android.mitcontaskmanagement.data.remote.dataSource.HarperDbAuthDataSource
import com.android.mitcontaskmanagement.util.ErrorTYpe
import com.android.mitcontaskmanagement.util.NetworkUtils
import com.android.mitcontaskmanagement.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Named

class AuthRepo @Inject constructor(
    private val authDataSource: FirebaseAuthDataSource,
    private val harperDbAuthDataSource: HarperDbAuthDataSource,
    @Named("sharedPref") private val preferencesDataSource: PreferencesDataSource,
    private val userMapper: UserMapper,
    private val networkUtils: NetworkUtils
) {

    fun getUserData() = preferencesDataSource.getUserData()

    fun isUserLoggedIn() =
        authDataSource.isUserLoggedIn() && preferencesDataSource.getUserData() != null

    suspend fun loginUser(phone: String): Resource<Unit> =
        withContext(Dispatchers.IO) {
            if (!networkUtils.checkInternetConnection())
                return@withContext Resource.Error(
                    message = "No internet",
                    errorType = ErrorTYpe.NO_INTERNET
                )
            val resource = authDataSource.loginUser(phone)
            return@withContext getUserDataAfterLogin(resource, phone)
        }


    suspend fun logoutUser() {
        authDataSource.logoutUser()
        preferencesDataSource.removeUserData()
    }



    private fun getUserDTOFromFirebaseUser(firebaseUser: FirebaseUser) = UserDTO(
        email = firebaseUser.email.toString(),
        username = firebaseUser.displayName.toString(),
        profile_img = firebaseUser.photoUrl.toString()
    )

    private suspend fun <T> getUserDataAfterLogin(
        loginResponse: Resource<T>,
        phone: String
    ): Resource<Unit> {
//        val harperResource = harperDbAuthDataSource.getUserData(phone)
//        if (harperResource is Resource.Error || authResource is Resource.Error) {
//            if (harperResource is Resource.Error && authResource is Resource.Success) {
//                if (harperResource.message != "User does not exist") logoutUser()
//                return Resource.Error(message = harperResource.message)
//            }
//            return Resource.Error(message = authResource.message, errorType = ErrorTYpe.UNKNOWN)
//        }

        // do not delete until user login is stored
        //preferencesDataSource.saveUserData(userMapper.toDomainModel(harperResource.data!!))
        if (loginResponse is Resource.Error) {
            return Resource.Error(message = loginResponse.message, errorType = ErrorTYpe.UNKNOWN)
        }
        return Resource.Success(message = "User logged in successfully")
    }


}
