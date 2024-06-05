package kr.tekit.lion.daongil.presentation.test

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import kr.tekit.lion.daongil.R
import kr.tekit.lion.daongil.presentation.test.vm.TestViewModel
import kr.tekit.lion.daongil.presentation.test.vm.TestViewModelFactory

class TestActivity : AppCompatActivity() {
    private val viewModel: TestViewModel by viewModels{ TestViewModelFactory() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)
        viewModel
    }
}