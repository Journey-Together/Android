package kr.tekit.lion.daongil.repository.areacode

import kr.tekit.lion.daongil.datasource.areacode.AreaCodeDataSource
import kr.tekit.lion.daongil.dto.request.AreaCodeRequest
import kr.tekit.lion.daongil.dto.response.areacode.AreaCodeResponse

class AreaCodeRepositoryImpl(
    private val remote: AreaCodeDataSource,
    private val local: AreaCodeDataSource,
): AreaCodeRepository {
    override suspend fun getAreaCode(request: AreaCodeRequest): AreaCodeResponse {
        return remote.getAreaCode(request)
    }
}