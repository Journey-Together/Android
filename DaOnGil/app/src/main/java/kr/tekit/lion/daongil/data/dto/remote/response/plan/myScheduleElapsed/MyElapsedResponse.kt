package kr.tekit.lion.daongil.data.dto.remote.response.plan.myScheduleElapsed

import com.squareup.moshi.JsonClass
import kr.tekit.lion.daongil.domain.model.MyElapsedScheduleInfo
import kr.tekit.lion.daongil.domain.model.MyElapsedSchedules

@JsonClass(generateAdapter = true)
data class MyElapsedResponse(
    val code: Int,
    val message: String,
    val data: Data,
){
    fun toDomainModel(): MyElapsedSchedules {
        return MyElapsedSchedules(
            myElapsedScheduleList = data.planResList.map{
                MyElapsedScheduleInfo(
                    planId = it.planId,
                    title = it.title,
                    startDate = it.startDate,
                    endDate = it.endDate,
                    imageUrl = it.imageUrl ?: "",
                    hasReview = it.hasReview
                )
            },
            last = data.last
        )
    }
}
