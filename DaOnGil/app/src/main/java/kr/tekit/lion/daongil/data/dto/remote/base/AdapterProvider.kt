package kr.tekit.lion.daongil.data.dto.remote.base

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

class AdapterProvider {
    companion object {
        fun <T> JsonAdapter(requestModel: Class<T>): JsonAdapter<T> {
            val moshi = Moshi.Builder()
                .add(KotlinJsonAdapterFactory())
                .build()

            return moshi.adapter(requestModel)
        }
    }
}
