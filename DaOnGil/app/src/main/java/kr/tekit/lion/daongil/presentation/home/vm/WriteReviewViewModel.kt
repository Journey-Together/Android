package kr.tekit.lion.daongil.presentation.home.vm

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kr.tekit.lion.daongil.domain.model.NewReviewData
import kr.tekit.lion.daongil.domain.model.NewReviewImages
import kr.tekit.lion.daongil.domain.usecase.base.onError
import kr.tekit.lion.daongil.domain.usecase.base.onSuccess
import kr.tekit.lion.daongil.domain.usecase.place.WritePlaceReviewDataUseCase
import org.json.JSONObject
import retrofit2.HttpException
import java.time.LocalDate

class WriteReviewViewModel(
    private val writePlaceReviewDataUseCase: WritePlaceReviewDataUseCase
) : ViewModel() {
    private val _placeVisitDate = MutableLiveData<LocalDate>()
    val placeVisitDate: LiveData<LocalDate> = _placeVisitDate

    private val _newReviewData = MutableLiveData<NewReviewData>()
    val newReviewData : LiveData<NewReviewData> = _newReviewData

    private val _newReviewImages = MutableLiveData<List<String>>()
    val newReviewImages : LiveData<List<String>> = _newReviewImages

    fun setPlaceVisitDate(startDate: LocalDate) {
        _placeVisitDate.value = startDate
    }

    fun writePlaceReviewData(placeId: Long, date: LocalDate, grade: Float, content: String, callback: (Boolean, Boolean, Int) -> Unit) {
        var requestFlag = false
        var code = 0

        _newReviewData.value = newReviewData.value?.copy(
            date = date,
            grade = grade,
            content = content
        )
        viewModelScope.launch{
            val success = try {
                writePlaceReviewDataUseCase(placeId, NewReviewData(date, grade, content), NewReviewImages(newReviewImages.value))
                    .onSuccess {
                        Log.e("writePlaceReviewData image", "images : ${newReviewImages.value}")
                        requestFlag = true
                        val json = JSONObject(it.string())
                        val successCode = json.getInt("code")
                        Log.d("writePlaceReviewData", json.toString())

                        code = successCode
                    }.onError {
                        if (it is HttpException) {
                            val errorBody = it.response()?.errorBody()?.string()
                            val json = JSONObject(errorBody)
                            val message = json.getString("message")
                            val errorCode = json.getInt("code")
                            Log.e("writePlaceReviewData Error__", "code : $errorCode, message : $message")

                            code = errorCode
                        }
                    }
                true
            } catch (e: Exception) {
                Log.d("writePlaceReviewData", "Error : ${e.message}")
                false
            }
            callback(success, requestFlag, code)
        }
    }

    fun setReviewImages(image: String) {
        val currentImages = _newReviewImages.value?.toMutableList() ?: mutableListOf()
        currentImages.add(image)
        _newReviewImages.value = currentImages
    }

    fun deleteImage(position: Int) {
        val currentImages = _newReviewImages.value?.toMutableList() ?: mutableListOf()
        if (position in currentImages.indices) {
            currentImages.removeAt(position)
            _newReviewImages.value = currentImages
        }
    }

}