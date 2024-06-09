package kr.tekit.lion.daongil.domain.usecase

import kr.tekit.lion.daongil.domain.model.PersonalInfo
import kr.tekit.lion.daongil.domain.repository.MemberRepository
import kr.tekit.lion.daongil.domain.usecase.base.BaseUseCase
import kr.tekit.lion.daongil.domain.usecase.base.Result
import okhttp3.MultipartBody
import okhttp3.RequestBody

class ModifyMyPersonalInfoUseCase(
    private val memberRepository: MemberRepository
): BaseUseCase() {
    suspend operator fun invoke(request: PersonalInfo): Result<Unit> = execute {
        memberRepository.modifyMyPersonalInfo(request)
    }
}