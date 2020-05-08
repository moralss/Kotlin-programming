package com.budgetapp.app.budget.config

import com.google.auth.oauth2.GoogleCredentials
import com.google.cloud.firestore.Firestore
import com.google.cloud.firestore.FirestoreOptions
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import java.io.IOException


@Configuration
class FirebaseConfig {

    @Value("\${spring.firebase.databaseUrl}")
    val firebaseDatabaseUrl: String? = null

    @Primary
    @Bean
    @Throws(IOException::class)
    fun firebaseInit() {
        val options = FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.getApplicationDefault()).setDatabaseUrl(firebaseDatabaseUrl).build()
        println(options)
        if (FirebaseApp.getApps().isEmpty()) {
            FirebaseApp.initializeApp(options)
        }
    }

    @get:Throws(IOException::class)
    @get:Bean
    val database: Firestore
        get() {
            val firestoreOptions = FirestoreOptions.newBuilder()
                    .setCredentials(GoogleCredentials.getApplicationDefault()).build()
            return firestoreOptions.service
        }
}