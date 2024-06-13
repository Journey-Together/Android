package kr.tekit.lion.daongil.domain.usecase.emergency

import kr.tekit.lion.daongil.domain.model.EmergencyMapInfo
import kr.tekit.lion.daongil.domain.usecase.base.BaseUseCase
import kr.tekit.lion.daongil.domain.usecase.base.Result
import kr.tekit.lion.daongil.domain.usecase.base.onSuccess

class GetEmergencyMapInfoUseCase(
    private val getHospitalMapInfoUseCase: GetHospitalMapInfoUseCase,
    private val getAedMapInfoUseCase: GetAedMapInfoUseCase
) : BaseUseCase() {
    suspend operator fun invoke(area: String?, areaDetail: String?): Result<List<EmergencyMapInfo>> =
        execute {
            val aedArea = if (area == "전북특별자치도") "전라북도" else area
            val editAreaDetail = if (area == "세종특별자치시") null else areaDetail

            val hospitalList = getHospitalMapInfoUseCase(area, areaDetail)
            val aedList = getAedMapInfoUseCase(aedArea, editAreaDetail)

            val emergencyList = mutableListOf<EmergencyMapInfo>()

            hospitalList.onSuccess {
                it.map {
                    emergencyList.add(
                        EmergencyMapInfo(
                            hospitalList = it,
                            emergencyType = "hospital",
                            emergencyId = it.hospitalId,
                            aedList = null
                        )
                    )
                }
            }

            aedList.onSuccess {
                it.map{
                    emergencyList.add(
                        EmergencyMapInfo(
                            hospitalList = null,
                            emergencyType = "aed",
                            emergencyId = null,
                            aedList = it
                        )
                    )
                }
            }

            emergencyList
        }
}