package kr.tekit.lion.daongil.data.datasource

import kr.tekit.lion.daongil.data.dto.remote.response.emergency.basic.EmergencyBasicResponse
import kr.tekit.lion.daongil.data.dto.remote.response.emergency.realtime.EmergencyRealtimeResponse
import kr.tekit.lion.daongil.data.network.service.EmergencyService

class EmergencyDataSource(
    private val emergencyService: EmergencyService
) {
    suspend fun getEmergencyRealtime(STAGE1: String?, STAGE2: String?) : EmergencyRealtimeResponse {
        return emergencyService.getEmergencyRealtime(STAGE1, STAGE2)
    }

    suspend fun getEmergencyBasic(HPID: String?) : EmergencyBasicResponse {
        return emergencyService.getEmergencyBasic(HPID)
    }
}