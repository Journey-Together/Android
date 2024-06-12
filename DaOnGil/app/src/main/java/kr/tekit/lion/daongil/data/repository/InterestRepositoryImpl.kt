package kr.tekit.lion.daongil.data.repository

import kr.tekit.lion.daongil.data.datasource.InterestDataSource
import kr.tekit.lion.daongil.data.dto.remote.response.Interest.InterestTypeRes
import kr.tekit.lion.daongil.domain.model.InterestType
import kr.tekit.lion.daongil.domain.repository.InterestRepository

class InterestRepositoryImpl(
    private val interestDataSource: InterestDataSource
) : InterestRepository {
    override suspend fun updateInterestType(interestTypeRes: InterestTypeRes) {
        return interestDataSource.updateInterestType(interestTypeRes)
    }

    override suspend fun getInterestType(): List<InterestType> {
        return listOf(interestDataSource.getInterestType().toDomainModel())
    }
}