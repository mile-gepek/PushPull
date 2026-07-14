plugins {
    this.alias(this.libs.plugins.android.application)
    this.kotlin("plugin.serialization")
}

android {
    this.namespace = "com.example.pushpull"
    this.compileSdk {
        this.version = this.release(36) {
            this.minorApiLevel = 1
        }
    }

    this.defaultConfig {
        this.applicationId = "com.example.pushpull"
        this.minSdk = 26
        this.targetSdk = 36
        this.versionCode = 1
        this.versionName = "1.0"

        this.testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    this.buildTypes {
        this.release {
            this.optimization {
                this.enable = false
            }
        }
    }

    this.buildFeatures {
        this.viewBinding = true
    }

    this.compileOptions {
        this.sourceCompatibility = JavaVersion.VERSION_11
        this.targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    this.implementation(libs.androidx.activity.ktx)
    this.implementation(libs.androidx.appcompat)
    this.implementation(libs.androidx.constraintlayout)
    this.implementation(libs.androidx.core.ktx)
    this.implementation(libs.androidx.legacy.support.v4)
    this.implementation(libs.androidx.recyclerview)
    this.implementation(libs.androidx.viewpager2)
    this.implementation(libs.material)
    this.testImplementation(libs.junit)
    this.androidTestImplementation(libs.androidx.espresso.core)
    this.androidTestImplementation(libs.androidx.junit)

    this.implementation(libs.kotlinx.serialization.json)
}

