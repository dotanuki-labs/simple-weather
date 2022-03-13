package io.dotanuki.demos.testing.commons.files

fun Any.loadFile(path: String) =
    this.javaClass
        .classLoader
        .getResourceAsStream(path)
        .bufferedReader()
        .use { it.readText() }
