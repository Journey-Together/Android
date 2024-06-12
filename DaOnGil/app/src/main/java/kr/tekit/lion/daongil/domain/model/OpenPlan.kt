package kr.tekit.lion.daongil.domain.model


data class OpenPlan(
    val last: Boolean,
    val openPlanList: List<OpenPlanInfo>,
    val pageNo: Int,
    val totalPages: Int
)
