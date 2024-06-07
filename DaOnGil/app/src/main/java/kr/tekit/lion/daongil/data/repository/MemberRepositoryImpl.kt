package kr.tekit.lion.daongil.data.repository

import kr.tekit.lion.daongil.data.datasource.MemberDataSource
import kr.tekit.lion.daongil.domain.model.MyDefaultInfo
import kr.tekit.lion.daongil.domain.model.MyInfo
import kr.tekit.lion.daongil.domain.repository.MemberRepository

class MemberRepositoryImpl(
    private val memberDataSource: MemberDataSource
): MemberRepository {

    override suspend fun getMyIfo(): MyInfo {
        return memberDataSource.getMyInfo().toDomainModel()
    }

    override suspend fun getMyDefaultInfo(): MyDefaultInfo {
        return memberDataSource.getMyDefaultInfo().toDomainModel()
    }
}