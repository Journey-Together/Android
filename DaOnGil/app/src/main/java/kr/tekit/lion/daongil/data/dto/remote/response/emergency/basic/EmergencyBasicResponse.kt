package kr.tekit.lion.daongil.data.dto.remote.response.emergency.basic

import kr.tekit.lion.daongil.domain.model.EmergencyBasicInfo
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class EmergencyBasicResponse(
    val response: Response
) {
    fun toDomainModel(): EmergencyBasicInfo {
        return response.body.items.item.let { item ->
            EmergencyBasicInfo(
                hospitalId = item.hpid,
                hospitalName = item.dutyName,
                hospitalAddress = item.dutyAddr,
                hospitalTel = item.dutyTel1,
                emergencyTel = item.dutyTel3,
                dialysis = checkAvailability(item.MKioskTy7),
                earlyBirth = checkAvailability(item.MKioskTy8),
                burns = checkAvailability(item.MKioskTy11),
                hospitalLon = item.wgs84Lon,
                hospitalLat = item.wgs84Lat
            )
        }
    }


    fun checkAvailability(input: String?): String {
        if (input == null) return "정보가 없습니다."
        return when {
            'Y' in input -> "진료 가능합니다."
            'N' in input -> "진료 불가능합니다."
            else -> "정보 없음"
        }
    }

}
