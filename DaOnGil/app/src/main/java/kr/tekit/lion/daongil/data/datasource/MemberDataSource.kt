package kr.tekit.lion.daongil.data.datasource

import kr.tekit.lion.daongil.data.dto.remote.response.member.MyDefaultInfoResponse
import kr.tekit.lion.daongil.data.dto.remote.response.member.MyInfoResponse
import kr.tekit.lion.daongil.data.network.service.MemberService
import okhttp3.MultipartBody
import okhttp3.RequestBody

class MemberDataSource(
    private val memberService: MemberService
) {
    suspend fun getMyInfo(): MyInfoResponse {
        return memberService.getMyInfo()
    }

    suspend fun getMyDefaultInfo(): MyDefaultInfoResponse {
        return memberService.getMyDefaultInfo()
    }

    suspend fun modifyMyPersonalInfo(request: RequestBody) {
        memberService.modifyMyPersonalInfo(request)
    }

    suspend fun modifyMyProfileImage(request: MultipartBody.Part) {
        memberService.modifyMyProfileImage(request)
    }

    suspend fun modifyMyIceInfo(request: RequestBody) {
        memberService.modifyMyIceInfo(request)
    }
}