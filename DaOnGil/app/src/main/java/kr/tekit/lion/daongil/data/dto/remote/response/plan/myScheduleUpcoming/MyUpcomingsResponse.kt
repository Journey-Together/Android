package kr.tekit.lion.daongil.data.dto.remote.response.plan.myScheduleUpcoming

import kr.tekit.lion.daongil.domain.model.MyUpcomingScheduleInfo
import kr.tekit.lion.daongil.domain.model.MyUpcomingSchedules

data class MyUpcomingsResponse (
    val code: Int,
    val message: String,
    val data: Data,
){
    fun toDomainModel(): MyUpcomingSchedules {
        return MyUpcomingSchedules(
            myUpcomingScheduleList = this.data.planResList.map {
                MyUpcomingScheduleInfo(
                    planId = it.planId,
                    title = it.title,
                    startDate = it.startDate,
                    endDate = it.endDate,
                    imageUrl = it.imageUrl ?: "",
                    remainDate = it.remainDate
                )
            },
            last = data.last
        )
    }
}