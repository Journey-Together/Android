package kr.tekit.lion.daongil.domain.model

data class PhysicalDisabilityTouristSpot (
    val common: TouristSpotCommon,
    val parking: String,
    val route: String,
    val publicTransport: String,
    val ticketOffice: String,
    val promotion: String,
    val wheelchair: String,
    val exit: String,
    val elevator: String,
    val restroom: String,
    val auditorium: String,
    val room: String,
    val etc: String
)