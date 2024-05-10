package com.android.mitcontaskmanagement.data.remote

import android.content.Intent
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.android.mitcontaskmanagement.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FirebaseAuthDataSource @Inject constructor(private val auth: FirebaseAuth) {

    fun logoutUser() = auth.signOut()

    fun isUserLoggedIn() = auth.currentUser != null

    suspend fun loginUser(email: String, password: String): Resource<Unit> =
        withContext(Dispatchers.IO) {
            return@withContext try {
                auth.signInWithEmailAndPassword(email, password).await()
                Resource.Success(message = "User signed in successfully")
            } catch (e: Exception) {
                Resource.Error(message = e.message.toString())
            }
        }

}
