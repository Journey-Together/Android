package kr.tekit.lion.daongil.domain.model

data class RecommendPlace (
    val address: String,
    val disability: List<String>,
    val image: String,
    val name: String,
    val placeId: Long
)