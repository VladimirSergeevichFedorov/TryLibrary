plugins {

    kotlin("multiplatform") version "1.8.21"
    kotlin("native.cocoapods")
    id("com.android.library")
    id("convention.publication")
}

group = "io.github.VladimirSergeevichFedorov"
version = "1.0.8"

buildscript {
    repositories {
        google()
        mavenCentral()
    }
}

@OptIn(org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi::class)
kotlin {
    targetHierarchy.default()

    android {
        publishLibraryVariants("release", "debug")
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }

    iosX64()
    iosArm64()
    iosSimulatorArm64()

    cocoapods {
        summary = "Some description for the Shared Module"
        homepage = "Link to the Shared Module homepage"
        version = "1.0"
        ios.deploymentTarget = "14.1"
        framework {
            baseName = "shared"
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                //put your multiplatform dependencies here
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
            val androidMain by getting {
                dependencies {
                    implementation ("androidx.core:core-ktx:1.8.0")
                    implementation ("androidx.appcompat:appcompat:1.6.1")
                    implementation ("com.google.android.material:material:1.5.0")
                    implementation("androidx.activity:activity-compose:1.7.2")
                    implementation("androidx.compose.ui:ui:1.4.3")
                    implementation("androidx.compose.ui:ui-tooling:1.4.3")
                    implementation("androidx.compose.ui:ui-tooling-preview:1.4.3")
                    implementation("androidx.compose.foundation:foundation:1.4.3")
                    implementation("androidx.compose.material:material:1.4.3")
                }

            }
            val androidUnitTest by getting
            val iosX64Main by getting
            val iosArm64Main by getting
            val iosSimulatorArm64Main by getting
            val iosMain by getting

            val iosX64Test by getting
            val iosArm64Test by getting
            val iosSimulatorArm64Test by getting
            val iosTest by getting
        }
    }
}

android {
    namespace = "com.example.newtrykmmlibrary"
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    compileSdk = 33
    defaultConfig {
        minSdk = 24
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.7"
    }
    publishing {
        singleVariant("release") {
            withSourcesJar()
            withJavadocJar()
        }
    }
}