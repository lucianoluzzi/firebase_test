package com.example.firebase_test.data

import android.util.Log
import com.google.firebase.auth.FirebaseAuth

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

    override fun signIn(password: String, email: String) {
        authenticator.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("SignIn", "signInWithEmail:success")
                    val user = authenticator.currentUser
                    // updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("SignIn", "signInWithEmail:failure", task.exception)
                    // updateUI(null)
                }
            }
    }

    override fun register(userName: String, password: String, email: String) {
        authenticator.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("Register", "createUserWithEmail:success")
                    val user = authenticator.currentUser
                    // updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("Register", "createUserWithEmail:failure", task.exception)
                    // updateUI(null)
                }
            }
    }
}