package kr.tekit.lion.daongil.domain.repository

import kr.tekit.lion.daongil.data.datasource.EmergencyDataSource
import kr.tekit.lion.daongil.data.network.RetrofitInstance
import kr.tekit.lion.daongil.data.repository.EmergencyRepositoryImpl
import kr.tekit.lion.daongil.domain.model.EmergencyBasicInfo
import kr.tekit.lion.daongil.domain.model.EmergencyMessageInfo
import kr.tekit.lion.daongil.domain.model.EmergencyRealtimeInfo

interface EmergencyRepository {

    suspend fun getEmergencyRealtime(STAGE1: String?, STAGE2: String?): List<EmergencyRealtimeInfo>

    suspend fun getEmergencyBasic(HPID: String?): EmergencyBasicInfo

    suspend fun getEmergencyMessage(HPID: String?):List<EmergencyMessageInfo>
    companion object{
        fun create(): EmergencyRepositoryImpl{
            return EmergencyRepositoryImpl(
                EmergencyDataSource(
                    RetrofitInstance.emergencyService
                )
            )
        }
    }
}