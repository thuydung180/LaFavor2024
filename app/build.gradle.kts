

plugins {
    id("com.android.application")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.nhom5.lafavor2024"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.nhom5.lafavor2024"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
    buildFeatures {
        viewBinding = true
    }

}

dependencies {

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("com.google.firebase:firebase-firestore:24.11.1")
    implementation("com.google.firebase:firebase-auth:22.3.1")
    implementation("com.google.firebase:firebase-database:20.3.1")
    implementation("androidx.navigation:navigation-fragment:2.7.7")
    implementation("androidx.navigation:navigation-ui:2.7.7")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    implementation("androidx.cardview:cardview:1.0.0")

    implementation("me.relex:circleindicator:2.1.6")

    implementation("com.firebaseui:firebase-ui-database:8.0.0")

    implementation("com.github.bumptech.glide:glide:4.16.0")

    implementation("com.squareup.picasso:picasso:2.71828")
    implementation("com.caverock:androidsvg:1.4")
    implementation("androidx.recyclerview:recyclerview:1.3.2")
    implementation("androidx.fragment:fragment:1.6.2")

    implementation("com.github.bumptech.glide:glide:4.16.0")
    implementation("de.hdodenhof:circleimageview:3.1.0")
    implementation("com.google.firebase:firebase-storage:20.0.0")
    implementation(platform("com.google.firebase:firebase-bom:33.0.0"))
    implementation ("com.google.firebase:firebase-appcheck")





}
//configurations.all {
//    resolutionStrategy {
//        force ("com.google.firebase:firebase-auth:22.3.1")
//        force ("com.google.firebase:firebase-database:20.3.1")
//        force ("com.google.firebase:firebase-firestore:24.11.1")
//        force ("com.google.firebase:firebase-storage:20.1.0")
//        force ("com.google.firebase:firebase-crashlytics:18.3.5")
//        force ("com.google.firebase:firebase-crashlytics-ktx:18.3.5")
//        force ("com.google.firebase:firebase-crashlytics-ndk:18.3.5")
//        force ("com.google.firebase:firebase-installations:17.1.2")
//        force ("com.google.firebase:firebase-installations-ktx:17.1.2")
//    }
//}

//apply { plugin ("com.google.gms.google-services") }
