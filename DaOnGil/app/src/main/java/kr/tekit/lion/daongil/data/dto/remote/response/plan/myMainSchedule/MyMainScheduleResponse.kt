package kr.tekit.lion.daongil.data.dto.remote.response.plan.myMainSchedule


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kr.tekit.lion.daongil.domain.model.MyMainSchedule

@JsonClass(generateAdapter = true)
data class MyMainScheduleResponse(
    @Json(name = "code")
    val code: Int,
    @Json(name = "data")
    val data: List<Data?>?,
    @Json(name = "message")
    val message: String
) {
    fun toDomainModel(): List<MyMainSchedule?>? {
        return data?.map{
            MyMainSchedule(
                endDate = it?.endDate,
                hasReview = it?.hasReview,
                imageUrl = it?.imageUrl,
                planId = it?.planId,
                remainDate = it?.remainDate,
                startDate = it?.startDate,
                title = it?.title
            )
        }
    }
}