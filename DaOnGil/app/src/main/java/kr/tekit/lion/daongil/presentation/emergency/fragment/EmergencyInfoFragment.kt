package kr.tekit.lion.daongil.presentation.emergency.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kr.tekit.lion.daongil.R
import kr.tekit.lion.daongil.databinding.FragmentEmergencyInfoBinding

class EmergencyInfoFragment : Fragment(R.layout.fragment_emergency_info) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentEmergencyInfoBinding.bind(view)
    }
}