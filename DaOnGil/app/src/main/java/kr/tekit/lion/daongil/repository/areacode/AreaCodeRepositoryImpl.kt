package kr.tekit.lion.daongil.repository.areacode

import kr.tekit.lion.daongil.datasource.areacode.AreaCodeDataSource
import kr.tekit.lion.daongil.local.AreaCodeEntity
import kr.tekit.lion.daongil.local.toEntity

class AreaCodeRepositoryImpl(
    private val remote: AreaCodeDataSource,
    private val local: AreaCodeDataSource,
) : AreaCodeRepository {

    override suspend fun getAreaCodeInfo(code: String): AreaCodeEntity {
        return local.getAreaCodeInfo(code)
    }

    override suspend fun getAllAreaCodes(): List<AreaCodeEntity> {
        return local.getAllAreaCodes()
    }

    override suspend fun fetchAreaCodeInfo() {
        val remoteResult = remote.getAreaInfoList(SERVICE_KEY, "1").toDomainModel().map { it.toEntity() } +
            remote.getAreaInfoList(SERVICE_KEY, "2").toDomainModel().map { it.toEntity() }
        local.addAreaInfoList(remoteResult)
    }

    companion object {
        const val SERVICE_KEY =
            "t2ivQakqcZ/cvxzekT7Ra9Ja8J1N1lBKu6LqVkijMliEeoD1lLXU0Qei+V9AC8aMbNG+TjVkca70NqFB9akmSg=="
    }
}