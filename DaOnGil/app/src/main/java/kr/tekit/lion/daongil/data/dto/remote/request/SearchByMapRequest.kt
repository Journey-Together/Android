package kr.tekit.lion.daongil.data.dto.remote.request

import kr.tekit.lion.daongil.domain.model.MapSearchOption

data class SearchByMapRequest (
    val category: String,
    val minX: Double,
    val maxX: Double,
    val minY: Double,
    val maxY: Double,
    val disabilityType: List<String>?,
    val detailFilter: List<String>?,
    val arrange: String?
)

fun MapSearchOption.toRequestModel(): SearchByMapRequest = SearchByMapRequest(
    category = category,
    minX = minX,
    maxX = maxX,
    minY = minY,
    maxY = maxY,
    disabilityType = disabilityType.toList(),
    detailFilter = detailFilter.toList(),
    arrange = arrange
)