// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version Versions.ANDROID_APPLICATION_GRADLE_PLUGIN_VERSION apply false
    id("org.jetbrains.kotlin.android") version Versions.KOTLIN_GRADLE_PLUGIN_VERSION apply false
    id("com.google.devtools.ksp") version Versions.KSP_PLUGIN_VERSION apply false
    id("androidx.room") version Versions.ANDROIDX_ROOM_VERSION apply false
}