plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp") version Versions.KSP_PLUGIN_VERSION
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
    implementation("androidx.navigation:navigation-fragment-ktx:${Versions.ANDROIDX_NAVIGATION_VERSION}")
    implementation("androidx.navigation:navigation-ui-ktx:${Versions.ANDROIDX_NAVIGATION_VERSION}")

    /** Google **/
    implementation("com.google.android.material:material:${Versions.GOOGLE_MATERIAL_VERSION}")

    /** Third Party Library **/
    implementation("net.danlew:android.joda:${Versions.JODA_TIME_VERSION}")
    implementation("com.squareup.okhttp3:okhttp:${Versions.OKHTTP3_VERSION}")
    implementation("com.squareup.okhttp3:logging-interceptor:${Versions.OKHTTP3_VERSION}")
    implementation("com.squareup.retrofit2:retrofit:${Versions.RETROFIT_VERSION}")
    implementation("com.squareup.retrofit2:converter-moshi:${Versions.RETROFIT_VERSION}")
    implementation("com.squareup.moshi:moshi-kotlin:${Versions.MOSHI_VERSION}")
    implementation("com.squareup.moshi:moshi-adapters:${Versions.MOSHI_VERSION}")

    /** Annotation **/
    ksp("com.squareup.moshi:moshi-kotlin-codegen:${Versions.MOSHI_VERSION}")

    /** Unit Test **/
    testImplementation("junit:junit:${Versions.TEST_JUNIT_VERSION}")

    /** UI Test **/
    androidTestImplementation("androidx.test.ext:junit:${Versions.UI_TEST_JUNIT_VERSION}")
    androidTestImplementation("androidx.test.espresso:espresso-core:${Versions.UI_TEST_ESPRESSO_VERSION}")
}