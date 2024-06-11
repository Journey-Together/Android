package kr.tekit.lion.daongil.presentation.scheduleform.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kr.tekit.lion.daongil.domain.repository.PlaceSearchResultRepository
import kr.tekit.lion.daongil.domain.usecase.GetPlaceSearchResultUseCase

// ViewModelFactory = ViewModel을 생성하는 역할을 하는 클래스
// ViewModelProvider.Factory 인터페이스를 구현한다.
class ScheduleFormViewModelFactory : ViewModelProvider.Factory {
    private val getPlaceSearchResultUseCase = GetPlaceSearchResultUseCase(
        PlaceSearchResultRepository.create() // repository의 인스턴스를 생성하여 전달
    )

    // create : 특정 ViewModel 클래스를 인스턴스화하는 역할
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(ScheduleFormViewModel::class.java)){
            return ScheduleFormViewModel(getPlaceSearchResultUseCase) as T
        }
        throw IllegalArgumentException("unknown ViewModel Class")
    }
}