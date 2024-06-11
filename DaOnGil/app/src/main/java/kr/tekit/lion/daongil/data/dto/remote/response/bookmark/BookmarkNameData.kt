package kr.tekit.lion.daongil.data.dto.remote.response.bookmark

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class BookmarkNameData(
    val placeId : Long,
    val placeName : String
)
