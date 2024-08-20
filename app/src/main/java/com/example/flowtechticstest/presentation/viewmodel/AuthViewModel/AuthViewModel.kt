package com.example.flowtechticstest.presentation.viewmodel.AuthViewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flowtechticstest.data.authentication.AuthRepo
import com.example.flowtechticstest.data.authentication.AuthState
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthViewModel(
    private val authRepo: AuthRepo
) : ViewModel() {

    private val _loginState = MutableStateFlow<AuthState>(AuthState.defult)
    val loginState: StateFlow<AuthState> get() = _loginState

    private val _logoutState = MutableStateFlow<AuthState>(AuthState.defult)
    val logoutState: StateFlow<AuthState> get() = _logoutState

    private val _registerState = MutableStateFlow<AuthState>(AuthState.defult)
    val registerState: StateFlow<AuthState> get() = _registerState

    fun signIn(email: String, password: String) {
        viewModelScope.launch {
            _loginState.value = AuthState.Loading
            try {
                val user = authRepo.signInWithEmail(email, password)
                _loginState.value = AuthState.Success(user)
            } catch (e: Exception) {
                _loginState.value = AuthState.Error(e)
            }
        }
    }

    fun signOut() {
        viewModelScope.launch {
            _logoutState.value = AuthState.Loading
            try {
                authRepo.signOut()
                _logoutState.value = AuthState.SignedOut
            } catch (e: Exception) {
                _logoutState.value = AuthState.Error(e)
            }
        }
    }

    fun register(userName: String, age: Int, email: String, password: String) {
        viewModelScope.launch {
            _registerState.value = AuthState.Loading
            try {
                val user = authRepo.register(userName, age, email, password)
                _registerState.value = AuthState.Success(user)
            } catch (e: Exception) {
                _registerState.value = AuthState.Error(e)
                Log.i("register error", "register: " + e.message)
            }
        }
    }
}
