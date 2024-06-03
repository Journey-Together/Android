package kr.tekit.lion.daongil.presentation.scheduleform.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import kr.tekit.lion.daongil.R
import kr.tekit.lion.daongil.databinding.FragmentFormSearchBinding


class FormSearchFragment : Fragment(R.layout.fragment_form_search) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentFormSearchBinding.bind(view)
    }

}