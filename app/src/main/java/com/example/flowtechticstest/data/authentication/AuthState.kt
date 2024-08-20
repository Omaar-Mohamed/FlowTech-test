package com.example.flowtechticstest.data.authentication

import com.google.firebase.auth.FirebaseUser

sealed class AuthState {
    object Loading : AuthState()
    data class Success(val user: FirebaseUser?) : AuthState()
    data class Error(val exception: Exception?) : AuthState()
    object SignedOut : AuthState()
}
