package kr.tekit.lion.daongil.data.repository

import kr.tekit.lion.daongil.data.datasource.MemberDataSource
import kr.tekit.lion.daongil.data.dto.remote.request.toRequestModel
import kr.tekit.lion.daongil.data.ext.toMultipartBody
import kr.tekit.lion.daongil.domain.model.MyDefaultInfo
import kr.tekit.lion.daongil.domain.model.MyIceInfo
import kr.tekit.lion.daongil.domain.model.MyInfo
import kr.tekit.lion.daongil.domain.model.MyPersonalInfo
import kr.tekit.lion.daongil.domain.repository.MemberRepository
import java.io.File

class MemberRepositoryImpl(
    private val memberDataSource: MemberDataSource
): MemberRepository {

    override suspend fun getMyIfo(): MyInfo {
        return memberDataSource.getMyInfo().toDomainModel()
    }

    override suspend fun getMyDefaultInfo(): MyDefaultInfo {
        return memberDataSource.getMyDefaultInfo().toDomainModel()
    }

    override suspend fun modifyMyPersonalInfo(modifiedData: MyPersonalInfo) {
        memberDataSource.modifyMyPersonalInfo(modifiedData.toRequestModel())
    }

    override suspend fun modifyMyProfileImg(profileImage: File) {
        memberDataSource.modifyMyProfileImage(profileImage.toMultipartBody())
    }

    override suspend fun modifyMyIceInfo(request: IceInfo) {
        memberDataSource.modifyMyIceInfo(request.toRequestBody())
    }
}