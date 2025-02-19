import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)

    //Se necesita para la db SQLDelight
    kotlin("plugin.serialization") version "2.0.0"

    // SQLDelight
    id("app.cash.sqldelight") version "2.0.2"

}

sqldelight {
    databases {
        create("AppDatabase") {
            packageName.set("com.expenseApp.db")
        }
    }
}

kotlin {
    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }

    listOf(
        iosX64(), iosArm64(), iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }

    sourceSets {

        androidMain.dependencies {
            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)

            //Koin - dependences Injection
            implementation(project.dependencies.platform("io.insert-koin:koin-bom:3.5.1"))
            implementation(libs.koin.core)
            implementation(libs.insert.koin.koin.android)

            //SQLDelight
            implementation(libs.android.driver)
        }
        commonMain.dependencies {
            implementation(compose.runtime)
            api(compose.foundation)
            api(compose.animation)
            implementation(compose.material)
            api(compose.materialIconsExtended)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodel)
            implementation(libs.androidx.lifecycle.runtime.compose)

            // Navigation Precompose
            api(libs.precompose)
            // ViewModel
            api(libs.precompose.viewmodel)

            //Koin - dependences Injection
            implementation(project.dependencies.platform("io.insert-koin:koin-bom:3.5.1"))
            implementation(libs.insert.koin.koin.core)
            implementation(libs.insert.koin.koin.compose)
            api(libs.precompose.koin)
        }
        iosMain.dependencies {
            //IOS dependencies

            //SQLDelight
            implementation(libs.native.driver)

        }

        // commonTest Dependencies
        commonTest.dependencies {
            implementation(libs.kotlin.test)
            implementation(libs.kotlinx.coroutines.core)

        }
    }
}

android {
    namespace = "org.example.project"
    compileSdk = 35

    defaultConfig {
        applicationId = "org.example.project"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    buildFeatures {
        compose = true
    }
}

dependencies {
    debugImplementation(compose.uiTooling)
}

