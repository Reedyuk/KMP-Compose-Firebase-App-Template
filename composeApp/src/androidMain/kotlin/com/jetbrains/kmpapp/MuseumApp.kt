package com.jetbrains.kmpapp

import android.app.Application
import com.google.firebase.FirebaseApp
import com.jetbrains.kmpapp.di.initKoin
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.app
import dev.gitlive.firebase.auth.auth
import dev.gitlive.firebase.firestore.FirebaseFirestore
import dev.gitlive.firebase.firestore.FirebaseFirestoreSettings
import dev.gitlive.firebase.firestore.firestore

class MuseumApp : Application() {
    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
        // Make sure to set this with your own port values
        Firebase.auth.useEmulator("10.0.2.2", 9099)
        Firebase.firestore.useEmulator("10.0.2.2", 8080)
        initKoin()
    }
}
