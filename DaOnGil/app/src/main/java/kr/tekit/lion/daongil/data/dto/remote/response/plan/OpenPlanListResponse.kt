package kr.tekit.lion.daongil.data.dto.remote.response.plan


import com.squareup.moshi.JsonClass
import kr.tekit.lion.daongil.domain.model.OpenPlan
import kr.tekit.lion.daongil.domain.model.OpenPlanInfo

@JsonClass(generateAdapter = true)
data class OpenPlanListResponse(
    val last: Boolean,
    val openPlanResList: List<OpenPlanRes>,
    val pageNo: Int,
    val pageSize: Int,
    val totalPages: Int
) {
    fun toDomainModel() : OpenPlan {
        return OpenPlan(
            last = this.last,
            pageNo = this.pageNo,
            totalPages = this.totalPages,
            openPlanList = this.openPlanResList.map {
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