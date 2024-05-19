package kr.tekit.lion.daongil.dto.response.searchkeyword

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Items(
    val item: List<Item>
)