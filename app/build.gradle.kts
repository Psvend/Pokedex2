
plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("com.google.devtools.ksp")
    id("dagger.hilt.android.plugin")
    alias(libs.plugins.protobuf)
}

android {
    namespace = "com.example.pokedex2"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.pokedex2"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {
    // Proto and datastore
    // DataStore and Protobuf
    implementation(libs.androidx.datastore) // DataStore dependency
    implementation(libs.protobuf.javalite) // Protobuf Lite

    // Retrofit
    implementation(libs.retrofit)
    // Retrofit with Scalar Converter
    implementation(libs.converter.gson)

    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation (libs.coil.kt.coil.compose)
    implementation(libs.support.annotations)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    implementation (libs.androidx.lifecycle.runtime.compose)
    implementation (libs.androidx.lifecycle.viewmodel.compose)

    // Dagger - Hilt
    implementation (libs.hilt.android)
    ksp (libs.hilt.android.compiler)
    ksp (libs.androidx.hilt.compiler)
    implementation (libs.androidx.hilt.navigation.compose)
    implementation(libs.dagger.android)
    ksp(libs.dagger.compiler)
    implementation(libs.hilt.android.v252)
    ksp(libs.hilt.android.compiler.v252)

    implementation (libs.androidx.room.ktx)
    ksp (libs.androidx.room.compiler)
    implementation (libs.androidx.room.paging)
    // Paging
    implementation (libs.androidx.paging.runtime.ktx)
    implementation (libs.androidx.paging.compose)
}

// Setup protobuf configuration, generating lite Java and Kotlin classes

protobuf {
    protoc {
        artifact = libs.protobuf.protoc.get().toString() // Correct Protobuf artifact
    }
    generateProtoTasks {
        all().forEach { task ->
            task.builtins {
                create("java") {
                    option("lite") // Generate lightweight Protobuf classes
                }
            }
        }
    }
}
