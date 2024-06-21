package kr.tekit.lion.daongil.data.repository

import kr.tekit.lion.daongil.data.datasource.MemberDataSource
import kr.tekit.lion.daongil.data.dto.remote.request.toMultiPartBody
import kr.tekit.lion.daongil.data.dto.remote.request.toRequestBody
import kr.tekit.lion.daongil.domain.model.ConcernType
import kr.tekit.lion.daongil.domain.model.MyDefaultInfo
import kr.tekit.lion.daongil.domain.model.IceInfo
import kr.tekit.lion.daongil.domain.model.MyInfo
import kr.tekit.lion.daongil.domain.model.PersonalInfo
import kr.tekit.lion.daongil.domain.model.ProfileImg
import kr.tekit.lion.daongil.domain.repository.MemberRepository
import okhttp3.MultipartBody

class MemberRepositoryImpl(
    private val memberDataSource: MemberDataSource
): MemberRepository {

    override suspend fun getMyIfo(): MyInfo {
        return memberDataSource.getMyInfo().toDomainModel()
    }

    override suspend fun getMyDefaultInfo(): MyDefaultInfo {
        return memberDataSource.getMyDefaultInfo().toDomainModel()
    }

    override suspend fun modifyMyPersonalInfo(request: PersonalInfo) {
        memberDataSource.modifyMyPersonalInfo(request.toRequestBody())
    }

    override suspend fun modifyMyProfileImg(profileImage: ProfileImg) {
        memberDataSource.modifyMyProfileImage(profileImage.toMultiPartBody())
    }

    override suspend fun modifyMyIceInfo(request: IceInfo) {
        memberDataSource.modifyMyIceInfo(request.toRequestBody())
    }

    override suspend fun getConcernType(): ConcernType {
        return memberDataSource.getConcernType().toDomainModel()
    }

    override suspend fun updateConcernType(requestBody: ConcernType) {
        return memberDataSource.updateConcernType(requestBody.toRequestBody())
    }
}