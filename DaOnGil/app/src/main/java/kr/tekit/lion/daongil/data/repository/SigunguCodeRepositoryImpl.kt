package kr.tekit.lion.daongil.data.repository

import kr.tekit.lion.daongil.data.datasource.SigunguCodeDataSource
import kr.tekit.lion.daongil.data.dto.local.toDomainModel
import kr.tekit.lion.daongil.data.dto.local.toEntity
import kr.tekit.lion.daongil.domain.model.SigunguCode
import kr.tekit.lion.daongil.domain.repository.VillageCodeRepository

class SigunguCodeRepositoryImpl(
    private val villageCodeDataSource: SigunguCodeDataSource
) : VillageCodeRepository {

    override suspend fun getSigunguCodeByVillageName(villageName: String): String? {
        return villageCodeDataSource.getSigunguCodeByVillageName(villageName)
    }

    override suspend fun getAllSigunguCode(code: String): List<SigunguCode> {
        return villageCodeDataSource.getAllSigunguInfoList(code).map { it.toDomainModel() }
    }

    override suspend fun addSigunguCode(sigunguCode: List<SigunguCode>) {
        villageCodeDataSource.addSigunguCodeInfoList(sigunguCode.map { it.toEntity() })
    }
}