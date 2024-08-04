package kr.tekit.lion.daongil.data.dto.remote.response.plan.scheduleDetail

import kr.tekit.lion.daongil.domain.model.ScheduleDetailInfo

data class ScheduleDetailResponse(
    val code: Int,
    val data: Data,
    val message: String
) {
    fun toDomainModel(): ScheduleDetailInfo {
        return data.let{
            ScheduleDetailInfo(
                title = it.title,
                startDate = it.startDate,
                endDate = it.endDate,
                remainDate = it.remainDate,
                isPublic = it.isPublic,
                isWriter = it.isWriter,
                nickname = it.writerNickname,
                images = it.imageUrls,
                writerId = it.writerId,
                dailyPlans = it.dailyList.map { it.toDomainModel() }
            )
        }
    }
}