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
        stage1: String?,
        stage2: String?
    ): List<EmergencyRealtimeInfo> {
        return emergencyDataSource.getEmergencyRealtime(stage1, stage2).toDomainModel()
    }

    override suspend fun getEmergencyBasic(hpid: String?): EmergencyBasicInfo {
        return emergencyDataSource.getEmergencyBasic(hpid).toDomainModel()
    }

    override suspend fun getEmergencyMessage(hpid: String?): List<EmergencyMessageInfo> {
        return emergencyDataSource.getEmergencyMessage(hpid).toDomainModel()
    }
}