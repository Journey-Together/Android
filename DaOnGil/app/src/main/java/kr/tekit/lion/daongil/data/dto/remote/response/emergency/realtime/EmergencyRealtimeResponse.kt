package kr.tekit.lion.daongil.data.dto.remote.response.emergency.realtime

import com.squareup.moshi.JsonClass

import kr.tekit.lion.daongil.domain.model.EmergencyRealtimeInfo

@JsonClass(generateAdapter = true)
data class EmergencyRealtimeResponse(
    val response: Response
) {
    fun toDomainModel(): List<EmergencyRealtimeInfo> {
        return this.response.body.items.item.map { item ->
            EmergencyRealtimeInfo(
                hospitalId = item.hpid,
                emergencyCount = item.hvec,
                emergencyKid = item.hv10,
                emergencyKidCount = item.hv28,
                emergencyAllCount = item.hvs01,
                emergencyKidAllCount = item.hvs02,
                lastUpdateDate = item.hvidate,
            )
        }
    }
}