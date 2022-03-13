package io.dotanuki.bootstrap.networking.transformers

import com.google.common.truth.Truth.assertThat
import io.dotanuki.bootstrap.networking.transformers.CheckErrorTransformation.Companion.checkTransformation
import io.dotanuki.bootstrap.networking.errors.RemoteServiceIntegrationError.ClientOrigin
import io.dotanuki.bootstrap.networking.errors.RemoteServiceIntegrationError.RemoteSystem
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response

class HttpErrorTransformerTests {

    @Test fun `should transform http error from downstream`() {
        listOf(
            httpException<Any>(418, "teapot") to ClientOrigin,
            httpException<Any>(503, "Internal Server Error") to RemoteSystem
        ).forEach { (incoming, expected) ->
            assertTransformation(incoming, expected)
        }
    }

    @Test fun `should propagate any other error`() {
        val otherError = IllegalStateException("Houston, we have a problem!")
        assertTransformation(otherError, otherError)
    }

    private fun assertTransformation(target: Throwable, expected: Throwable) {
        checkTransformation(
            from = target,
            using = HttpErrorTransformer,
            check = { transformed -> assertThat(transformed).isEqualTo(expected) }
        )
    }

    private fun <T> httpException(statusCode: Int, errorMessage: String): HttpException {
        val jsonMediaType = "application/json".toMediaTypeOrNull()
        val body = errorMessage.toResponseBody(jsonMediaType)
        return HttpException(Response.error<T>(statusCode, body))
    }
}
