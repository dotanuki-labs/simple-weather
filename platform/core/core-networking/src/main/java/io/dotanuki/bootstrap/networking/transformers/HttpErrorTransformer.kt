package io.dotanuki.bootstrap.networking.transformers

import io.dotanuki.bootstrap.networking.errors.RemoteServiceIntegrationError
import retrofit2.HttpException

object HttpErrorTransformer : ErrorTransformer {

    override suspend fun transform(incoming: Throwable) =
        when (incoming) {
            is HttpException -> translateUsingStatusCode(incoming.code())
            else -> incoming
        }

    private fun translateUsingStatusCode(code: Int) =
        when (code) {
            in 400..499 -> RemoteServiceIntegrationError.ClientOrigin
            else -> RemoteServiceIntegrationError.RemoteSystem
        }
}
