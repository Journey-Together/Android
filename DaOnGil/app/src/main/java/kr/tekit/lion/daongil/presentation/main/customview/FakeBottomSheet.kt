package kr.tekit.lion.daongil.presentation.main.customview

import android.content.Context
import android.content.Intent
import kr.tekit.lion.daongil.databinding.EmergencyBottomSheetLayoutBinding
import kr.tekit.lion.daongil.domain.model.FakeAroundPlace
import kr.tekit.lion.daongil.presentation.emergency.EmergencyInfoActivity

class FakeBottomSheet(
    private val binding: EmergencyBottomSheetLayoutBinding,
    private val pharmacyBottomList: List<FakeAroundPlace>
) {
    private val pharmacyBottomAdapter: FakeBottomAdapter by lazy {
        FakeBottomAdapter(pharmacyBottomList,
            itemClickListener = { position ->
                val context: Context = binding.root.context
                val intent = Intent(context, EmergencyInfoActivity::class.java)
                intent.putExtra("infoType", "pharmacy")
                context.startActivity(intent)
            })
    }

    fun setRecyclerView() {
        with(binding) {
            emergencyBottomRv.adapter = pharmacyBottomAdapter
            pharmacyBottomAdapter.notifyDataSetChanged()
        }
    }

    fun recyclerViewTopButton() {
        with(binding) {
            emergencyBottomSheetHead.setOnClickListener {
                emergencyBottomRv.scrollToPosition(0)
            }
        }
    }
}