package kr.tekit.lion.daongil.domain.model

data class DailySchedule (
    val dailyIdx : Int,
    val dailyDate : String,
    val dailyPlaces : List<FormPlace>
)