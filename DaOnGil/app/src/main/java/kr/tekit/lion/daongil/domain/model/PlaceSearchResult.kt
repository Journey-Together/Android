package kr.tekit.lion.daongil.domain.model

import kr.tekit.lion.daongil.domain.model.PlaceSearchInfoList as Result
data class PlaceSearchResult (
    val placeInfoList: List<Result.PlaceSearchInfo>,
    val pageNo: Int,
//    val pageSize: Int,
//    val totalPages: Int,
    val last: Boolean,
    val totalElements: Result.TotalElementsInfo
)