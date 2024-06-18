package kr.tekit.lion.daongil.data.repository

import kr.tekit.lion.daongil.data.datasource.AedDataSource
import kr.tekit.lion.daongil.domain.model.AedMapInfo
import kr.tekit.lion.daongil.domain.repository.AedRepository

class AedRepositoryImpl(
    private val aedDataSource: AedDataSource
) : AedRepository {
    override suspend fun getAedInfo(q0: String?,q1: String?): List<AedMapInfo> {
        return aedDataSource.getAedInfo(q0, q1).toDomainModel()
    }
}