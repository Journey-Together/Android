package kr.tekit.lion.daongil.data.dto.remote.response.barrierfree


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Item(
    @Json(name = "audioguide")
    val audioGuide: String,
    @Json(name = "auditorium")
    val auditorium: String,
    @Json(name = "babysparechair")
    val babySpareChair: String,
    @Json(name = "bigprint")
    val bigPrint: String,
    @Json(name = "blindhandicapetc")
    val blindHandicapEtc: String,
    @Json(name = "braileblock")
    val braileBlock: String,
    @Json(name = "brailepromotion")
    val brailePromotion: String,
    @Json(name = "contentid")
    val contentId: String,
    @Json(name = "elevator")
    val elevator: String,
    @Json(name = "exit")
    val exit: String,
    @Json(name = "guidehuman")
    val guideHuman: String,
    @Json(name = "guidesystem")
    val guideSystem: String,
    @Json(name = "handicapetc")
    val handicapEtc: String,
    @Json(name = "hearinghandicapetc")
    val hearingHandicapEtc: String,
    @Json(name = "hearingroom")
    val hearingRoom: String,
    @Json(name = "helpdog")
    val helpDog: String,
    @Json(name = "infantsfamilyetc")
    val infantsFamilyEtc: String,
    @Json(name = "lactationroom")
    val lactationRoom: String,
    val parking: String,
    val promotion: String,
    @Json(name = "publictransport")
    val publicTransport: String,
    val restroom: String,
    val room: String,
    val route: String,
    @Json(name = "signguide")
    val signGuide: String,
    val stroller: String,
    @Json(name = "ticketoffice")
    val ticketOffice: String,
    @Json(name = "videoguide")
    val videoGuide: String,
    val wheelchair: String
)