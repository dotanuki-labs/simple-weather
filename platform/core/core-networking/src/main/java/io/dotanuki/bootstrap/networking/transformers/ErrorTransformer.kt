package io.dotanuki.bootstrap.networking.transformers

interface ErrorTransformer {

    suspend fun transform(incoming: Throwable): Throwable
}
