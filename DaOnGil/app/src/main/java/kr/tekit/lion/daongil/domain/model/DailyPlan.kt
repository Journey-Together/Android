package kr.tekit.lion.daongil.domain.model

data class DailyPlan(
    val dailyPlanDate: Int,
    val schedulePlaces : List<SchedulePlace?>
)