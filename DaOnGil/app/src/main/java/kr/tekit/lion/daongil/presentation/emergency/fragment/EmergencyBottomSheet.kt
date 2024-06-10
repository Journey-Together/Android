package kr.tekit.lion.daongil.presentation.emergency.fragment

import android.content.Context
import android.content.Intent
import kr.tekit.lion.daongil.databinding.EmergencyBottomSheetLayoutBinding
import kr.tekit.lion.daongil.domain.model.EmergencyMapInfo
import kr.tekit.lion.daongil.presentation.emergency.EmergencyInfoActivity
import kr.tekit.lion.daongil.presentation.emergency.adapter.EmergencyBottomAdapter

class EmergencyBottomSheet(
   private val binding: EmergencyBottomSheetLayoutBinding,
   private val emergencyMapInfoList: List<EmergencyMapInfo>
) {

   private val emergencyBottomadapter: EmergencyBottomAdapter by lazy {
      EmergencyBottomAdapter(emergencyMapInfoList,
         itemClickListener = { position ->
            val context: Context = binding.root.context
            val intent = Intent(context, EmergencyInfoActivity::class.java)
            intent.putExtra("infoType", emergencyMapInfoList[position].emergencyType)
            intent.putExtra("data", emergencyMapInfoList[position])
            intent.putExtra("id", emergencyMapInfoList[position].emergencyId)
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

   fun recyclerViewTopButton() {
      with(binding){
         emergencyBottomSheetHead.setOnClickListener {
            emergencyBottomRv.scrollToPosition(0)
         }
      }
   }

}