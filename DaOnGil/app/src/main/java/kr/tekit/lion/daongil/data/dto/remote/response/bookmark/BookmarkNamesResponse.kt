package kr.tekit.lion.daongil.data.dto.remote.response.bookmark

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class BookmarkNamesResponse (
    val code: Int,
    val message: String,
    val data: List<BookmarkNameData>
){
    fun toDomainModel(): List<BookmarkNameData> {
        return data.map { bookmarkNameData ->
            BookmarkNameData(
                placeId = bookmarkNameData.placeId,
                placeName = bookmarkNameData.placeName
            )

        }
    }
}