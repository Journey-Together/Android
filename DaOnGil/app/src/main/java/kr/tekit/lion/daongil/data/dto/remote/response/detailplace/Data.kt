package kr.tekit.lion.daongil.data.dto.remote.response.detailplace

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Data(
    val address: String,
    val bookmarkNum: Int,
    val category: String,
    val disability: List<Int>,
    val image: String,
    val isMark: Boolean,
    val mapX: String,
    val mapY: String,
    val name: String,
    val overview: String,
    val placeId: Int,
    val reviewList: List<Int>,
    val subDisability: List<Int>
)