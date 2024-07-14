package kr.tekit.lion.daongil.domain.model

data class MyUpcomingSchedules (
    val myUpcomingScheduleList: List<MyUpcomingScheduleInfo>,
    val pageNo: Int,
    val pageSize: Int,
    val totalPages: Int,
    val last: Boolean,
)