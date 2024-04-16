plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-parcelize")
    id("kotlin-kapt")
}

android {
    namespace = "ru.potemkin.orpheusjetpackcompose"
    compileSdk = 34

    defaultConfig {
        applicationId = "ru.potemkin.orpheusjetpackcompose"
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
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.1")
    implementation("androidx.activity:activity-compose:1.7.0")
    implementation(platform("androidx.compose:compose-bom:2023.03.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.03.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
    implementation("androidx.navigation:navigation-compose:2.6.0")
    implementation("androidx.compose.material:material-icons-extended:1.5.0-alpha04")
    implementation("androidx.compose.material:material:1.5.1")
    implementation("com.google.accompanist:accompanist-systemuicontroller:0.23.0")
    implementation( "com.google.accompanist:accompanist-pager:0.23.0")
    implementation ("androidx.compose.runtime:runtime-livedata:1.5.4")
    implementation ("com.google.android.material:material:1.12.0-alpha03")
    implementation ("androidx.compose.material3:material3:1.3.0-alpha01")
    implementation ("io.coil-kt:coil-compose:2.1.0")
    implementation ("com.google.accompanist:accompanist-systemuicontroller:0.27.0")
    implementation ("androidx.core:core-ktx:1.1.0")
    //di
    implementation ("com.google.dagger:dagger:2.43.2")
    kapt ("com.google.dagger:dagger-compiler:2.43.2")
    implementation("androidx.security:security-crypto:1.1.0-alpha04")

    //network
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation ("com.squareup.okhttp3:logging-interceptor:4.9.3")

    implementation ("androidx.compose.runtime:runtime-livedata:1.5.4")

    implementation ("io.coil-kt:coil-compose:2.1.0")

    //Preferences DataStore
    implementation ("androidx.datastore:datastore-preferences:1.0.0-alpha05")

    //Map
    implementation ("com.google.maps.android:maps-compose:2.11.4")
    implementation ("com.google.android.gms:play-services-maps:18.1.0")
    implementation ("com.google.android.gms:play-services-location:18.0.0")
    implementation ("com.google.accompanist:accompanist-permissions:0.15.0")

    //Calendar
    implementation ("io.github.boguszpawlowski.composecalendar:composecalendar:1.2.0")

    //Lottie
    implementation("com.airbnb.android:lottie-compose:6.0.0")

}