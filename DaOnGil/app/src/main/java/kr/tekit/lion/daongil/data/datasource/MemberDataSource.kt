package kr.tekit.lion.daongil.data.datasource

import kr.tekit.lion.daongil.data.dto.remote.response.member.MyDefaultInfoResponse
import kr.tekit.lion.daongil.data.dto.remote.response.member.MyInfoResponse
import kr.tekit.lion.daongil.data.network.service.MemberService

class MemberDataSource(
    private val memberService: MemberService
) {
    suspend fun getMyInfo(): MyInfoResponse {
        return memberService.getMyInfo()
    }

    suspend fun getMyDefaultInfo(): MyDefaultInfoResponse {
        return memberService.getMyDefaultInfo()
    }
}