package kr.tekit.lion.daongil.presentation.emergency.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kr.tekit.lion.daongil.R
import kr.tekit.lion.daongil.databinding.FragmentAedInfoBinding


class AedInfoFragment : Fragment(R.layout.fragment_aed_info) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentAedInfoBinding.bind(view)
    }
}