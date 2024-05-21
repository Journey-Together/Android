package kr.tekit.lion.daongil.repository.villagecode

import kr.tekit.lion.daongil.datasource.LocalVillageCodeDataSource
import kr.tekit.lion.daongil.local.entity.VillageCodeEntity
import kr.tekit.lion.daongil.local.entity.toDomainModel
import kr.tekit.lion.daongil.model.AreaCode
import kr.tekit.lion.daongil.model.VillageCode

class VillageCodeRepositoryImpl(
    private val villageCodeDataSource: LocalVillageCodeDataSource
): VillageCodeRepository {

    override suspend fun getAllVillageCode(): List<VillageCode> {
        return villageCodeDataSource.getAllVillageInfoList().map { it.toDomainModel() }
    }

    override suspend fun addVillageCode(areaCode: String, villageCode: List<AreaCode>) {
        villageCodeDataSource.addVillageCodeInfoList(
            villageCode.map {
                VillageCodeEntity(it.name, it.code, areaCode)
            }
        )
    }

}