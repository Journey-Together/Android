package kr.tekit.lion.daongil.data.network

import android.util.Log
import kr.tekit.lion.daongil.data.network.service.AuthService
import kr.tekit.lion.daongil.data.network.service.PlaceService
import kr.tekit.lion.daongil.data.network.service.KorWithService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create

object RetrofitInstance {
    private val okHttpClient: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor ()
            .setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()
    }

    private val authClient: OkHttpClient by lazy {
        OkHttpClient.Builder()
            //.authenticator(AuthAuthenticator())
            .addInterceptor(AuthInterceptor())
            .addInterceptor(HttpLoggingInterceptor{
                Log.d("MyOkHttpLog", it)
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

    val placeService: PlaceService by lazy {
        Retrofit.Builder()
            .baseUrl("http://13.124.100.238/v1/place/")
            .addConverterFactory(MoshiConverterFactory.create().asLenient())
            .client(authClient)
            .build()
            .create()
    }

    val authService: AuthService by lazy {
        Retrofit.Builder()
            .baseUrl("http://13.124.100.238/v1/auth/")
            .addConverterFactory(MoshiConverterFactory.create().asLenient())
            .client(authClient)
            .build()
            .create()
    }
}