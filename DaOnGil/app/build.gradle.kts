import java.util.Properties

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-parcelize")
    id("kotlin-kapt")
    id("androidx.navigation.safeargs.kotlin")
    id("kotlinx-serialization")
}

val properties = Properties()
properties.load(project.rootProject.file("local.properties").inputStream())
val kakaoApiKey = properties.getProperty("kakao_api_key") ?: ""
val kakaoNativeKey = properties.getProperty("kakao_native_key") ?: ""
val baseUrl = properties.getProperty("base_url") ?: ""
val naverMapBase = properties.getProperty("naver_map_base") ?: ""
val naverMapId = properties.getProperty("naver_map_id") ?: ""
val naverMapSecret = properties.getProperty("naver_map_secret") ?: ""
val naverClientId = properties.getProperty("naverClientId") ?: ""
val naverClientSecret = properties.getProperty("naverClientSecret") ?: ""
val naverClientName = properties.getProperty("naverClientName") ?: ""

android {
    namespace = "kr.tekit.lion.daongil"
    compileSdk = 34

    defaultConfig {
        applicationId = "kr.tekit.lion.daongil"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        // buildConfigField 메서드를 올바르게 호출합니다.
        buildConfigField("String", "NAVER_MAP_BASE", "\"$naverMapBase\"")
        buildConfigField("String", "NAVER_MAP_ID", "\"$naverMapId\"")
        buildConfigField("String", "NAVER_MAP_SECRET", "\"$naverMapSecret\"")
        buildConfigField("String", "KAKAO_API_KEY", "\"$kakaoApiKey\"")
        buildConfigField("String", "NAVER_CLIENT_ID", "\"$naverClientId\"")
        buildConfigField("String", "NAVER_CLIENT_SECRET", "\"$naverClientSecret\"")
        buildConfigField("String", "NAVER_CLIENT_NAME", "\"$naverClientName\"")
        buildConfigField("String", "KAKAO_NATIVE_KEY", "\"$kakaoNativeKey\"")
        buildConfigField("String", "BASE_URL", "\"$baseUrl\"")

        //manifest에서 사용
        manifestPlaceholders["KAKAO_NATIVE_KEY"] = kakaoNativeKey
        manifestPlaceholders["NAVER_MAP_ID"] = naverMapId
    }

    kapt {
        arguments {
            arg("room.schemaLocation", "$projectDir/schemas")
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    viewBinding {
        enable = true
    }
    buildFeatures {
        buildConfig = true
    }

}

dependencies {

    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.activity:activity:1.8.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    implementation("com.squareup.retrofit2:retrofit:2.11.0")
    implementation("com.squareup.retrofit2:converter-moshi:2.11.0")

    implementation("com.squareup.moshi:moshi:1.15.1")
    implementation("com.squareup.moshi:moshi-kotlin:1.15.1")
    kapt("com.squareup.moshi:moshi-kotlin-codegen:1.15.1")
    implementation("com.squareup.okhttp3:logging-interceptor:4.10.0")

    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0")
    implementation("androidx.fragment:fragment-ktx:1.7.1")

    val roomVersion = "2.6.1"

    implementation("androidx.room:room-runtime:$roomVersion")
    kapt("androidx.room:room-compiler:$roomVersion")

    implementation("androidx.room:room-ktx:$roomVersion")
    implementation("com.google.code.gson:gson:2.10.1")

    implementation("androidx.navigation:navigation-fragment-ktx:2.7.7")
    implementation("androidx.navigation:navigation-ui-ktx:2.7.7")

    implementation("com.naver.maps:map-sdk:3.17.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-jvm:1.7.3")

    implementation("me.relex:circleindicator:2.1.6")

    implementation("com.kakao.sdk:v2-all:2.20.1") // 전체 모듈 설치, 2.11.0 버전부터 지원
    implementation("com.kakao.sdk:v2-user:2.20.1") // 카카오 로그인 API
    implementation("com.google.android.gms:play-services-location:21.3.0")


    implementation("com.github.ome450901:SimpleRatingBar:1.5.1")
    implementation("pl.droidsonroids.gif:android-gif-drawable:1.2.28")
    implementation("com.github.bumptech.glide:glide:4.16.0")


    implementation("com.navercorp.nid:oauth:5.9.1") // jdk 11
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.3")
    implementation("androidx.datastore:datastore-preferences:1.1.1")
    implementation("androidx.core:core-splashscreen:1.0.1")
    implementation("com.github.bumptech.glide:glide:4.16.0")
    annotationProcessor("com.github.bumptech.glide:compiler:4.13.2")
}