package kr.tekit.lion.daongil.data.datasource

import kr.tekit.lion.daongil.data.dto.remote.request.MyIceInfoRequest
import kr.tekit.lion.daongil.data.dto.remote.request.MyPersonalInfoRequest
import kr.tekit.lion.daongil.data.dto.remote.request.toRequestBody
import kr.tekit.lion.daongil.data.dto.remote.request.toRequestModel
import kr.tekit.lion.daongil.data.dto.remote.response.member.MyDefaultInfoResponse
import kr.tekit.lion.daongil.data.dto.remote.response.member.MyInfoResponse
import kr.tekit.lion.daongil.data.network.service.MemberService
import kr.tekit.lion.daongil.domain.model.MyPersonalInfo
import kr.tekit.lion.daongil.domain.model.PersonalInfo
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody

class MemberDataSource(
    private val memberService: MemberService
) {
    suspend fun getMyInfo(): MyInfoResponse {
        return memberService.getMyInfo()
    }

    suspend fun getMyDefaultInfo(): MyDefaultInfoResponse {
        return memberService.getMyDefaultInfo()
    }

    suspend fun modifyMyPersonalInfo(request: PersonalInfo) {
        memberService.modifyMyPersonalInfo(request.toRequestBody())
    }

    suspend fun modifyMyProfileImage(request: MultipartBody.Part){
        memberService.modifyMyProfileImage(request)
    }

    suspend fun modifyMyIceInfo(request: MyIceInfoRequest){
        memberService.modifyMyIceInfo(request)
    }
}