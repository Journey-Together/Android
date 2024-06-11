package kr.tekit.lion.daongil.domain.model

data class SearchOption (
    val category: String,
    val query: String,
    val size: String,
    val page: String,
    val minX: String?,
    val maxX: String?,
    val minY: String?,
    val maxY: String?,
    val disabilityType: String?,
    val detailFilter: String?,
    val areaCode: String?,
    val sigunguCode: String,
    val arrange: String?
)