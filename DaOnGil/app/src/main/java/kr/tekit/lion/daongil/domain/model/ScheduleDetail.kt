package kr.tekit.lion.daongil.domain.model

data class ScheduleDetail (
    val scheduleIdx : Int,
    val title : String,
    val startDate : String,
    val endDate : String,
    // 여행 시작일까지 남은 날짜 (이미 지난 경우 null?)
    val daysRemaining : Int?,
    // 공개 일정 여부
    var isPublic: Boolean,
    // 본인 일정 여부
    val isWriter : Boolean,
    val nickname : String,
    val images : List<String>,
    val dailyPlans: List<DailyPlan>,
    // 리뷰 Index
    val reviewIdx : Int?,
    val review : String?,
    val rating : Long?
)