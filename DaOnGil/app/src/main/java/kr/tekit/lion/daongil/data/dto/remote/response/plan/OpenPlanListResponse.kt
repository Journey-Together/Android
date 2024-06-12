package kr.tekit.lion.daongil.data.dto.remote.response.plan


import com.squareup.moshi.JsonClass
import kr.tekit.lion.daongil.domain.model.OpenPlan
import kr.tekit.lion.daongil.domain.model.OpenPlanInfo

@JsonClass(generateAdapter = true)
data class OpenPlanListResponse(
    val code: Int,
    val data: Data,
    val message: String
) {
    fun toDomainModel() : OpenPlan {
        return OpenPlan(
            last = this.data.last,
            pageNo = this.data.pageNo,
            totalPages = this.data.totalPages,
            openPlanList = this.data.openPlanResList.map {
                OpenPlanInfo(
                    date = it.date,
                    imageUrl = it.imageUrl,
                    memberId = it.memberId,
                    memberImageUrl = it.memberImageUrl,
                    memberNickname = it.memberNickname,
                    planId = it.planId,
                    title = it.title
                )
            }
        )
    }
}