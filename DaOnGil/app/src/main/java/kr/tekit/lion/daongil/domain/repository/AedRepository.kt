package kr.tekit.lion.daongil.domain.repository

import kr.tekit.lion.daongil.data.datasource.AedDataSource
import kr.tekit.lion.daongil.data.network.RetrofitInstance
import kr.tekit.lion.daongil.data.repository.AedRepositoryImpl
import kr.tekit.lion.daongil.domain.model.AedMapInfo

interface AedRepository {
    suspend fun getAedInfo(Q0: String?, Q1: String?): List<AedMapInfo>

    companion object {
        fun create(): AedRepositoryImpl{
            return AedRepositoryImpl(
                AedDataSource(
                    RetrofitInstance.aedService
                )
            )

        }
    }
}