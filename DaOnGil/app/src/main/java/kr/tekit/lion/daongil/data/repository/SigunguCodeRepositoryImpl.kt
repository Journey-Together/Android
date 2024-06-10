package kr.tekit.lion.daongil.data.repository

import kr.tekit.lion.daongil.data.datasource.LocalSigunguCodeDataSource
import kr.tekit.lion.daongil.data.dto.local.toDomainModel
import kr.tekit.lion.daongil.data.dto.local.toEntity
import kr.tekit.lion.daongil.domain.model.VillageCode
import kr.tekit.lion.daongil.domain.repository.VillageCodeRepository

class SigunguCodeRepositoryImpl(
    private val villageCodeDataSource: LocalSigunguCodeDataSource
) : VillageCodeRepository {

    override suspend fun getSigunguCodeByVillageName(villageName: String): String? {
        return villageCodeDataSource.getSigunguCodeByVillageName(villageName)
    }

    override suspend fun getAllSigunguCode(code: String): List<VillageCode> {
        return villageCodeDataSource.getAllSigunguInfoList(code).map { it.toDomainModel() }
    }

    override suspend fun addSigunguCode(villageCode: List<VillageCode>) {
        villageCodeDataSource.addSigunguCodeInfoList(villageCode.map { it.toEntity() })
    }
}