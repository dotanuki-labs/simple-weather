package io.dotanuki.bootstrap.testing.coroutines

import kotlinx.coroutines.*
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.rules.ExternalResource
import java.util.concurrent.Executors

@OptIn(ExperimentalCoroutinesApi::class)
class CoroutinesTestHelper : ExternalResource() {

    private val singleThread = Executors.newSingleThreadExecutor().asCoroutineDispatcher()
    val scope = CoroutineScope(singleThread)

    override fun before() {
        Dispatchers.setMain(singleThread)
        super.before()
    }

    override fun after() {
        Dispatchers.resetMain()
        singleThread.close()
        scope.cancel()
        super.after()
    }
}
