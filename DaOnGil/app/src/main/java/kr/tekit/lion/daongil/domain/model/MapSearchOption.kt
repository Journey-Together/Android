package kr.tekit.lion.daongil.domain.model

import java.util.TreeSet

data class MapSearchOption(
    val category: String,
    val minX: Double,
    val maxX: Double,
    val minY: Double,
    val maxY: Double,
    val disabilityType: TreeSet<String> = TreeSet(),
    val detailFilter: Set<String> = TreeSet(),
    val arrange: String = "C"
)