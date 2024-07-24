package kr.tekit.lion.daongil.data.dto.remote.response.plan.scheduleDetail

import kr.tekit.lion.daongil.domain.model.DailyPlan

data class Daily(
    val dailyPlaceInfoList: List<DailyPlaceInfo>?,
    val date: String
){
    fun toDomainModel(): DailyPlan{
        return DailyPlan(
            dailyPlanDate = date,
            schedulePlaces = dailyPlaceInfoList?.map { it.toDomainModel() } ?: emptyList()
        )
    }
}