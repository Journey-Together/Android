package kr.tekit.lion.daongil.data.dto.remote.request

data class DailyPlaceRequest(
    val date: String,
    val places: List<Long?>,
)