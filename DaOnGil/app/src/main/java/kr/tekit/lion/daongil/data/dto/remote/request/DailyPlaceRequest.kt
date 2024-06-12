package kr.tekit.lion.daongil.data.dto.remote.request

import kr.tekit.lion.daongil.data.dto.remote.base.AdapterProvider
import kr.tekit.lion.daongil.domain.model.DailyPlace
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody

data class DailyPlaceRequest(
    val date: String,
    val places: List<Long?>,
)
fun DailyPlace.toRequestBody(): RequestBody {
    return AdapterProvider.JsonAdapter(DailyPlaceRequest::class.java).toJson(
        DailyPlaceRequest(
            this.date,
            this.places,
            )
    ).toRequestBody("application/json".toMediaTypeOrNull())
}