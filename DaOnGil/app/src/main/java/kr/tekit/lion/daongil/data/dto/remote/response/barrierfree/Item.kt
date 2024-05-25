package kr.tekit.lion.daongil.data.dto.remote.response.barrierfree


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Item(
    @Json(name = "contentid")
    val contentId: String,
    val parking: String,
    val route: String,
    @Json(name = "publictransport")
    val publicTransport: String,
    @Json(name = "ticketoffice")
    val ticketOffice: String,
    val promotion: String,
    val wheelchair: String,
    val exit: String,
    val elevator: String,
    val restroom: String,
    val auditorium: String,
    val room: String,
    @Json(name = "handicapetc")
    val handicapEtc: String,
    @Json(name = "braileblock")
    val braileBlock: String,
    @Json(name = "helpdog")
    val helpDog: String,
    @Json(name = "guidehuman")
    val guideHuman: String,
    @Json(name = "audioguide")
    val audioGuide: String,
    @Json(name = "bigprint")
    val bigPrint: String,
    @Json(name = "brailepromotion")
    val brailePromotion: String,
    @Json(name = "guidesystem")
    val guideSystem: String,
    @Json(name = "blindhandicapetc")
    val blindHandicapEtc: String,
    @Json(name = "signguide")
    val signGuide: String,
    @Json(name = "videoguide")
    val videoGuide: String,
    @Json(name = "hearingroom")
    val hearingRoom: String,
    @Json(name = "hearinghandicapetc")
    val hearingHandicapEtc: String,
    val stroller: String,
    @Json(name = "lactationroom")
    val lactationRoom: String,
    @Json(name = "babysparechair")
    val babySpareChair: String,
    @Json(name = "infantsfamilyetc")
    val infantsFamilyEtc: String,
)