package kr.tekit.lion.daongil.domain.model

data class ScheduleDetailnfo (
    val title : String,
    val startDate : String,
    val endDate : String,
    // 여행 시작일까지 남은 날짜 (이미 지난 경우 null?)
    val remainDate : String?,
    // 공개 일정 여부
    val isPublic: Boolean,
    // 본인 일정 여부
    val isWriter : Boolean,
    val nickname : String,
    val images : List<String>?,
    val dailyPlans: List<DailyPlan>,
    val writerId: Long
)