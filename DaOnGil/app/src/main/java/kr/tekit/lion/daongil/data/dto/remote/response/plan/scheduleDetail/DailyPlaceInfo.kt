package kr.tekit.lion.daongil.data.dto.remote.response.plan.scheduleDetail

import kr.tekit.lion.daongil.domain.model.SchedulePlace

data class DailyPlaceInfo(
    val category: String,
    val imageUrl: String?,
    val disabilityCategoryList: List<Int>,
    val name: String,
    val placeId: Long
){
    fun toDomainModel(): SchedulePlace {
        return SchedulePlace(
            placeId = placeId,
            name = name,
            category = category,
            imageUrl = imageUrl ?: "",
            disability = disabilityCategoryList
        )
    }
}