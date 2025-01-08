@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.kapt)
    alias(libs.plugins.hiltPlugin)
    id("com.google.gms.google-services")
    alias(libs.plugins.compose.compiler)
}

android {
    namespace = "com.avinash.zapcom.demo"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.avinash.zapcom.demo"
        minSdk = 26
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    signingConfigs {
        create("test") {
            keyAlias = "debug_alias"
            keyPassword = "debug_alias"
            storeFile = file("secret/debug.keystore")
            storePassword = "debugkey"
        }
    }

    buildTypes {
        debug {
            versionNameSuffix = "Debug"
            isDebuggable = true
            enableUnitTestCoverage = true
            enableAndroidTestCoverage = true
            signingConfig = signingConfigs.getByName("test")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
            excludes += "build-data.properties"
            merges += "META-INF/LICENSE.md"
            merges += "META-INF/LICENSE-notice.md"
        }
    }
}

dependencies {

    implementation(libs.core.ktx)
    implementation(libs.lifecycle.runtime.ktx)
    implementation(libs.activity.compose)
    implementation(platform(libs.compose.bom))
    implementation(libs.ui)
    implementation(libs.ui.graphics)
    implementation(libs.ui.tooling.preview)
    implementation(libs.material3)
    implementation(libs.retrofit)
    implementation(libs.converterGson)
    kapt(libs.retrofit)
    implementation(libs.rxJava)
    implementation(libs.rxAndroid)
    implementation(libs.retrofitAdapter)
    implementation(libs.okHttpLogging)
    implementation(libs.coilCompose)
    implementation(libs.placeHolder)
    implementation(libs.composeRuntime)

    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics)

    // Dependencies for Hilt
    implementation(libs.hilt)
    kapt(libs.hiltCompiler)
    androidTestImplementation(libs.hiltAndroidTesting) // For instrumented tests.
    kaptAndroidTest(libs.hiltAndroidCompiler) // ...with Kotlin.
//    kapt("org.jetbrains.kotlinx:kotlinx-metadata-jvm:0.5.0")
    implementation(libs.hiltWork)
    implementation(libs.workRunTimeKtx)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)
    androidTestImplementation(platform(libs.compose.bom))
    androidTestImplementation(libs.ui.test.junit4)
    androidTestImplementation(libs.mockWebServer)
    androidTestImplementation(libs.archCoreTesting)
    debugImplementation(libs.ui.tooling)
    debugImplementation(libs.ui.test.manifest)
}

kapt {
    correctErrorTypes = true
}