package kr.tekit.lion.daongil.data.dto.remote.response.plan.briefScheduleInfo

import com.squareup.moshi.JsonClass
import kr.tekit.lion.daongil.domain.model.BriefScheduleInfo

@JsonClass(generateAdapter = true)
data class BriefScheduleInfoResponse(
    val code: Int,
    val message: String,
    val data: Data?
){
    fun toDomainModel() : BriefScheduleInfo {
        return BriefScheduleInfo(
            planId = this.data?.planId,
            title = this.data?.title,
            startDate = this.data?.startDate,
            endDate = this.data?.endDate,
            imageUrl = this.data?.imageUrl
        )
    }
}
