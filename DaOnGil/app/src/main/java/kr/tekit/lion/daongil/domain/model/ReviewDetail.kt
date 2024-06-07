package kr.tekit.lion.daongil.domain.model

data class ReviewDetail (
    var nickname: String,
    var content : String,
    var date : String,
    var imageList : List<String>
)