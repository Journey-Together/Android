package kr.tekit.lion.daongil.repository.areacode

import kr.tekit.lion.daongil.datasource.areacode.AreaCodeDataSource
import kr.tekit.lion.daongil.dto.request.AreaCodeRequest
import kr.tekit.lion.daongil.dto.response.areacode.AreaCodeResponse
import kr.tekit.lion.daongil.local.AreaCodeEntity
import kr.tekit.lion.daongil.local.toEntity
import kr.tekit.lion.daongil.model.AreaCode

class AreaCodeRepositoryImpl(
    private val remote: AreaCodeDataSource,
    private val local: AreaCodeDataSource,
) : AreaCodeRepository {

    override suspend fun getAreaCode(serviceKey: String, pageNo: String): AreaCodeResponse {
        return remote.getAreaCodes(
            AreaCodeRequest(
                serviceKey = serviceKey,
                pageNo = pageNo,
            ).toRequestModel()
        )
    }

    override suspend fun getAreaCode(code: String): AreaCodeEntity {
        return local.getAreaCode(code)
    }

    override suspend fun getAreaCodes(): List<AreaCodeEntity> {
        return local.getAreaCodes()
    }

    override suspend fun setAreaCode(areaCode: List<AreaCode>) {
        local.setAreaCode(areaCode.map { it.toEntity() })
    }
}