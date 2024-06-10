package kr.tekit.lion.daongil.presentation.emergency.fragment

import android.content.Context
import android.content.Intent
import kr.tekit.lion.daongil.databinding.EmergencyBottomSheetLayoutBinding
import kr.tekit.lion.daongil.domain.model.EmergencyBottom
import kr.tekit.lion.daongil.presentation.emergency.EmergencyInfoActivity
import kr.tekit.lion.daongil.presentation.emergency.adapter.EmergencyBottomAdapter

class EmergencyBottomSheet(
   private val binding: EmergencyBottomSheetLayoutBinding,
   private val emergencyBottomList: List<EmergencyBottom>
) {

   private val emergencyBottomadapter: EmergencyBottomAdapter by lazy {
      EmergencyBottomAdapter(emergencyBottomList,
         itemClickListener = { position ->
            val context: Context = binding.root.context
            val intent = Intent(context, EmergencyInfoActivity::class.java)
            intent.putExtra("infoType", emergencyBottomList[position].emergencyType)
            intent.putExtra("data", emergencyBottomList[position])
            intent.putExtra("id", emergencyBottomList[position].emergencyId)
            context.startActivity(intent)
         }
      )
   }

   fun setRecyclerView(){
      with(binding){
         emergencyBottomRv.adapter = emergencyBottomadapter
         emergencyBottomadapter.notifyDataSetChanged()
      }
   }

}