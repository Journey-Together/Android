package kr.tekit.lion.daongil.domain.model

import java.util.TreeSet

data class ListSearchOption (
    val category: String,
    val size: Int,
    val page: Int,
    val query: String? = null,
    val disabilityType: TreeSet<String>? = null,
    val detailFilter: Set<String>? = null,
    val areaCode: String? = null,
    val sigunguCode: String? = null,
    val arrange: String = "B"
)