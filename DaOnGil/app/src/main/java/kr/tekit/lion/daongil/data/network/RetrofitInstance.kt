package kr.tekit.lion.daongil.data.network

import android.util.Log
import kr.tekit.lion.daongil.BuildConfig
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

    private fun retrofitProvider(): Retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create())
        .client(authClient)
        .build()

    fun <T> serviceProvider(apiService: Class<T>): T {
        return retrofitProvider().create(apiService)
    }
}