package kr.tekit.lion.daongil.data.dto.remote.response.emergency.realtime
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Body(
    val items: Items,
    val numOfRows: Int,
    val pageNo: Int,
    val totalCount: Int
)