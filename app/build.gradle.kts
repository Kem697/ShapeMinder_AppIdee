plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.gms.google-services")
    id ("kotlin-kapt")
    kotlin("plugin.serialization").version("1.9.23")
    id("androidx.navigation.safeargs.kotlin")
}

val apiKey: String = com.android.build.gradle.internal.cxx.configure.gradleLocalProperties(rootDir).getProperty("apiKey")

android {

    namespace = "de.kem697.shapeminder"
    compileSdk = 34



    defaultConfig {
        applicationId = "de.kem697.shapeminder"
        minSdk = 26
        targetSdk = 34
        versionCode = 2
        versionName = "1.1"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        viewBinding = true
        buildConfig = true
    }

    buildTypes {
        release {
            buildConfigField("String","apiKey",apiKey)
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        debug {
            buildConfigField("String","apiKey",apiKey)
        }
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

    implementation("com.google.android.gms:play-services-fitness:21.1.0")
    val retrofitVersion = "2.9.0"
    val roomVersion = "2.6.0"

    val kotlin_version = "1.9.23"


    //YoutubePlayer
    implementation("com.pierfrancescosoffritti.androidyoutubeplayer:core:12.1.0")

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.navigation:navigation-fragment-ktx:2.7.7")
    implementation("androidx.navigation:navigation-ui-ktx:2.7.7")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    //Retrofit und Moshi
    implementation("com.squareup.retrofit2:retrofit:$retrofitVersion")
    implementation("com.squareup.retrofit2:converter-moshi:$retrofitVersion")
    implementation("com.squareup.moshi:moshi-kotlin:1.15.0")
    implementation ("com.squareup.retrofit2:converter-scalars:2.1.0")
    kapt("com.squareup.moshi:moshi-kotlin-codegen:1.8.0")



    // Coil
    implementation("io.coil-kt:coil:2.5.0")

    //Room
    implementation("androidx.room:room-runtime:$roomVersion")
    kapt("androidx.room:room-compiler:$roomVersion")
    implementation("androidx.room:room-ktx:$roomVersion")

    //Firebase
    implementation("com.google.firebase:firebase-analytics")
    implementation("com.google.firebase:firebase-storage-ktx:20.3.0")
    implementation("com.google.firebase:firebase-firestore-ktx:24.11.0")
    implementation("com.google.firebase:firebase-auth:22.3.1")
    implementation(platform("com.google.firebase:firebase-bom:33.0.0"))

    implementation("com.google.android.gms:play-services-auth:21.1.1")




    //Serialization
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.0")


    //Loggin
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")



    //Barcode scanning libary ZXing for decoding.
    implementation ("com.journeyapps:zxing-android-embedded:4.3.0")


    //Google Maps API
    implementation ("com.google.maps.android:android-maps-utils:3.8.0")



    implementation ("androidx.credentials:credentials:<latest version>")
    implementation ("androidx.credentials:credentials-play-services-auth:<latest version>")
    implementation ("com.google.android.libraries.identity.googleid:googleid:<latest version>")


    //Google Places SDK
    implementation(platform("org.jetbrains.kotlin:kotlin-bom:$kotlin_version"))
    implementation("com.google.android.libraries.places:places:3.4.0")






    //Protobuf

    implementation ("com.google.protobuf:protobuf-javalite:3.22.3")
    implementation ("com.google.protobuf:protobuf-kotlin-lite:3.20.1")










}








