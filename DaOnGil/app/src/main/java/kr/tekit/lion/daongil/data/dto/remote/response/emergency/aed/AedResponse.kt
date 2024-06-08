package kr.tekit.lion.daongil.data.dto.remote.response.emergency.aed

import com.squareup.moshi.JsonClass
import kr.tekit.lion.daongil.domain.model.AedMapInfo

@JsonClass(generateAdapter = true)
data class AedResponse(
    val response: Response
) {
    fun toDomainModel(): List<AedMapInfo>{
        return this.response.body.items.item.map { item ->
            AedMapInfo(
                aedName = item.org,
                aedAdress = item.buildAddress,
                managerTel = item.managerTel,
                aedTel = item.clerkTel,
                aedLocation = item.buildPlace,
                monTime = item.monSttTme.toString() + "-" + item.monEndTme.toString(),
                tueTime = item.tueSttTme.toString() + "-" + item.tueEndTme.toString(),
                wedTime = item.wedSttTme.toString() + "-" + item.wedEndTme.toString(),
                thuTime = item.thuSttTme.toString() + "-" + item.thuEndTme.toString(),
                friTime = item.friSttTme.toString() + "-" + item.friEndTme.toString(),
                satTime = item.satSttTme.toString() + "-" + item.satEndTme.toString(),
                sunTime = item.sunSttTme.toString() + "-" + item.sunEndTme.toString(),
                holTime = item.holSttTme.toString() + "-" + item.holEndTme.toString(),
                sunAvailable = updateSunAvailable(
                    item.sunFrtYon.toString(),
                    item.sunScdYon.toString(), item.sunThiYon.toString(),
                    item.sunFurYon.toString(), item.sunFifYon.toString()
                ),
                aedLon = item.wgs84Lon,
                aedLat = item.wgs84Lat
            )
        }
    }

    fun updateSunAvailable(first: String, second: String, third: String, fourth: String, fifth: String, ) : String{
        var sunAvailable = ""

        if(first == "Y") {
            sunAvailable += "첫째주"
        }
        if(second == "Y") {
            if (sunAvailable.isNotEmpty()) sunAvailable += ", "
            sunAvailable += "둘째주"
        }
        if(third == "Y") {
            if (sunAvailable.isNotEmpty()) sunAvailable += ", "
            sunAvailable += "셋째주"
        }
        if (fourth == "Y") {
            if (sunAvailable.isNotEmpty()) sunAvailable += ", "
            sunAvailable += "넷째주"

        }
        if (fifth == "Y"){
            if (sunAvailable.isNotEmpty()) sunAvailable += ", "
            sunAvailable += "다섯째주"

        }

        return sunAvailable
    }
}