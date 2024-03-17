plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.brandonhc.clocksampleapp"
    compileSdk = Versions.SDK_COMPILE_VERSION

    defaultConfig {
        applicationId = "com.brandonhc.clocksampleapp"
        minSdk = Versions.SDK_MIN_VERSION
        targetSdk = Versions.SDK_TARGET_VERSION
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

    buildFeatures {
        viewBinding = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    /** AndroidX **/
    implementation("androidx.core:core-ktx:${Versions.ANDROIDX_CORE_VERSION}")
    implementation("androidx.appcompat:appcompat:${Versions.ANDROIDX_APPCOMPAT_VERSION}")
    implementation("androidx.constraintlayout:constraintlayout:${Versions.ANDROIDX_CONSTRAINT_LAYOUT_VERSION}")

    /** Google **/
    implementation("com.google.android.material:material:${Versions.GOOGLE_MATERIAL_VERSION}")

    /** Unit Test **/
    testImplementation("junit:junit:${Versions.TEST_JUNIT_VERSION}")

    /** UI Test **/
    androidTestImplementation("androidx.test.ext:junit:${Versions.UI_TEST_JUNIT_VERSION}")
    androidTestImplementation("androidx.test.espresso:espresso-core:${Versions.UI_TEST_ESPRESSO_VERSION}")
}