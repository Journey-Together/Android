package kr.tekit.lion.daongil.presentation.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import kr.tekit.lion.daongil.R
import kr.tekit.lion.daongil.presentation.main.vm.MainViewModel
import kr.tekit.lion.daongil.presentation.main.vm.MainViewModelFactory

class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModels { MainViewModelFactory(this) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel
    }
}