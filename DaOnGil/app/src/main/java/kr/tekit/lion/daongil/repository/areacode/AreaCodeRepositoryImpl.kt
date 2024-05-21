package kr.tekit.lion.daongil.repository.areacode

import kr.tekit.lion.daongil.datasource.areacode.LocalAreaCodeDataSource
import kr.tekit.lion.daongil.datasource.areacode.RemoteAreaCodeDataSource
import kr.tekit.lion.daongil.local.entity.toDomainModel
import kr.tekit.lion.daongil.local.entity.toEntity
import kr.tekit.lion.daongil.model.AreaCode

class AreaCodeRepositoryImpl(
    private val remote: RemoteAreaCodeDataSource,
    private val local: LocalAreaCodeDataSource,
) : AreaCodeRepository {

    override suspend fun getAreaCodeInfo(code: String): AreaCode {
        return local.getAreaCodeInfo(code).toDomainModel()
    }

    override suspend fun getAllAreaCodes(): List<AreaCode> {
        return local.getAllAreaCodes().map { it.toDomainModel() }
    }

    override suspend fun getDetailAreaCode(areaCode: String): List<AreaCode> {
        return remote.getAreaInfoList(areaCode).toDomainModel()
    }

    override suspend fun getAreaCodeInfo(): List<AreaCode> {
        return remote.getAreaInfoList().toDomainModel()
    }

    override suspend fun addAreaCodeInfo() {
        local.addAreaCodeInfoList(getAreaCodeInfo().map { it.toEntity() })
    }
}