package io.dotanuki.demos.core.networking.transformers

interface ErrorTransformer {

    suspend fun transform(incoming: Throwable): Throwable
}
