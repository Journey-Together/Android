package kr.tekit.lion.daongil.presentation.emergency.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import kr.tekit.lion.daongil.R
import kr.tekit.lion.daongil.databinding.FragmentPharmacyInfoBinding

class PharmacyInfoFragment : Fragment(R.layout.fragment_pharmacy_info) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentPharmacyInfoBinding.bind(view)

        with(binding.toolbarPharmcyInfo){
            setNavigationOnClickListener {
                requireActivity().finish()
            }
        }
    }
}