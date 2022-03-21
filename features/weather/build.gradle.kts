
plugins {
    id("modules.feature.android")
    id("org.jetbrains.kotlin.plugin.serialization")
    id("kotlin-parcelize")
}

dependencies {

    implementation(projects.platform.common.commonAndroid)
    implementation(projects.platform.common.commonKodein)
    implementation(projects.platform.common.commonStatic)
    implementation(projects.platform.core.coreNavigator)
    implementation(projects.platform.core.coreNetworking)

    implementation(Deps.retrofit)
    implementation(Deps.kotlinSerializationCore)
    implementation(Deps.kotlinSerializationJson)
    implementation(Deps.kotlinSerializationJvm)

    implementation(Deps.kodeinDi)
    implementation(Deps.kodeinType)
    implementation(Deps.coroutinesCore)
    implementation(Deps.coroutinesAndroid)
    implementation(Deps.coroutinesJvm)
    implementation(Deps.androidxLifecycleCommon)
    implementation(Deps.androidxLifecycleRuntime)
    implementation(Deps.androidxLifecycleViewModel)
    implementation(Deps.androidxLifecycleViewModelKtx)
    implementation(Deps.androidxAppCompat)
    implementation(Deps.androidxSwipeToRefresh)
    implementation(Deps.androidxCoordinatorLayout)
    implementation(Deps.androidxRecyclerView)
    implementation(Deps.androidxCore)
    implementation(Deps.googleMaterialDesign)
    implementation("io.coil-kt:coil:2.0.0-rc02")

    testImplementation(projects.platform.testing.testingApp)
    testImplementation(projects.platform.testing.testingCommons)

    testImplementation(Deps.junit4)
    testImplementation(Deps.truth)
    testImplementation(Deps.okHttpMockWebServer)
    testImplementation(Deps.androidxTestCore)
    testImplementation(Deps.androidxTestExtJunit)
    testImplementation(Deps.robolectric)
}
