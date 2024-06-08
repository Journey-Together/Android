package kr.tekit.lion.daongil.presentation.myinfo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import kr.tekit.lion.daongil.R
import kr.tekit.lion.daongil.presentation.myinfo.vm.MyInfoViewModel
import kr.tekit.lion.daongil.presentation.myinfo.vm.MyInfoViewModelFactory

class MyInfoActivity : AppCompatActivity() {
    private val viewModel: MyInfoViewModel by viewModels { MyInfoViewModelFactory() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_info)

        intent.getStringExtra("name")?.let { viewModel.whenGetStringExtra(it) }
    }
}