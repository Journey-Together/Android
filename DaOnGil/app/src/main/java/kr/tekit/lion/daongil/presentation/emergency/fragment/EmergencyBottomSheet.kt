package kr.tekit.lion.daongil.presentation.emergency.fragment

import android.util.Log
import kr.tekit.lion.daongil.databinding.EmergencyBottomSheetLayoutBinding

class EmergencyBottomSheet(private val emergencyBottomSheet: EmergencyBottomSheetLayoutBinding) {

   fun testClick() {
      emergencyBottomSheet.emergencyBottomSheetHead.setOnClickListener {
         Log.d("test2222", "test2222")
      }
   }

}