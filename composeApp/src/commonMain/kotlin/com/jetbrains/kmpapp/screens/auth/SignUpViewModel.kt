package com.jetbrains.kmpapp.screens.auth

import androidx.lifecycle.ViewModel
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class SignUpViewModel() : ViewModel() {

    val userInfoMessage: MutableStateFlow<String?> = MutableStateFlow(null)

    fun signUp(email: String, password: String) {
        userInfoMessage.tryEmit(null)
        if (email.isEmpty() || password.isEmpty()) {
            userInfoMessage.tryEmit("Please enter a valid email and password")
            return
        }
        CoroutineScope(Dispatchers.IO).launch {
            runCatching {
                val status = Firebase.auth.createUserWithEmailAndPassword(email, password)
                if (status.user == null || status.user?.isAnonymous == true) {
                    userInfoMessage.tryEmit("An error ocurred when trying to sign up, please try again.")
                } else {
                // dismiss signup screen.

                }
            }.onFailure {
                userInfoMessage.tryEmit(it.message ?: "ooops, something went wrong")
            }
        }
    }
}
