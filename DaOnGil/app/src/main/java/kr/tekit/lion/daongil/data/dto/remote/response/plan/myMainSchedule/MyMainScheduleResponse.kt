package kr.tekit.lion.daongil.data.dto.remote.response.plan.myMainSchedule

import com.squareup.moshi.JsonClass
import kr.tekit.lion.daongil.domain.model.MyMainSchedule

@JsonClass(generateAdapter = true)
data class MyMainScheduleResponse(
    val code: Int,
    val data: List<Data?>?,
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