package kr.tekit.lion.daongil.presentation.main.customview

import android.os.Bundle
import android.view.View
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kr.tekit.lion.daongil.R
import kr.tekit.lion.daongil.databinding.CategoryBottomSheetLayoutBinding

class CategoryBottomSheet(
    private val selectedCategory: Int,
    private val itemClick: (List<String>) -> Unit
) : BottomSheetDialogFragment(R.layout.category_bottom_sheet_layout) {
    private val selectedOptions = ArrayList<String>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = CategoryBottomSheetLayoutBinding.bind(view)
        var physicalDisabilityBtnState = ButtonState.UNSELECTED

        setOptions(binding)
    }

    private fun setOptions(binding: CategoryBottomSheetLayoutBinding) {
        with(binding) {
            when (selectedCategory) {
                Category.PhysicalDisability.type -> {
                    chipGroupPhysicalDisability.visibility = View.VISIBLE
                    chipGroupVisualImpairment.visibility = View.GONE
                    chipGroupHearingImpairment.visibility = View.GONE
                    chipGroupInfantFamily.visibility = View.GONE
                    chipGroupElderlyPerson.visibility = View.GONE
                }

                Category.HearingImpairment.type -> {
                    chipGroupHearingImpairment.visibility = View.VISIBLE
                    chipGroupPhysicalDisability.visibility = View.GONE
                    chipGroupVisualImpairment.visibility = View.GONE
                    chipGroupInfantFamily.visibility = View.GONE
                    chipGroupElderlyPerson.visibility = View.GONE

                }

                Category.VisualImpairment.type -> {
                    chipGroupVisualImpairment.visibility = View.VISIBLE
                    chipGroupPhysicalDisability.visibility = View.GONE
                    chipGroupHearingImpairment.visibility = View.GONE
                    chipGroupInfantFamily.visibility = View.GONE
                    chipGroupElderlyPerson.visibility = View.GONE

                }

                Category.InfantFamily.type -> {
                    chipGroupInfantFamily.visibility = View.VISIBLE
                    chipGroupPhysicalDisability.visibility = View.GONE
                    chipGroupVisualImpairment.visibility = View.GONE
                    chipGroupHearingImpairment.visibility = View.GONE
                    chipGroupElderlyPerson.visibility = View.GONE

                }
                Category.ElderlyPeople.type ->{
                    chipGroupElderlyPerson.visibility = View.VISIBLE
                    chipGroupPhysicalDisability.visibility = View.GONE
                    chipGroupVisualImpairment.visibility = View.GONE
                    chipGroupHearingImpairment.visibility = View.GONE
                    chipGroupInfantFamily.visibility = View.GONE
                }
            }

            allChip.setOnClickListener {
                selectedOptions.clear()

                when (selectedCategory) {
                    Category.PhysicalDisability.type -> {
                        if(allChip.isChecked) allChip.isChecked = false
                        chipGroupPhysicalDisability.check(R.id.wheelchair_chip)
                        chipGroupPhysicalDisability.check(R.id.parking_chip)
                        chipGroupPhysicalDisability.check(R.id.public_transport_chip)
                        chipGroupPhysicalDisability.check(R.id.route_chip)
                        chipGroupPhysicalDisability.check(R.id.ticket_office_chip)
                        chipGroupPhysicalDisability.check(R.id.promotion_chip)
                        chipGroupPhysicalDisability.check(R.id.exit_chip)
                        chipGroupPhysicalDisability.check(R.id.elevator_chip)
                        chipGroupPhysicalDisability.check(R.id.restroom_chip)
                        chipGroupPhysicalDisability.check(R.id.auditorium_chip)
                        chipGroupPhysicalDisability.check(R.id.room_chip)

                    }

                    Category.VisualImpairment.type -> {
                        if(allChip.isChecked) allChip.isChecked = false
                        chipGroupVisualImpairment.check(R.id.braileblock_chip)
                        chipGroupVisualImpairment.check(R.id.help_dog_chip)
                        chipGroupVisualImpairment.check(R.id.guide_chip)
                        chipGroupVisualImpairment.check(R.id.audio_guide_chip)
                        chipGroupVisualImpairment.check(R.id.big_print_chip)
                        chipGroupVisualImpairment.check(R.id.braile_promotion_chip)
                        chipGroupVisualImpairment.check(R.id.guide_system_chip)
                    }

                    Category.HearingImpairment.type -> {
                        if(allChip.isChecked) allChip.isChecked = false
                        chipGroupHearingImpairment.check(R.id.sign_guide_chip)
                        chipGroupHearingImpairment.check(R.id.video_guide_chip)
                    }

                    Category.InfantFamily.type -> {
                        if(allChip.isChecked) allChip.isChecked = false
                        chipGroupInfantFamily.check(R.id.stroller_chip)
                        chipGroupInfantFamily.check(R.id.lactation_room_chip)
                        chipGroupInfantFamily.check(R.id.baby_spare_chair_chip)
                    }
                    Category.ElderlyPeople.type ->{
                        if(allChip.isChecked) allChip.isChecked = false
                        chipGroupInfantFamily.check(R.id.elderly_wheelchair_chip)
                        chipGroupInfantFamily.check(R.id.lend_chip)
                    }
                }
            }

            btnReset.setOnClickListener {
                when (selectedCategory) {
                    Category.PhysicalDisability.type,
                    Category.ElderlyPeople.type -> {
                        allChip.isChecked = false
                        chipGroupPhysicalDisability.clearCheck()
                    }

                    Category.HearingImpairment.type -> {
                        allChip.isChecked = false
                        chipGroupHearingImpairment.clearCheck()
                    }

                    Category.VisualImpairment.type -> {
                        allChip.isChecked = false
                        chipGroupVisualImpairment.clearCheck()
                    }

                    Category.InfantFamily.type -> {
                        allChip.isChecked = false
                        chipGroupInfantFamily.clearCheck()
                    }
                }
            }

            btnSubmit.setOnClickListener {
                val selectedChipIds = when (selectedCategory) {
                    Category.PhysicalDisability.type,
                    Category.ElderlyPeople.type -> {
                        chipGroupPhysicalDisability.checkedChipIds
                    }

                    Category.HearingImpairment.type -> {
                        allChip.isChecked = false
                        chipGroupHearingImpairment.checkedChipIds
                    }

                    Category.VisualImpairment.type -> {
                        allChip.isChecked = false
                        chipGroupVisualImpairment.checkedChipIds
                    }

                    Category.InfantFamily.type -> {
                        allChip.isChecked = false
                        chipGroupInfantFamily.checkedChipIds
                    }
                    else -> emptyList()
                }
                val resourceNames = selectedChipIds.map { n->
                    resources.getResourceEntryName(n)
                }
                itemClick.invoke(resourceNames)
                dismiss()
            }
        }
    }

    override fun getTheme(): Int {
        return R.style.category_bottom_sheet_dialog_theme
    }
}

enum class ButtonState{
    SELECTED,
    UNSELECTED
}

sealed class Category(val type: Int) {
    data object PhysicalDisability : Category(1)
    data object VisualImpairment : Category(2)
    data object HearingImpairment : Category(3)
    data object InfantFamily : Category(4)
    data object ElderlyPeople : Category(5)
}
