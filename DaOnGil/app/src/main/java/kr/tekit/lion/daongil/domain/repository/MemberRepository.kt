package kr.tekit.lion.daongil.domain.repository

import kr.tekit.lion.daongil.data.datasource.MemberDataSource
import kr.tekit.lion.daongil.data.network.RetrofitInstance
import kr.tekit.lion.daongil.data.network.service.MemberService
import kr.tekit.lion.daongil.data.repository.MemberRepositoryImpl
import kr.tekit.lion.daongil.domain.model.MyDefaultInfo
import kr.tekit.lion.daongil.domain.model.MyInfo

interface MemberRepository {
    suspend fun getMyIfo(): MyInfo
    suspend fun getMyDefaultInfo(): MyDefaultInfo

    companion object{
        fun create(): MemberRepositoryImpl{
            return MemberRepositoryImpl(
                MemberDataSource(
                    RetrofitInstance.serviceProvider(MemberService::class.java)
                )
            )
        }
    }
}