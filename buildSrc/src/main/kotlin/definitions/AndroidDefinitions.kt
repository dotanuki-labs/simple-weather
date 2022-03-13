package definitions

import java.util.Collections

object AndroidDefinitions {

    const val applicationId = "io.dotanuki.bootstrap.android"

    const val compileSdk = 31
    const val minSdk = 26
    const val targetSdk = compileSdk

    const val buildToolsVersion = "31.0.0"

    const val instrumentationTestRunner = "androidx.test.runner.AndroidJUnitRunner"

    val noGeneratedDensities = Collections.emptySet<String>().toTypedArray()
}
