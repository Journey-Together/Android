package kr.tekit.lion.daongil.domain.model

data class ReviewDetail (
    val nickname: String,
    val profileImg: String,
    val content : String,
    val date : String,
    val grade : Float,
    val imageList : List<String>
)