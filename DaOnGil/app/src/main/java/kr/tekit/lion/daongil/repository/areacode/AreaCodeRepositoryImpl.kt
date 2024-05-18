package kr.tekit.lion.daongil.repository.areacode

import kr.tekit.lion.daongil.datasource.areacode.AreaCodeDataSource
import kr.tekit.lion.daongil.local.AreaCodeEntity

class AreaCodeRepositoryImpl(
    private val remote: AreaCodeDataSource,
    private val local: AreaCodeDataSource,
) : AreaCodeRepository {

    override suspend fun getAreaCodeInfo(code: String): AreaCodeEntity {
        return local.getAreaCodeInfo(code)
    }

    override suspend fun getAllAreaCodes(): List<AreaCodeEntity> {
        return local.getAllAreaCodes()
    }

    override suspend fun fetchAreaCodeInfo() {
        val remoteResult = remote.getAreaInfoList().toDomainModel()
        local.addAreaInfoList(remoteResult)
    }
}