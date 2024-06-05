package kr.tekit.lion.daongil.data.repository

import kr.tekit.lion.daongil.data.datasource.LocalVillageCodeDataSource
import kr.tekit.lion.daongil.data.dto.local.VillageCodeEntity
import kr.tekit.lion.daongil.data.dto.local.toDomainModel
import kr.tekit.lion.daongil.data.dto.local.toEntity
import kr.tekit.lion.daongil.domain.model.AreaCode
import kr.tekit.lion.daongil.domain.model.VillageCode
import kr.tekit.lion.daongil.domain.repository.VillageCodeRepository

class VillageCodeRepositoryImpl(
    private val villageCodeDataSource: LocalVillageCodeDataSource
): VillageCodeRepository {

    override suspend fun getAllVillageCode(code: String): List<VillageCode> {
        return villageCodeDataSource.getAllVillageInfoList(code).map { it.toDomainModel() }
    }

    override suspend fun addVillageCode(villageCode: List<VillageCode>) {
        villageCodeDataSource.addVillageCodeInfoList(villageCode.map { it.toEntity() })
    }
}