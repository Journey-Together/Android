package kr.tekit.lion.daongil.presentation.main

import android.os.Bundle
import android.util.Log
import android.view.View
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kr.tekit.lion.daongil.R
import kr.tekit.lion.daongil.databinding.CategoryBottomSheetLayoutBinding

class CategoryBottomSheet(
    private val selectedCategory: String,
    private val itemClick: (List<String>) -> Unit
) : BottomSheetDialogFragment(R.layout.category_bottom_sheet_layout) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = CategoryBottomSheetLayoutBinding.bind(view)

        setOptions(binding)
    }

    private fun setOptions(binding: CategoryBottomSheetLayoutBinding) {
        with(binding) {
            when (selectedCategory) {
                Category.PHYSICAL_DISABILITY.type,
                Category.ELDERLY_PEOPLE.type -> {
                    chipGroupPhysicalDisability.visibility = View.VISIBLE
                    chipGroupVisualImpairment.visibility = View.GONE
                    chipGroupHearingImpairment.visibility = View.GONE
                    chipGroupInfantFamily.visibility = View.GONE
                }

                Category.HEARING_IMPAIRMENT.type -> {
                    chipGroupHearingImpairment.visibility = View.VISIBLE
                    chipGroupPhysicalDisability.visibility = View.GONE
                    chipGroupVisualImpairment.visibility = View.GONE
                    chipGroupInfantFamily.visibility = View.GONE
                }

                Category.VISUAL_IMPAIRMENT.type -> {
                    chipGroupVisualImpairment.visibility = View.VISIBLE
                    chipGroupPhysicalDisability.visibility = View.GONE
                    chipGroupHearingImpairment.visibility = View.GONE
                    chipGroupInfantFamily.visibility = View.GONE
                }

                Category.INFANT_FAMILY.type -> {
                    chipGroupInfantFamily.visibility = View.VISIBLE
                    chipGroupPhysicalDisability.visibility = View.GONE
                    chipGroupVisualImpairment.visibility = View.GONE
                    chipGroupHearingImpairment.visibility = View.GONE
                }
            }

            allChip.setOnClickListener {
                when (selectedCategory) {
                    Category.PHYSICAL_DISABILITY.type,
                    Category.ELDERLY_PEOPLE.type -> {
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

                    Category.VISUAL_IMPAIRMENT.type -> {
                        if(allChip.isChecked) allChip.isChecked = false
                        chipGroupVisualImpairment.check(R.id.braileblock_chip)
                        chipGroupVisualImpairment.check(R.id.help_dog_chip)
                        chipGroupVisualImpairment.check(R.id.guide_chip)
                        chipGroupVisualImpairment.check(R.id.audio_guide_chip)
                        chipGroupVisualImpairment.check(R.id.big_print_chip)
                        chipGroupVisualImpairment.check(R.id.braile_promotion_chip)
                        chipGroupVisualImpairment.check(R.id.guide_system_chip)
                    }

                    Category.HEARING_IMPAIRMENT.type -> {
                        if(allChip.isChecked) allChip.isChecked = false
                        chipGroupHearingImpairment.check(R.id.sign_guide_chip)
                        chipGroupHearingImpairment.check(R.id.video_guide_chip)
                        chipGroupHearingImpairment.check(R.id.hearing_room_chip)
                    }

                    Category.INFANT_FAMILY.type -> {
                        if(allChip.isChecked) allChip.isChecked = false
                        chipGroupInfantFamily.check(R.id.stroller_chip)
                        chipGroupInfantFamily.check(R.id.lactation_room_chip)
                        chipGroupInfantFamily.check(R.id.baby_spare_chair_chip)
                    }
                }
            }

            btnReset.setOnClickListener {
                when (selectedCategory) {
                    Category.PHYSICAL_DISABILITY.type,
                    Category.ELDERLY_PEOPLE.type -> {
                        allChip.isChecked = false
                        chipGroupPhysicalDisability.clearCheck()
                    }

                    Category.HEARING_IMPAIRMENT.type -> {
                        allChip.isChecked = false
                        chipGroupHearingImpairment.clearCheck()
                    }

                    Category.VISUAL_IMPAIRMENT.type -> {
                        allChip.isChecked = false
                        chipGroupVisualImpairment.clearCheck()
                    }

                    Category.INFANT_FAMILY.type -> {
                        allChip.isChecked = false
                        chipGroupInfantFamily.clearCheck()
                    }
                }
            }

            btnSubmit.setOnClickListener {
                val selectedChipIds = when (selectedCategory) {
                    Category.PHYSICAL_DISABILITY.type,
                    Category.ELDERLY_PEOPLE.type -> {
                        chipGroupPhysicalDisability.checkedChipIds
                    }

                    Category.HEARING_IMPAIRMENT.type -> {
                        allChip.isChecked = false
                        chipGroupHearingImpairment.checkedChipIds
                    }

                    Category.VISUAL_IMPAIRMENT.type -> {
                        allChip.isChecked = false
                        chipGroupVisualImpairment.checkedChipIds
                    }

                    Category.INFANT_FAMILY.type -> {
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

enum class Category(val type: String) {
    PHYSICAL_DISABILITY("physical_disability"),
    HEARING_IMPAIRMENT("hearing_impairment"),
    VISUAL_IMPAIRMENT("visual_impairment"),
    INFANT_FAMILY("infant_family"),
    ELDERLY_PEOPLE("elderly_people"),
    CLEAR("clear"),
}