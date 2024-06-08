package kr.tekit.lion.daongil.data.network

import android.util.Log
import com.squareup.moshi.KotlinJsonAdapterFactory
import com.squareup.moshi.Moshi
import kr.tekit.lion.daongil.BuildConfig
import kr.tekit.lion.daongil.data.dto.remote.response.emergency.realtime.EmergencyRealtimeJsonAdapter
import kr.tekit.lion.daongil.data.network.service.EmergencyService
import kr.tekit.lion.daongil.data.network.service.PlaceService
import kr.tekit.lion.daongil.data.network.service.KorWithService
import kr.tekit.lion.daongil.data.network.service.NaverMapService
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

    private val naverMapClient: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .addHeader("X-NCP-APIGW-API-KEY-ID", BuildConfig.NAVER_MAP_ID)
                    .addHeader("X-NCP-APIGW-API-KEY", BuildConfig.NAVER_MAP_SECRET)
                    .build()
                chain.proceed(request)
            }
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



    val naverMapService: NaverMapService by lazy {
        Retrofit.Builder()
            .baseUrl(BuildConfig.NAVER_MAP_BASE)
            .addConverterFactory(MoshiConverterFactory.create(
                Moshi.Builder()
                .build()))
            .client(naverMapClient)
            .build()
            .create(NaverMapService::class.java)
    }

    private val emergencyMoshi = Moshi.Builder()
        .add(EmergencyRealtimeJsonAdapter())
        .add(KotlinJsonAdapterFactory())
        .build()

    val emergencyService: EmergencyService by lazy {
        Retrofit.Builder()
            .baseUrl(BuildConfig.EMERGENCY_BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(emergencyMoshi))
            .client(okHttpClient)
            .build()
            .create(EmergencyService::class.java)
    }


}