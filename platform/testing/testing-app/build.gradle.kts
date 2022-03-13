
plugins {
    id("modules.platform.android")
}

dependencies {

    implementation(projects.platform.core.coreNavigator)

    implementation(Deps.kodeinDi)
    implementation(Deps.kodeinType)
    implementation(Deps.androidxLifecycleCommon)
    implementation(Deps.okHttp)
    implementation(Deps.robolectric)
    implementation(Deps.androidxTestCore)
    implementation(Deps.androidxTestCoreKtx)
    implementation(Deps.androidxTestMonitor)
}
