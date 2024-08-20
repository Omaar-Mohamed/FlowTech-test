package com.example.flowtechticstest.data.authentication

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.tasks.await

object AuthRepositoryImpl : AuthRepo {
    private val firebaseAuth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }

    override suspend fun signInWithEmail(email: String, password: String): FirebaseUser? {
        return try {
            val authResult = FirebaseAuth.getInstance()
                .signInWithEmailAndPassword(email, password)
                .await() // Extension function to convert Task to Deferred for coroutines
            authResult.user
        } catch (e: FirebaseAuthException) {
            throw Exception("Authentication failed: ${e.message}")
        }
    }


    override suspend fun signOut() {
        firebaseAuth.signOut()
    }

    override suspend fun register(
        userName: String,
        age: Int,
        email: String,
        password: String
    ): FirebaseUser? {
        return try {
            val result = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            result.user
        } catch (e: Exception) {
            null
        }
    }
}
