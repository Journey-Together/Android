package kr.tekit.lion.daongil.presentation.scheduleform.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import kr.tekit.lion.daongil.R
import kr.tekit.lion.daongil.databinding.FragmentScheduleConfirmFormBinding
import kr.tekit.lion.daongil.presentation.scheduleform.vm.ScheduleFormViewModel
import kr.tekit.lion.daongil.presentation.scheduleform.vm.ScheduleFormViewModelFactory

class ScheduleConfirmFormFragment : Fragment(R.layout.fragment_schedule_confirm_form) {
    private val scheduleFormViewModel: ScheduleFormViewModel by activityViewModels{
        ScheduleFormViewModelFactory()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentScheduleConfirmFormBinding.bind(view)
    }
}