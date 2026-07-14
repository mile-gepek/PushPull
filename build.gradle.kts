// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    this.alias(this.libs.plugins.android.application) apply false

    this.kotlin("android") version "2.4.0" apply false
    this.kotlin("plugin.serialization") version "2.4.0" apply false
}