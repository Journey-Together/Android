package kr.tekit.lion.daongil.data.dto.remote.response.emergency.basic

import kr.tekit.lion.daongil.domain.model.EmergencyBasicInfo
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class EmergencyBasicResponse(
    val response: Response
) {
    fun toDomainModel(): EmergencyBasicInfo {
        this.response.body.items.item.apply {
            return EmergencyBasicInfo(
                hospitalId = hpid,
                hospitalName = dutyName,
                hospitalAddress = dutyAddr,
                hospitalTel = dutyTel1,
                emergencyTel = dutyTel3,
                dialysis = MKioskTy7,
                earlyBirth = MKioskTy8,
                burns = MKioskTy11,
                hospitalLon = wgs84Lon,
                hospitalLat = wgs84Lat
            )
        }
    }
}