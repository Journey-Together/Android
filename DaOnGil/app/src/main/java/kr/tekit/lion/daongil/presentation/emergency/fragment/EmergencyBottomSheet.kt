package kr.tekit.lion.daongil.presentation.emergency.fragment

import android.util.Log
import kr.tekit.lion.daongil.databinding.EmergencyBottomSheetLayoutBinding
import kr.tekit.lion.daongil.domain.model.EmergencyBottom
import kr.tekit.lion.daongil.presentation.emergency.adapter.EmergencyBottomAdapter

class EmergencyBottomSheet(
   private val binding: EmergencyBottomSheetLayoutBinding,
   private val emergencyBottomList: List<EmergencyBottom>
) {

   private val emergencyBottomadapter: EmergencyBottomAdapter by lazy {
      EmergencyBottomAdapter(emergencyBottomList,
         itemClickListener = { id ->
            Log.d("testId", id)
         }
      )
   }

   fun setRecyclerView(){
      with(binding){
         emergencyBottomRv.adapter = emergencyBottomadapter
      }
   }

}