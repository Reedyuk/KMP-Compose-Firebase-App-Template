package com.jetbrains.kmpapp.data

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.firestore.firestore
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.utils.io.CancellationException

interface MuseumApi {
    suspend fun getData(): List<MuseumObject>
}

object KtorMuseumApi : MuseumApi {
    override suspend fun getData(): List<MuseumObject> {
        val collection = Firebase.firestore
            .collection("art")
            .get()
        println(collection)
        println(collection.metadata)

        return Firebase.firestore
            .collection("art")
            .get()
            .documents
            .map {
                it.data()
            }
    }
}
