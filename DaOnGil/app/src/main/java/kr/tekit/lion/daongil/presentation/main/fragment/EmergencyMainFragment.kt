package kr.tekit.lion.daongil.presentation.main.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import kr.tekit.lion.daongil.R
import kr.tekit.lion.daongil.databinding.FragmentEmergencyMainBinding
import kr.tekit.lion.daongil.presentation.emergency.EmergencyMapActivity

class EmergencyMainFragment : Fragment(R.layout.fragment_emergency_main) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentEmergencyMainBinding.bind(view)

        moveMap(binding)
    }

    private fun moveMap(binding: FragmentEmergencyMainBinding){
        binding.emrAedCard.setOnClickListener {
            val intent = Intent(requireActivity(), EmergencyMapActivity::class.java)
            intent.putExtra("mapType", "Emergency")
            startActivity(intent)
        }

        binding.pharmacyCard.setOnClickListener {
            val intent = Intent(requireActivity(), EmergencyMapActivity::class.java)
            intent.putExtra("mapType", "pharmacy")
            startActivity(intent)
        }
    }
}