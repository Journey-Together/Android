package kr.tekit.lion.daongil.domain.model

data class MyUpcomingSchedules (
    val myUpcomingScheduleList: List<MyUpcomingScheduleInfo>,
    val last: Boolean,
)