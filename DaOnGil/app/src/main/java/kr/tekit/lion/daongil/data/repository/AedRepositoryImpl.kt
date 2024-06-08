package kr.tekit.lion.daongil.data.repository

import kr.tekit.lion.daongil.data.datasource.AedDataSource
import kr.tekit.lion.daongil.domain.model.AedMapInfo
import kr.tekit.lion.daongil.domain.repository.AedRepository

class AedRepositoryImpl(
    private val aedDataSource: AedDataSource
) : AedRepository {
    override suspend fun getAedInfo(Q0: String?, Q1: String?): List<AedMapInfo> {
        return aedDataSource.getAedInfo(Q0, Q1).toDomainModel()
    }


}