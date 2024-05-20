package kr.tekit.lion.daongil.datasource.areacode

import kr.tekit.lion.daongil.dto.request.AreaCodeRequest
import kr.tekit.lion.daongil.dto.response.areacode.AreaCodeResponse
import kr.tekit.lion.daongil.local.entity.AreaCodeEntity
import kr.tekit.lion.daongil.local.entity.VillageCodeEntity
import kr.tekit.lion.daongil.model.AreaCode
import kr.tekit.lion.daongil.model.VillageCode
import kr.tekit.lion.daongil.network.service.KorWithService

class RemoteAreaCodeDataSource(
    private val korWithService: KorWithService
) : AreaCodeDataSource {

    override suspend fun getAreaInfoList(areaCode: String): AreaCodeResponse {
        return korWithService.getAreaCode(AreaCodeRequest(areaCode).toRequestModel())
    }

    override suspend fun addAreaCodeInfoList(areaCodes: List<AreaCode>) {}

    override suspend fun addVillageCodeInfoList(villageCodes: List<VillageCode>) {}

    override suspend fun getAllAreaCodes(): List<AreaCodeEntity> { TODO() }

    override suspend fun getAreaCodeInfo(code: String): AreaCodeEntity { TODO() }

    override suspend fun getAllVillageInfoList(): List<VillageCodeEntity> { TODO() }

    override suspend fun getAllVillageInfo(): VillageCodeEntity { TODO() }
}