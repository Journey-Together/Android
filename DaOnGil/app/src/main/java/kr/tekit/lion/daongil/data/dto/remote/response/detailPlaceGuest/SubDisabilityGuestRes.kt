package kr.tekit.lion.daongil.data.dto.remote.response.detailPlaceGuest

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SubDisabilityGuestRes (
    val description: String?,
    val subDisabilityName: String
)