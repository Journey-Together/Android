package kr.tekit.lion.daongil.data.dto.remote.response.plan

data class Data(
    val reviewId: Long?,
    val content: String?,
    val grade: Double?,
    val imageList: List<String>?,
    val isWriter: Boolean,
    val hasReview: Boolean,
    val profileUrl: String
)