package kr.tekit.lion.daongil.presentation.scheduleform.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.fragment.findNavController
import kr.tekit.lion.daongil.R
import kr.tekit.lion.daongil.databinding.FragmentNameAndPeriodFormBinding

class NameAndPeriodFormFragment : Fragment(R.layout.fragment_name_and_period_form) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentNameAndPeriodFormBinding.bind(view)

        binding.temButton.setOnClickListener {
            val navController = findNavController()
            // 전환할 화면의 id (graph에 선언된 id)를 넣어준다.
            navController.navigate(R.id.scheduleDetailsFormFragment)
        }
    }
}