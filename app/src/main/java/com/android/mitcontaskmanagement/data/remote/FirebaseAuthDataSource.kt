package com.android.mitcontaskmanagement.data.remote

import android.content.Intent
import com.android.mitcontaskmanagement.data.model_class_api.LoginRequest
import com.android.mitcontaskmanagement.data.models.remote.TaskDTO
import com.android.mitcontaskmanagement.data.remote.harperDb.Api
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.android.mitcontaskmanagement.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import org.json.JSONObject
import javax.inject.Inject

class FirebaseAuthDataSource @Inject constructor(private val auth: FirebaseAuth, private val api: Api) {

    fun logoutUser() = auth.signOut()

    fun isUserLoggedIn() = auth.currentUser != null

    suspend fun loginUser(phone: String): Resource<Any> = try {
        val response = api.checkPhone(LoginRequest(phone))
        if (response.isSuccessful)
            Resource.Success(response.body(), response.body()?.message!!)
        else
            Resource.Error(parseErrorMessage(response.errorBody()?.string()))
    } catch (e: Exception) {
        Resource.Error(e.message.toString())
    }

    fun parseErrorMessage(responseBody: String?): String {
        return try {
            val json = JSONObject(responseBody)
            val message = json.getString("message")
            message
        } catch (e: Exception) {
            "General Error" // Return a default message if parsing fails
        }
    }

//    suspend fun loginUser(email: String, password: String): Resource<Unit> =
//        withContext(Dispatchers.IO) {
//            return@withContext try {
//                auth.signInWithEmailAndPassword(email, password).await()
//                Resource.Success(message = "User signed in successfully")
//            } catch (e: Exception) {
//                Resource.Error(message = e.message.toString())
//            }
//        }

}
