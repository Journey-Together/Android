package kr.tekit.lion.daongil.domain.model

data class PlaceSearchResult (
    val placeInfoList: List<PlaceSearchInfo>,
    val pageNo: Int,
    val pageSize: Int,
    val totalPages: Int,
    val last: Boolean,
)