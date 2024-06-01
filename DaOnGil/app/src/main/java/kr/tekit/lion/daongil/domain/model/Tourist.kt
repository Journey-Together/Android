package kr.tekit.lion.daongil.domain.model

data class Tourist (
    val touristLocation : String,
    val touristName : String,
    val touristImage : String?,
    val disabilityType : List<String>
)