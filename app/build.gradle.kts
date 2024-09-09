@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.kapt)
    alias(libs.plugins.hiltPlugin)
}

android {
    namespace = "com.avinash.zapcom.demo"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.avinash.zapcom.demo"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        debug {
            versionNameSuffix = "Debug"
            isDebuggable = true
            enableUnitTestCoverage = true
            enableAndroidTestCoverage = true
        }
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
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

    // Dependencies for Hilt
    implementation(libs.hilt)
    kapt(libs.hiltCompiler)
    androidTestImplementation(libs.hiltAndroidTesting) // For instrumented tests.
    kaptAndroidTest(libs.hiltAndroidCompiler) // ...with Kotlin.
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