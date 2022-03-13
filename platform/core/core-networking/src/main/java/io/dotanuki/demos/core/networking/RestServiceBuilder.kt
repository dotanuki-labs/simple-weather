package io.dotanuki.demos.core.networking

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level.BODY
import retrofit2.Retrofit

@OptIn(ExperimentalSerializationApi::class)
object RestServiceBuilder {

    inline fun <reified T> build(apiURL: String): T = build(apiURL.toHttpUrl())

    inline fun <reified T> build(apiURL: HttpUrl): T {

        val standardJsonConfig by lazy {
            Json {
                isLenient = false
                ignoreUnknownKeys = true
                allowSpecialFloatingPointValues = true
            }
        }

        val standardHttpClient = OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(BODY))
            .build()

        val contentType by lazy {
            "application/json".toMediaTypeOrNull()!!
        }

        val retrofit = Retrofit.Builder()
            .baseUrl(apiURL)
            .client(standardHttpClient)
            .addConverterFactory(standardJsonConfig.asConverterFactory(contentType))
            .build()

        return retrofit.create(T::class.java)
    }
}
