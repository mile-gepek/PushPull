// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false

    kotlin("android") version "2.4.0" apply false
    kotlin("plugin.serialization") version "2.4.0" apply false
}