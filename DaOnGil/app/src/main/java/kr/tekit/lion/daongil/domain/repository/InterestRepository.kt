package kr.tekit.lion.daongil.domain.repository

import kr.tekit.lion.daongil.data.datasource.InterestDataSource
import kr.tekit.lion.daongil.data.dto.remote.response.Interest.InterestTypeRes
import kr.tekit.lion.daongil.data.network.RetrofitInstance
import kr.tekit.lion.daongil.data.network.service.InterestService
import kr.tekit.lion.daongil.data.repository.InterestRepositoryImpl
import kr.tekit.lion.daongil.domain.model.InterestType

interface InterestRepository {

    suspend fun updateInterestType(interestType: InterestTypeRes)

    suspend fun getInterestType(): List<InterestType>

    companion object{
        fun create(): InterestRepositoryImpl {
            return InterestRepositoryImpl(
                InterestDataSource(
                    RetrofitInstance.interestService
                    // RetrofitInstance.serviceProvider(InterestService::class.java)
                )
            )
        }
    }
}

