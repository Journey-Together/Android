package kr.tekit.lion.daongil.presentation.emergency.fragment

import android.content.Context
import android.content.Intent
import kr.tekit.lion.daongil.databinding.EmergencyBottomSheetLayoutBinding
import kr.tekit.lion.daongil.domain.model.PharmacyMapInfo
import kr.tekit.lion.daongil.presentation.emergency.EmergencyInfoActivity
import kr.tekit.lion.daongil.presentation.emergency.adapter.PharmacyBottomAdapter

class PharmacyBottomSheet(
    private val binding: EmergencyBottomSheetLayoutBinding,
    private val pharmacyBottomList: List<PharmacyMapInfo>
) {
    private val pharmacyBottomAdapter: PharmacyBottomAdapter by lazy {
        PharmacyBottomAdapter(pharmacyBottomList,
            itemClickListener = {item ->
                val context: Context = binding.root.context
                val intent = Intent(context, EmergencyInfoActivity::class.java)
                intent.putExtra("infoType", "pharmacy")
                intent.putExtra("data", item)
                context.startActivity(intent)
            })
    }

    fun setRecyclerView(){
        with(binding){
            emergencyBottomRv.adapter = pharmacyBottomAdapter
            pharmacyBottomAdapter.notifyDataSetChanged()
        }
    }
}