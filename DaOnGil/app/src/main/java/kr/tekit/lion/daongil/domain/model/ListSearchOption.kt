package kr.tekit.lion.daongil.domain.model

import java.util.TreeSet

data class ListSearchOption (
    val category: String,
    val query: String,
    val size: Int,
    val page: Int,
    val minX: Double? = 0.0,
    val maxX: Double?= 0.0,
    val minY: Double?= 0.0,
    val maxY: Double?= 0.0,
    val disabilityType: TreeSet<String> = TreeSet(),
    val detailFilter: Set<String> = TreeSet(),
    val areaCode: String ?= "",
    val sigunguCode: String? = "",
    val arrange: String = "C"
)