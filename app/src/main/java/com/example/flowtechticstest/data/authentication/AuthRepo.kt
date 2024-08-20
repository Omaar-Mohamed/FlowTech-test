package com.example.flowtechticstest.data.authentication

import com.google.firebase.auth.FirebaseUser

interface AuthRepo {
    suspend fun signInWithEmail(email: String, password: String): FirebaseUser?
    suspend fun signOut()
    suspend fun register(userName: String , age: Int , email: String, password: String): FirebaseUser?
}