package kr.tekit.lion.daongil.data.dto.remote.response.emergency.realtime
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Items(
    val item: List<Item>
)