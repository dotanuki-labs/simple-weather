package conventions

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.gradle.BaseExtension
import definitions.AndroidDefinitions
import definitions.ProguardDefinitions
import definitions.Versioning
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import java.io.File
import java.io.FileInputStream
import java.util.Properties

fun Project.applyAndroidStandardConventions() {
    val android = extensions.findByName("android") as BaseExtension

    android.apply {
        compileSdkVersion(AndroidDefinitions.compileSdk)
        buildToolsVersion(AndroidDefinitions.buildToolsVersion)

        defaultConfig {

            minSdk = AndroidDefinitions.minSdk
            targetSdk = AndroidDefinitions.targetSdk
            versionCode = Versioning.version.code
            versionName = Versioning.version.name

            vectorDrawables.apply {
                useSupportLibrary = true
                generatedDensities(*(AndroidDefinitions.noGeneratedDensities))
            }

            resourceConfigurations.add("en")
        }

        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_11
            targetCompatibility = JavaVersion.VERSION_11
        }

        testOptions {
            unitTests.isReturnDefaultValues = true
            unitTests.isIncludeAndroidResources = true
            unitTests.all {
                // https://github.com/robolectric/robolectric/issues/3023
                it.jvmArgs?.addAll(
                    listOf("-ea", "-noverify")
                )
            }
        }
    }
}

fun Project.applyAndroidLibraryConventions() {
    applyAndroidStandardConventions()

    val android = extensions.findByName("android") as BaseExtension

    android.apply {
        buildTypes {
            getByName("release") {
                isMinifyEnabled = false
            }
        }
    }
}

fun Project.applyAndroidApplicationConventions() {
    applyAndroidStandardConventions()

    val android = extensions.findByName("android") as ApplicationExtension

    android.apply {

        defaultConfig {
            applicationId = AndroidDefinitions.applicationId
            testInstrumentationRunner = AndroidDefinitions.instrumentationTestRunner
        }

        buildTypes {

            getByName("debug") {
                applicationIdSuffix = ".debug"
                versionNameSuffix = "-DEBUG"
            }

            getByName("release") {
                isMinifyEnabled = false
                isShrinkResources = false
            }
        }

        packagingOptions {
            jniLibs.useLegacyPackaging = true
        }
    }
}
