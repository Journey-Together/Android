package kr.tekit.lion.daongil.domain.model

data class MyElapsedSchedules (
    val myElapsedScheduleList: List<MyElapsedScheduleInfo>,
    val last: Boolean,
)