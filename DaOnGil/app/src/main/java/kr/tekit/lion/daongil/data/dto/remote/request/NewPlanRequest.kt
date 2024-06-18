package kr.tekit.lion.daongil.data.dto.remote.request

import kr.tekit.lion.daongil.data.dto.remote.base.AdapterProvider.Companion.JsonAdapter
import kr.tekit.lion.daongil.domain.model.NewPlan
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody

data class NewPlanRequest(
    val title: String,
    val startDate: String,
    val endDate: String,
    val isPublic: Boolean = false,
    val dailyPlace: List<DailyPlaceRequest>,
)

fun NewPlan.toRequestBody(): RequestBody {
    return JsonAdapter(NewPlanRequest::class.java).toJson(
        NewPlanRequest(
            this.title,
            this.startDate,
            this.endDate,
            this.isPublic,
            this.dailyPlace.map {
                DailyPlaceRequest(
                    it.date,
                    it.places
                )
            }
        )
    ).toRequestBody("application/json".toMediaTypeOrNull())
}