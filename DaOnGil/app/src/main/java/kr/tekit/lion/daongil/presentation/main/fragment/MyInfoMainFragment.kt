package kr.tekit.lion.daongil.presentation.main.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import kr.tekit.lion.daongil.R
import kr.tekit.lion.daongil.databinding.FragmentMyInfoMainBinding

class MyInfoMainFragment : Fragment(R.layout.fragment_my_info_main) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentMyInfoMainBinding.bind(view)
    }
}