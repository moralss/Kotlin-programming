package com.budgetapp.app.budget.config

import com.google.auth.oauth2.GoogleCredentials
import com.google.cloud.firestore.Firestore
import com.google.cloud.firestore.FirestoreOptions
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import jdk.nashorn.internal.objects.NativeDebug.getClass
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
        val classLoader = javaClass.classLoader
        val serviceAccount =classLoader.getResourceAsStream("buget-app-411e3.json")
        val options = FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount)).setDatabaseUrl(firebaseDatabaseUrl).build()
        println(options)
        if (FirebaseApp.getApps().isEmpty()) {
            FirebaseApp.initializeApp(options)
        }
    }

    @get:Throws(IOException::class)
    @get:Bean
    val database: Firestore
        get() {
            val classLoader = javaClass.classLoader
            val serviceAccount =classLoader.getResourceAsStream("buget-app-411e3.json")
            val firestoreOptions = FirestoreOptions.newBuilder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount)).build()
            return firestoreOptions.service
        }
}