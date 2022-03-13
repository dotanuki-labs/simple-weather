plugins {
    id("modules.platform.android")
}

dependencies {
    implementation(projects.platform.common.commonKodein)

    implementation(Deps.kodeinDi)
    implementation(Deps.kodeinType)
    implementation(Deps.androidxAppCompat)
    implementation(Deps.androidxLifecycleCommon)
    implementation(Deps.coroutinesJvm)
}
