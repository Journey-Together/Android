package kr.tekit.lion.daongil.dto.response.areabased

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Item(
    @Json(name = "addr1")
    val address: String,
    @Json(name = "addr2")
    val detailAddress: String,
    @Json(name = "areacode")
    val areaCode: String,
    @Json(name = "booktour")
    val bookTour: String,
    val cat1: String,
    val cat2: String,
    val cat3: String,
    @Json(name = "contentid")
    val contentId: String,
    @Json(name = "contenttypeid")
    val contentTypeId: String,
    @Json(name = "cpyrhtDivCd")
    val copyrightType: String,
    @Json(name = "createdtime")
    val createdTime: String,
    @Json(name = "firstimage")
    val firstImage: String,
    @Json(name = "firstimage2")
    val firstImage2: String,
    @Json(name = "mapx")
    val mapX: String,
    @Json(name = "mapy")
    val mapY: String,
    @Json(name = "mlevel")
    val mLevel: String,
    @Json(name = "modifiedtime")
    val modifiedTime: String,
    @Json(name = "sigungucode")
    val villageCode: String,
    val tel: String,
    val title: String,
    val zipcode: String
)