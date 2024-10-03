import org.jetbrains.kotlin.kapt3.base.Kapt.kapt

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.gms.google-services")
    id("kotlin-android")
    id("kotlin-kapt")
    id("com.google.firebase.crashlytics")
    id("com.google.dagger.hilt.android")
    //id("androidx.navigation.safeargs.kotlin")

}

android {
    namespace = "com.android.mitcontaskmanagement"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.android.mitcontaskmanagement"
        minSdk = 29
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }
    buildFeatures{
        viewBinding = true
        dataBinding= true
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

configurations {
    all {
        exclude(module="guava-jdk5")
        exclude(group = "com.google.guava", module = "listenablefuture")
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")
    implementation("androidx.activity:activity-compose:1.9.0")
    implementation(platform("androidx.compose:compose-bom:2024.05.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2024.05.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    // copied
    implementation("com.google.android.gms:play-services-auth:21.1.0")

    testImplementation("junit:junit:4.13.2")
    //var lifecycle_version = "2.7.0"
    implementation("androidx.datastore:datastore-preferences:1.1.1")
    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.legacy:legacy-support-v4:1.0.0")
    /* coroutines support for firebase operations */
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-play-services:1.7.3")

// ViewModel
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0")

// LiveData
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.7.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")

// optional - helpers for implementing LifecycleOwner in a Service
    implementation("androidx.lifecycle:lifecycle-service:2.7.0")
    implementation(platform("com.google.firebase:firebase-bom:28.1.0"))
    implementation("com.google.firebase:firebase-crashlytics-ktx")
    implementation("com.google.firebase:firebase-analytics-ktx")
    implementation("com.google.firebase:firebase-firestore-ktx")
    implementation("com.google.firebase:firebase-storage-ktx")
    implementation("com.google.firebase:firebase-auth-ktx")
    implementation("com.airbnb.android:lottie:6.4.0")
    implementation("com.google.dagger:hilt-android:2.44")
    kapt("com.google.dagger:hilt-android-compiler:2.44")

    // Allow references to generated code
    implementation("androidx.hilt:hilt-navigation-fragment:1.2.0")
    kapt("androidx.hilt:hilt-compiler:1.2.0")
//navigation

    val nav_version = "2.7.7"
    implementation("androidx.navigation:navigation-fragment-ktx:$nav_version")
    implementation("androidx.navigation:navigation-ui-ktx:$nav_version")

//timber
    implementation("com.jakewharton.timber:timber:5.0.1")

//coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")
    implementation("com.github.bumptech.glide:glide:4.16.0")
    kapt("com.github.bumptech.glide:compiler:4.14.1")
    implementation("com.google.code.gson:gson:2.10.1")
    implementation (platform ("com.google.firebase:firebase-bom:28.3.0"))
    implementation("com.google.firebase:firebase-analytics")
    implementation("nl.psdcompany:duo-navigation-drawer:3.0.0")
    val room_version = "2.6.1"
    implementation("androidx.room:room-runtime:$room_version")
    kapt("androidx.room:room-compiler:$room_version")

// optional - Kotlin Extensions and Coroutines support for Room
    implementation("androidx.room:room-ktx:$room_version")
    implementation("io.coil-kt:coil:2.6.0")
    implementation("com.squareup.retrofit2:retrofit:2.11.0")
    implementation("com.squareup.retrofit2:converter-gson:2.11.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.9.1")
    implementation("de.hdodenhof:circleimageview:3.1.0")
    implementation("com.mikhaellopez:circularprogressbar:3.1.0")
    implementation("com.github.beksomega:loopinglayout:0.5.0")
    implementation("com.diogobernardino:williamchart:3.11.0")

//dots
    implementation("com.tbuonomo:dotsindicator:5.0")

 // bottom navigation
    implementation ("com.google.android.material:material:1.3.0-alpha03")
    // adding support for sdp for all layout
    implementation("com.intuit.sdp:sdp-android:1.0.6")
    implementation ("com.wdullaer:materialdatetimepicker:4.2.3")

    //Navigation Architecture Component
    implementation ("androidx.navigation:navigation-fragment-ktx:$nav_version")
    implementation ("androidx.navigation:navigation-ui-ktx:$nav_version")
    implementation ("com.github.PratikFagadiya:AnimatedSmoothBottomNavigation-JetpackCompose:1.1.1")
    implementation ("com.github.ibrahimsn98:SmoothBottomBar:1.7.9")

    //google calender

    implementation ("com.google.oauth-client:google-oauth-client-jetty:1.23.0")
    implementation ("com.google.apis:google-api-services-calendar:v3-rev305-1.23.0")
    implementation ("com.google.guava:listenablefuture:9999.0-empty-to-avoid-conflict-with-guava")
    implementation ("com.google.guava:guava:28.1-android")
    implementation ("com.google.api-client:google-api-client-android:1.23.0") {
        exclude(group="org.apache.httpcomponents")
    }

    //so that we can easily control permissions
    implementation ("pub.devrel:easypermissions:3.0.0")

}
