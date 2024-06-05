package kr.tekit.lion.daongil.presentation.emergency.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class EmergencyMapViewModelFactory(): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EmergencyMapViewModel::class.java)){
            return EmergencyMapViewModel() as T
        }
        throw IllegalArgumentException("unknown ViewModel class")
    }
}