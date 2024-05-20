package kr.tekit.lion.daongil.repository.areacode

import kr.tekit.lion.daongil.datasource.areacode.AreaCodeDataSource
import kr.tekit.lion.daongil.local.entity.toDomainModel
import kr.tekit.lion.daongil.model.AreaCode
import kr.tekit.lion.daongil.model.VillageCode

class AreaCodeRepositoryImpl(
    private val remote: AreaCodeDataSource,
    private val local: AreaCodeDataSource,
) : AreaCodeRepository {

    override suspend fun getAreaCodeInfo(code: String): AreaCode {
        return local.getAreaCodeInfo(code).toDomainModel()
    }

    override suspend fun getAllAreaCodes(): List<AreaCode> {
        return local.getAllAreaCodes().map { it.toDomainModel() }
    }

    override suspend fun addAreaCodeInfo() {
        val remoteResult = remote.getAreaInfoList().toDomainModel()
        local.addAreaCodeInfoList(remoteResult)
    }

    override suspend fun addVillageCodeInfo() {
        getAllAreaCodes().map { entity ->
            val villageCodes = remote.getAreaInfoList(entity.code).toDomainModel().map {
                VillageCode(entity.code, it.code, it.name)
            }
           local.addVillageCodeInfoList(villageCodes)
        }
    }
}