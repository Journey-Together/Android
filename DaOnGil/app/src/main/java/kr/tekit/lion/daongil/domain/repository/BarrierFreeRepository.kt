package kr.tekit.lion.daongil.domain.repository

import kr.tekit.lion.daongil.data.datasource.BarrierFreeDataSource
import kr.tekit.lion.daongil.data.network.RetrofitInstance
import kr.tekit.lion.daongil.data.repository.BarrierFreeRepositoryImpl
import kr.tekit.lion.daongil.domain.model.BarrierFree

interface BarrierFreeRepository {
    suspend fun getBarrierFreeInfo(contentId: String): List<BarrierFree>

    companion object {
        fun create(): BarrierFreeRepositoryImpl {
            return BarrierFreeRepositoryImpl(
                BarrierFreeDataSource(
                    RetrofitInstance.korWithService
                )
            )
        }
    }
}
