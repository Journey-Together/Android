package kr.tekit.lion.daongil.domain.model

data class DailyPlan(
    val dailyPlanDate: String,
    val schedulePlaces : List<SchedulePlace>
)