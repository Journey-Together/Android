package kr.tekit.lion.daongil.data.repository

import kr.tekit.lion.daongil.data.datasource.areacode.LocalAreaCodeDataSource
import kr.tekit.lion.daongil.data.datasource.areacode.RemoteAreaCodeDataSource
import kr.tekit.lion.daongil.data.dto.local.toDomainModel
import kr.tekit.lion.daongil.data.dto.local.toEntity
import kr.tekit.lion.daongil.domain.model.AreaCode
import kr.tekit.lion.daongil.domain.repository.AreaCodeRepository

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

    override suspend fun getAreaCodeInfo(): List<AreaCode> {
        return remote.getAreaInfoList().toDomainModel()
    }

    override suspend fun addAreaCodeInfo() {
        local.addAreaCodeInfoList(getAreaCodeInfo().map { it.toEntity() })
    }
}