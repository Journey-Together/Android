package kr.tekit.lion.daongil.domain.usecase

import android.util.Log
import kr.tekit.lion.daongil.domain.model.ProfileImg
import kr.tekit.lion.daongil.domain.repository.MemberRepository
import kr.tekit.lion.daongil.domain.usecase.base.BaseUseCase
import kr.tekit.lion.daongil.domain.usecase.base.Result


class ModifyMyProfileImageUseCase(
    private val memberRepository: MemberRepository
): BaseUseCase() {
    suspend operator fun invoke(profileImage: ProfileImg): Result<Unit> = execute{
        Log.d("ImgRuestResult", "UseCase ${profileImage.path}")

        memberRepository.modifyMyProfileImg(profileImage)
    }
}