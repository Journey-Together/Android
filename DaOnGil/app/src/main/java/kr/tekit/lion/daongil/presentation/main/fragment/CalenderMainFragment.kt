package kr.tekit.lion.daongil.presentation.main.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kr.tekit.lion.daongil.R
import kr.tekit.lion.daongil.databinding.FragmentCalenderMainBinding

class CalenderMainFragment : Fragment(R.layout.fragment_calender_main) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentCalenderMainBinding.bind(view)
    }
}