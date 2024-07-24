package kr.tekit.lion.daongil.domain.model

sealed class PlaceSearchInfoList {
    data class PlaceSearchInfo (
        val placeId: Long,
        val placeName: String,
        val category: String,
        val imageUrl: String?
    ): PlaceSearchInfoList()

    data class TotalElementsInfo (
        val totalElements: Long
    ): PlaceSearchInfoList()
}