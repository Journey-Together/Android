package kr.tekit.lion.daongil.presentation.scheduleform.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import kr.tekit.lion.daongil.R
import kr.tekit.lion.daongil.databinding.FragmentNameAndPeriodFormBinding

class NameAndPeriodFormFragment : Fragment(R.layout.fragment_name_and_period_form) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentNameAndPeriodFormBinding.bind(view)

    }
}