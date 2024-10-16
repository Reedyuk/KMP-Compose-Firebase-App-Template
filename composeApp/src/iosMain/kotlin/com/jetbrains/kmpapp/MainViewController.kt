package com.jetbrains.kmpapp

import androidx.compose.ui.window.ComposeUIViewController
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.firestore.FirebaseFirestoreSettings
import dev.gitlive.firebase.firestore.LocalCacheSettings
import dev.gitlive.firebase.firestore.firestore
import dev.gitlive.firebase.initialize
import platform.darwin.dispatch_get_main_queue

fun MainViewController() = ComposeUIViewController {
    App()
}

fun setupFirebase() {
    Firebase.initialize()
    Firebase.firestore.settings = FirebaseFirestoreSettings(
        host = "127.0.0.1:8080",
        sslEnabled = false,
        cacheSettings = LocalCacheSettings.Memory.newBuilder().build(),
        dispatchQueue = dispatch_get_main_queue()
    )
}