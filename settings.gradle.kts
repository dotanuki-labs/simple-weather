pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

include(
    ":app",
    ":platform:testing:testing-app",
    ":platform:testing:testing-commons",
    ":platform:core:core-navigator",
    ":platform:core:core-networking",
    ":platform:common:common-static",
    ":platform:common:common-android",
    ":platform:common:common-kodein",
    ":features:listing"
)

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
