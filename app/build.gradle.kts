
plugins {
    id("modules.app.android")
}

dependencies {
    implementation(projects.platform.core.coreNavigator)
    implementation(projects.platform.common.commonAndroid)
    implementation(projects.platform.common.commonKodein)
    implementation(projects.platform.common.commonStatic)
    implementation(projects.features.listing)

    implementation(Deps.kodeinDi)
    implementation(Deps.kodeinType)
    implementation(Deps.coroutinesCore)
    implementation(Deps.coroutinesJvm)
    implementation(Deps.coroutinesAndroid)

    implementation(Deps.androidxCore)
    implementation(Deps.androidxCoreKtx)
    implementation(Deps.androidxAppCompat)
    implementation(Deps.androidxLifecycleCommon)
    implementation(Deps.androidxLifecycleRuntime)
    implementation(Deps.okHttp)
    implementation(Deps.playServicesMaps)

    androidTestImplementation(Deps.junit4)
    androidTestImplementation(Deps.truth)
    androidTestImplementation(Deps.androidxTestCore)
    androidTestImplementation(Deps.androidxTestCoreKtx)
    androidTestImplementation(Deps.androidxTestExtJunit)
    androidTestImplementation(Deps.androidxTestExtJunitKtx)
    androidTestImplementation(Deps.androidxTestRunner)
    androidTestImplementation(Deps.androidxTestRules)
    androidTestImplementation(Deps.okHttpMockWebServer)
    androidTestImplementation(Deps.espressoCore)
    androidTestImplementation(Deps.radiography)

    androidTestImplementation(Deps.barista) {
        exclude(group = "org.jetbrains.kotlin")
        exclude(group = "org.checkerframework")
    }
}
