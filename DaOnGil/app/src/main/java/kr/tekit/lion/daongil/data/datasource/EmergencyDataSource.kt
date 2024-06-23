package kr.tekit.lion.daongil.data.datasource

import kr.tekit.lion.daongil.data.dto.remote.response.emergency.basic.EmergencyBasicResponse
import kr.tekit.lion.daongil.data.dto.remote.response.emergency.message.EmergencyMessageResponse
import kr.tekit.lion.daongil.data.dto.remote.response.emergency.realtime.EmergencyRealtimeResponse
import kr.tekit.lion.daongil.data.network.service.EmergencyService

class EmergencyDataSource(
    private val emergencyService: EmergencyService
) {
    suspend fun getEmergencyRealtime(stage1: String?, stage2: String?) : EmergencyRealtimeResponse {
        return emergencyService.getEmergencyRealtime(stage1, stage2)
    }

    suspend fun getEmergencyBasic(hpid: String?) : EmergencyBasicResponse {
        return emergencyService.getEmergencyBasic(hpid)
    }

    suspend fun getEmergencyMessage(hpid: String?): EmergencyMessageResponse {
        return emergencyService.getEmergencyMessage(hpid)
    }
}