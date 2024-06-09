package kr.tekit.lion.daongil.domain.repository

import kr.tekit.lion.daongil.data.datasource.MemberDataSource
import kr.tekit.lion.daongil.data.network.RetrofitInstance
import kr.tekit.lion.daongil.data.network.service.MemberService
import kr.tekit.lion.daongil.data.repository.MemberRepositoryImpl
import kr.tekit.lion.daongil.domain.model.MyDefaultInfo
import kr.tekit.lion.daongil.domain.model.IceInfo
import kr.tekit.lion.daongil.domain.model.MyInfo
import kr.tekit.lion.daongil.domain.model.PersonalInfo
import okhttp3.MultipartBody

interface MemberRepository {
    suspend fun getMyIfo(): MyInfo
    suspend fun getMyDefaultInfo(): MyDefaultInfo
    suspend fun modifyMyPersonalInfo(modifiedData: MyPersonalInfo)
    suspend fun modifyMyProfileImg(profileImage: File)
    suspend fun modifyMyIceInfo(modifiedData: MyIceInfo)

    suspend fun modifyMyPersonalInfo(request: PersonalInfo)

    suspend fun modifyMyIceInfo(request: IceInfo)

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