package kr.tekit.lion.daongil.network

import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object RetrofitInstance {

    private val okHttpClient: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor { message ->
                Log.e("MyOkHttpClient :", message + "")
            }.setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()
    }

    private fun provideRetrofit(baseUrl: String): Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(MoshiConverterFactory.create().asLenient())
        .client(okHttpClient)
        .build()

    fun <T> provideService(baseUrl: String, apiService: Class<T>): T {
        return provideRetrofit(baseUrl).create(apiService)
    }
}