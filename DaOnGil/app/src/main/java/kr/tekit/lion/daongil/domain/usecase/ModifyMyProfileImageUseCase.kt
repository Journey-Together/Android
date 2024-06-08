package kr.tekit.lion.daongil.domain.usecase

import kr.tekit.lion.daongil.domain.repository.MemberRepository
import kr.tekit.lion.daongil.domain.usecase.base.BaseUseCase
import kr.tekit.lion.daongil.domain.usecase.base.Result
import java.io.File


class ModifyMyProfileImageUseCase(
    private val memberRepository: MemberRepository
): BaseUseCase() {
    suspend operator fun invoke(profileImage: File): Result<Unit> = execute{
        memberRepository.modifyMyProfileImg(profileImage)
    }
}