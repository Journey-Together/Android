package kr.tekit.lion.daongil.data.repository

import kr.tekit.lion.daongil.data.datasource.EmergencyDataSource
import kr.tekit.lion.daongil.domain.model.EmergencyBasicInfo
import kr.tekit.lion.daongil.domain.model.EmergencyMessageInfo
import kr.tekit.lion.daongil.domain.model.EmergencyRealtimeInfo
import kr.tekit.lion.daongil.domain.repository.EmergencyRepository

class EmergencyRepositoryImpl(
    private val emergencyDataSource: EmergencyDataSource
)  : EmergencyRepository{

    override suspend fun getEmergencyRealtime(
        STAGE1: String?,
        STAGE2: String?
    ): List<EmergencyRealtimeInfo> {
        return emergencyDataSource.getEmergencyRealtime(STAGE1, STAGE2).toDomainModel()
    }

    override suspend fun getEmergencyBasic(
        HPID: String?
    ): EmergencyBasicInfo {
        return emergencyDataSource.getEmergencyBasic(HPID).toDomainModel()
    }

    override suspend fun getEmergencyMessage(HPID: String?): List<EmergencyMessageInfo> {
        return emergencyDataSource.getEmergencyMessage(HPID).toDomainModel()
    }
}