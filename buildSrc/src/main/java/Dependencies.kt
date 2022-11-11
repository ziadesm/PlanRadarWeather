package com.planradar.weather.buildsrc

object Versions {
    const val kotlin = "1.7.10"
    const val hilt = "2.42"
    const val navigation = "2.5.3"
    const val coroutinesTest = "1.6.4"
    const val uiAutomator = "2.2.0"
    const val junit = "4.13.2"
    const val junitKtx = "1.1.3"
    const val coreKtx = "1.4.0"
    const val mockito = "4.8.0"
    const val robolectric = "4.8"
    const val rules = "1.4.0"
    const val mockWebServer = "4.9.3"
    const val mockk = "1.13.2"
    const val gson = "2.9.0"
    const val retrofit = "2.9.0"
    const val okhttp = "4.9.3"
    const val room = "2.4.3"
    const val timber = "5.0.1"
    const val fragment_lifecycle = "1.5.4"
}


object Libs {
    const val androidGradlePlugin = "com.android.tools.build:gradle:7.2.2"

    object Kotlin {
        const val gradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
    }

    object Hilt {
        const val android = "com.google.dagger:hilt-android:${Versions.hilt}"
        const val compiler = "androidx.hilt:hilt-compiler:1.0.0"
        const val kapt_compiler = "com.google.dagger:hilt-compiler:${Versions.hilt}"

        const val androidCompiler = "com.google.dagger:hilt-android-compiler:${Versions.hilt}"
        const val androidTesting = "com.google.dagger:hilt-android-testing:${Versions.hilt}"
    }


    object Android {
        const val coroutinesTest = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.coroutinesTest}"

    }

    object Navigation {
        const val fragment = "androidx.navigation:navigation-fragment-ktx:${Versions.navigation}"
        const val ui = "androidx.navigation:navigation-ui-ktx:${Versions.navigation}"
    }

    object Lifecycle {
        const val fragment = "androidx.fragment:fragment-ktx:${Versions.fragment_lifecycle}"
    }

    object Testing {

        const val uiAutomator = "androidx.test.uiautomator:uiautomator:${Versions.uiAutomator}"
        const val jUnit = "junit:junit:${Versions.junit}"
        const val rules = "androidx.test:rules:${Versions.rules}"
        const val junitKtx = "androidx.test.ext:junit-ktx:${Versions.junitKtx}"
        const val coreKtx = "androidx.test:core-ktx:${Versions.coreKtx}"
        const val mockito = "org.mockito:mockito-core:${Versions.mockito}"
        const val robolectric = "org.robolectric:robolectric:${Versions.robolectric}"
        const val mockWebServer = "com.squareup.okhttp3:mockwebserver:${Versions.mockWebServer}"
    }

    object Network {
        const val gson = "com.google.code.gson:gson:${Versions.gson}"
        const val gsonConverter = "com.squareup.retrofit2:converter-gson:${Versions.retrofit}"
        const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
        const val okhttp = "com.squareup.okhttp3:okhttp:${Versions.okhttp}"
        const val loggingInterceptor = "com.squareup.okhttp3:logging-interceptor:${Versions.okhttp}"
    }

    object Cache{
        const val runtime = "androidx.room:room-runtime:${Versions.room}"
        const val compiler = "androidx.room:room-compiler:${Versions.room}"
        const val ktx = "androidx.room:room-ktx:${Versions.room}"
    }
    object Others{
        const val timber = "com.jakewharton.timber:timber:${Versions.timber}"
    }
}