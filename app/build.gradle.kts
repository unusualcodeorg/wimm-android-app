plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.dagger.hilt.android")
    id("kotlin-parcelize")
    id("com.google.devtools.ksp")
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
}

android {
    namespace = "com.whereismymotivation"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.whereismymotivation"
        minSdk = 24
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
            buildConfigField("String", "BASE_URL", "\"http://192.168.1.6:3000/\"")
            buildConfigField("String", "API_KEY", "\"1D3F2DD1A5DE725DD4DF1D82BBB37\"")
        }
        release {
            isDebuggable = false
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
            )
            buildConfigField("String", "BASE_URL", "\"http://192.168.1.5:3000/\"")
            buildConfigField("String", "API_KEY", "\"KHTLL24C5AWEB\"")
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
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.8"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
            excludes += "/META-INF/LICENSE.md"
        }
    }
}

dependencies {

    val core = "1.12.0"
    implementation("androidx.core:core-ktx:$core")

    // Material Design 3
    val material3 = "1.1.2"
    implementation("androidx.compose.material3:material3:$material3")

    // lifecycle
    val lifecycle = "2.7.0"
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:$lifecycle")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycle")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:$lifecycle")
    implementation("androidx.lifecycle:lifecycle-runtime-compose:$lifecycle")

    // Compose
    val compose = "2023.03.00"
    implementation(platform("androidx.compose:compose-bom:$compose"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.compose.ui:ui-tooling-preview")
    debugImplementation("androidx.compose.ui:ui-tooling")
    implementation("androidx.compose.material:material-icons-extended")
    implementation("androidx.compose.material3:material3-window-size-class")

    val navigation = "2.7.6"
    implementation("androidx.navigation:navigation-compose:$navigation")

    val constraintlayout = "1.0.1"
    implementation("androidx.constraintlayout:constraintlayout-compose:$constraintlayout")

    val activity = "1.8.2"
    implementation("androidx.activity:activity-compose:$activity")

    val browser = "1.7.0"
    implementation("androidx.browser:browser:$browser")

    val charty = "2.0.0-alpha01"
    implementation("com.himanshoe:charty:$charty")

    // Coroutine
    val coroutines = "1.7.3"
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutines")

    // Log
    val timber = "5.0.1"
    implementation("com.jakewharton.timber:timber:$timber")

    // Network
    val retrofit2 = "2.9.0"
    implementation("com.squareup.retrofit2:retrofit:$retrofit2")
    implementation("com.squareup.retrofit2:converter-moshi:${retrofit2}")

    val okhttp3 = "4.11.0"
    implementation(platform("com.squareup.okhttp3:okhttp-bom:$okhttp3"))
    implementation("com.squareup.okhttp3:okhttp")
    implementation("com.squareup.okhttp3:logging-interceptor")

    // Firebase
    val firebase = "32.7.0"
    implementation(platform("com.google.firebase:firebase-bom:$firebase"))
    implementation("com.google.firebase:firebase-analytics")
    implementation("com.google.firebase:firebase-messaging")
    implementation("com.google.firebase:firebase-crashlytics")
    implementation("com.google.firebase:firebase-config")

    val crashlytics = "2.9.9"
    implementation("com.google.firebase:firebase-crashlytics-buildtools:$crashlytics")

    // Work
    val work = "2.9.0"
    implementation("androidx.work:work-runtime-ktx:$work")

    // JSON
    val moshi = "1.15.0"
    implementation("com.squareup.moshi:moshi:$moshi")
    ksp("com.squareup.moshi:moshi-kotlin-codegen:$moshi")

    // Database
    val room = "2.6.1"
    implementation("androidx.room:room-runtime:$room")
    ksp("androidx.room:room-compiler:$room")
    implementation("androidx.room:room-ktx:$room")

    // Dependency Management
    val hilt = "2.50"
    implementation("com.google.dagger:hilt-android:$hilt")
    ksp("com.google.dagger:hilt-android-compiler:$hilt")

    val hiltKtx = "1.1.0"
    implementation("androidx.hilt:hilt-navigation-compose:$hiltKtx")
    implementation("androidx.hilt:hilt-work:$hiltKtx")
    ksp("androidx.hilt:hilt-compiler:$hiltKtx")

    // Image
    val coil = "2.5.0"
    implementation("io.coil-kt:coil:$coil")
    implementation("io.coil-kt:coil-compose:$coil")

    val lottie = "6.3.0"
    implementation("com.airbnb.android:lottie-compose:$lottie")

    // Unit Test
    val junit = "4.13.2"
    testImplementation("junit:junit:$junit")

    val mockk = "1.13.9"
    testImplementation("io.mockk:mockk:$mockk")

    val coroutinesTest = "1.7.3"
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:$coroutinesTest")

    // Android Test
    val junitExt = "1.1.5"
    androidTestImplementation("androidx.test.ext:junit:$junitExt")

    val espresso = "3.5.1"
    androidTestImplementation("androidx.test.espresso:espresso-core:$espresso")

    androidTestImplementation("io.mockk:mockk-android:$mockk")

    androidTestImplementation(platform("androidx.compose:compose-bom:$compose"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

}