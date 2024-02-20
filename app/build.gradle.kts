plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.clevertap.demo"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.clevertap.demo"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures{
        viewBinding = true
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
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

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")



    //Added for cleverTap
    implementation ("com.clevertap.android:clevertap-android-sdk:5.0.0") // checkout the latest sdk version at https://github.com/CleverTap/clevertap-android-sdk/blob/master/README.md#-installation
    implementation ("com.google.firebase:firebase-messaging:23.3.1")
    //Mandatory for CleverTap Android SDK v3.6.4 and above add the following -
    implementation ("com.android.installreferrer:installreferrer:2.2")


    //for google Ads
    implementation ("com.google.android.gms:play-services-ads:22.6.0")

    //firebase
    implementation(platform("com.google.firebase:firebase-bom:32.7.0"))
    implementation("com.google.firebase:firebase-analytics")



    //MANDATORY for App Inbox

    implementation("androidx.recyclerview:recyclerview:1.3.2")
    implementation("androidx.viewpager:viewpager:1.0.0")
    implementation("com.github.bumptech.glide:glide:4.12.0")

    //push template SDK
    implementation("com.clevertap.android:push-templates:1.0.9")


}

