package kr.tekit.lion.daongil.domain.model

data class MyElapsedSchedules (
    val myElapsedScheduleList: List<MyElapsedScheduleInfo>,
    val pageNo: Int,
    val pageSize: Int,
    val totalPages: Int,
    val last: Boolean,
)