package kr.tekit.lion.daongil.data.network

import android.util.Log
import kr.tekit.lion.daongil.data.network.service.KorWithService
import kr.tekit.lion.daongil.data.network.service.LoginService
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

    val loginService: LoginService by lazy {
        Retrofit.Builder()
            .baseUrl("http://13.124.100.238/") // API URL
            .addConverterFactory(MoshiConverterFactory.create().asLenient())
            .client(headerClient)
            .build()
            .create()
    }

    private val headerClient: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .addHeader("Content-Type", "application/json")
                    .build()

                chain.proceed(request)
            }
            .addInterceptor(HttpLoggingInterceptor{
                Log.d("MyOkHttpLog", it)
            }.setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()
    }

}