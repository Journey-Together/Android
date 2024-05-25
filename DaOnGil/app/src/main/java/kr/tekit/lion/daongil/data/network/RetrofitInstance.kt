package kr.tekit.lion.daongil.data.network

import android.util.Log
import kr.tekit.lion.daongil.data.network.service.FirebaseService
import kr.tekit.lion.daongil.data.network.service.KorWithService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create

object RetrofitInstance {

    private val okHttpClient: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor { message ->
                Log.e("MyOkHttpClient :", message + "")
            }.setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()
    }

    val korWithService: KorWithService by lazy {
        Retrofit.Builder()
            .baseUrl("https://apis.data.go.kr/B551011/KorWithService1/")
            .addConverterFactory(MoshiConverterFactory.create().asLenient())
            .client(okHttpClient)
            .build()
            .create()
    }

    val firebaseService: FirebaseService by lazy {
        Retrofit.Builder()
            .baseUrl("https://asia-northeast3-daongil-a293d.cloudfunctions.net/DaOnGileAPI/")
            .addConverterFactory(MoshiConverterFactory.create().asLenient())
            .client(okHttpClient)
            .build()
            .create(FirebaseService::class.java)
    }
}