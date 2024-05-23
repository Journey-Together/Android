package kr.tekit.lion.daongil.data.repository

import kr.tekit.lion.daongil.data.datasource.BarrierFreeDataSource
import kr.tekit.lion.daongil.domain.model.BarrierFree
import kr.tekit.lion.daongil.domain.repository.BarrierFreeRepository

class BarrierFreeRepositoryImpl(
    private val barrierFreeDataSource: BarrierFreeDataSource
) : BarrierFreeRepository {

    override suspend fun getBarrierFreeInfo(contentId: String): List<BarrierFree> {
        return barrierFreeDataSource.getBarrierFreeInfo(contentId).toDomainModel()
    }
}