
plugins {
    id("modules.platform.jvm")
}

dependencies {

    implementation(Deps.okHttp)
    implementation(Deps.okHttpLogging)
    implementation(Deps.retrofit)
    implementation(Deps.retrofitKotlixConverter)
    implementation(Deps.kotlinSerializationCore)
    implementation(Deps.kotlinSerializationJvm)
    implementation(Deps.kotlinSerializationJson)
    implementation(Deps.coroutinesCore)

    testImplementation(Deps.junit4)
    testImplementation(Deps.truth)
    testImplementation(Deps.coroutinesJvm)
}
