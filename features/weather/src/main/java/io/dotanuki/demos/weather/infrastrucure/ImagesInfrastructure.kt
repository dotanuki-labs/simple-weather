package io.dotanuki.demos.weather.infrastrucure

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import okio.BufferedSink
import okio.ByteString
import okio.ByteString.Companion.toByteString
import okio.buffer
import okio.sink
import java.io.File
import java.io.IOException

class ImagesInfrastructure(private val cacheDir: File) {

    private val httpClient by lazy {
        OkHttpClient.Builder().build()
    }

    suspend fun retrieveIfDownloaded(imageUrl: String): File? = withContext(Dispatchers.IO) {
        val cacheFileName = imageUrl.encodeToByteArray().toByteString().base64()
        val downloaded = File("${cacheDir}/$cacheFileName")

        when {
            downloaded.exists() -> downloaded
            else -> null
        }
    }

    suspend fun retrieve(imageUrl: String): File = withContext(Dispatchers.IO) {
        val cacheFileName = imageUrl.encodeToByteArray().toByteString().base64()
        val imageFile = File("${cacheDir}/$cacheFileName")

        val downloadRequest = Request.Builder().url(imageUrl).get().build()
        val response = httpClient.newCall(downloadRequest).execute();

        if (!response.isSuccessful) {
            throw IOException("Unexpected code: $response");
        }

        requireNotNull(response.body).source().use { bufferedSource ->
            val bufferedSink: BufferedSink = imageFile.sink().buffer()
            bufferedSink.writeAll(bufferedSource)
            bufferedSink.close()
        }

        imageFile
    }
}
