package com.lucianoluzzi.firebase_test.data

import android.util.Log
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.userProfileChangeRequest
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class UserRepositoryImpl(
    private val authenticator: FirebaseAuth
) : UserRepository {

    override fun getUser(): User? {
        authenticator.currentUser?.let {
            val name = it.displayName
            val email = it.email

            if (name == null || email == null)
                return null

            return User(
                name = name,
                email = email
            )
        }

        return null
    }

    override suspend fun signIn(password: String, email: String): User? {
        return suspendCoroutine { continuation ->
            authenticator.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d("SignIn", "signInWithEmail:success")

                        val user = authenticator.currentUser
                        user?.let {
                            val name = it.displayName
                            val email = it.email

                            if (name != null && email != null) {
                                continuation.resume(
                                    User(
                                        name = name,
                                        email = email
                                    )
                                )
                            }
                        }
                        // updateUI(user)
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w("SignIn", "signInWithEmail:failure", task.exception)
                        // updateUI(null)
                    }
                }
        }
    }

    override fun signIn(authCredential: AuthCredential, displayName: String?) {
        authenticator.signInWithCredential(authCredential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("LoginFragment", "signInWithCredential:success")
                    val user = authenticator.currentUser
                    user?.let {
                        if (it.displayName.isNullOrBlank() && displayName != null) {
                            updateUserName(it, displayName)
                        }
                    }
                    // updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("LoginFragment", "signInWithCredential:failure", task.exception)
                    // updateUI(null)
                }
            }
    }

    private fun updateUserName(user: FirebaseUser, displayName: String) {
        val userProfileChangeRequest = userProfileChangeRequest {
            this.displayName = displayName
        }
        user.updateProfile(userProfileChangeRequest).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.d("UpdateUser", "success")
            } else {
                Log.w("UpdateUser", "updateProfile:failure", task.exception)
            }
        }
    }

    override fun register(userName: String, password: String, email: String) {
        authenticator.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("Register", "createUserWithEmail:success")
                    authenticator.currentUser?.let {
                        updateUserName(it, userName)
                    }
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("Register", "createUserWithEmail:failure", task.exception)
                    // updateUI(null)
                }
            }
    }
}