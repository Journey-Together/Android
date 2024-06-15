package kr.tekit.lion.daongil.data.dto.remote.request

import kr.tekit.lion.daongil.domain.model.ListSearchOption

data class SearchByListRequest (
    val category: String,
    val size: Int,
    val page: Int,
    val query: String?,
    val disabilityType: List<String>?,
    val detailFilter: List<String>?,
    val areaCode: String?,
    val sigunguCode: String?,
    val arrange: String
)

fun ListSearchOption.toRequestModel() = SearchByListRequest(
    category = category,
    size = size,
    page = page,
    query = query,
    disabilityType = disabilityType?.toList(),
    detailFilter = detailFilter?.toList(),
    areaCode = areaCode,
    sigunguCode = sigunguCode,
    arrange = arrange
)
