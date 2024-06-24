package kr.tekit.lion.daongil.domain.model

data class NewPlan(
    val title: String,
    val startDate: String,
    val endDate: String,
    val dailyPlace: List<DailyPlace>,
)
