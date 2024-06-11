package kr.tekit.lion.daongil.data.dto.remote.request

import kr.tekit.lion.daongil.domain.model.SearchOption

fun SearchOption.toRequestModel(): Map<String, String?> {
    return mapOf(
        "category" to category,
        "query" to query,
        "size" to size,
        "page" to page,
        "minX" to minX,
        "maxX" to maxX,
        "minY" to minY,
        "maxY" to maxY,
        "disabilityType" to disabilityType,
        "detailFilter" to detailFilter,
        "areacode" to areaCode,
        "sigungucode" to sigunguCode,
        "arrange" to arrange
    )
}