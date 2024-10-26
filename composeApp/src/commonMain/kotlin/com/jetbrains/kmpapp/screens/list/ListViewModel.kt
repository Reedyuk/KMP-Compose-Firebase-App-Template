package com.jetbrains.kmpapp.screens.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jetbrains.kmpapp.data.MuseumObject
import com.jetbrains.kmpapp.data.MuseumRepository
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ListViewModel(museumRepository: MuseumRepository) : ViewModel() {
    val showLoginButton: StateFlow<Boolean> = Firebase.auth.authStateChanged.map {
        it?.isAnonymous != false
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), Firebase.auth.currentUser?.isAnonymous != false)

    val objects: StateFlow<List<MuseumObject>> =
        museumRepository.getObjects()
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun logout() {
        CoroutineScope(Dispatchers.IO).launch {
            Firebase.auth.signOut()
        }
    }
}
