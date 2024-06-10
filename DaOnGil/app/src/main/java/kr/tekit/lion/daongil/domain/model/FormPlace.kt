package kr.tekit.lion.daongil.domain.model

data class FormPlace (
    val placeId : Int,
    val placeImage : String, // 사진 url
    val placeAddress : String, // 주소
    val placeName : String, // 장소명
    // 무장애 유형 정보
    // 1 (지체 장애), 2 (시각 장애), 3 (청각 장애), 4 (유아 동반), 5 (고령자)
    val placeDisability : List<Int>
)