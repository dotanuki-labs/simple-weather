package io.dotanuki.bootstrap.networking

import io.dotanuki.bootstrap.networking.transformers.HttpErrorTransformer
import io.dotanuki.bootstrap.networking.transformers.NetworkingErrorTransformer
import io.dotanuki.bootstrap.networking.transformers.SerializationErrorTransformer

private val transformers = listOf(
    HttpErrorTransformer,
    NetworkingErrorTransformer,
    SerializationErrorTransformer
)

suspend fun <T> managedExecution(target: suspend () -> T): T =
    try {
        target.invoke()
    } catch (incoming: Throwable) {
        throw transformers
            .map { it.transform(incoming) }
            .reduce { transformed, another ->
                when {
                    transformed == another -> transformed
                    another == incoming -> transformed
                    else -> another
                }
            }
    }
