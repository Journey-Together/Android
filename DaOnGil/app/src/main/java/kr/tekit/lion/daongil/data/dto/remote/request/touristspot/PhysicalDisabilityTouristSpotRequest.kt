package kr.tekit.lion.daongil.data.dto.remote.request.touristspot

import com.squareup.moshi.JsonClass
import kr.tekit.lion.daongil.domain.model.PhysicalDisabilityTouristSpot

@JsonClass(generateAdapter = true)
data class PhysicalDisabilityTouristSpotRequest (
    val common: TouristSpotCommonRequest,
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

fun PhysicalDisabilityTouristSpot.toRequestModel(): PhysicalDisabilityTouristSpotRequest {
    return PhysicalDisabilityTouristSpotRequest(
        common.toRequestModel(),
        auditorium,
        route,
        publicTransport,
        ticketOffice,
        promotion,
        wheelchair,
        exit,
        elevator,
        restroom,
        auditorium,
        room,
        etc
    )
}