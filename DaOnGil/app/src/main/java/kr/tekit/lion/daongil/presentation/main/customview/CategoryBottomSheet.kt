package kr.tekit.lion.daongil.presentation.main.customview

import android.os.Bundle
import android.view.View
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip
import kr.tekit.lion.daongil.R
import kr.tekit.lion.daongil.databinding.CategoryBottomSheetLayoutBinding
import kr.tekit.lion.daongil.presentation.main.vm.Category

class CategoryBottomSheet(
    private val selectedOption : List<Int>,
    private val selectedCategory: Category,
    private val itemClick: ( List<Int> ) -> Unit
) : BottomSheetDialogFragment(R.layout.category_bottom_sheet_layout) {
    private val selectedOptions = ArrayList<String>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = CategoryBottomSheetLayoutBinding.bind(view)

        initChips(binding)
        setOptions(binding)
    }

    private fun initChips(binding: CategoryBottomSheetLayoutBinding){
        if(selectedOption.isNotEmpty()){
            selectedOption.map {
                when (selectedCategory) {
                    Category.PhysicalDisability-> {
                        val chip = binding.chipGroupPhysicalDisability.findViewById<Chip>(it)
                        chip.isChecked = true
                    }
                    Category.HearingImpairment -> {
                        val chip = binding.chipGroupHearingImpairment.findViewById<Chip>(it)
                        chip.isChecked = true
                    }
                    Category.VisualImpairment -> {
                        val chip = binding.chipGroupVisualImpairment.findViewById<Chip>(it)
                        chip.isChecked = true
                    }
                    Category.InfantFamily -> {
                        val chip = binding.chipGroupInfantFamily.findViewById<Chip>(it)
                        chip.isChecked = true
                    }
                    Category.ElderlyPeople -> {
                        val chip = binding.chipGroupElderlyPerson.findViewById<Chip>(it)
                        chip.isChecked = true
                    }
                }
            }
        }
    }


    private fun setOptions(binding: CategoryBottomSheetLayoutBinding) {
        with(binding) {
            when (selectedCategory) {
                Category.PhysicalDisability-> {
                    chipGroupPhysicalDisability.visibility = View.VISIBLE
                    chipGroupVisualImpairment.visibility = View.GONE
                    chipGroupHearingImpairment.visibility = View.GONE
                    chipGroupInfantFamily.visibility = View.GONE
                    chipGroupElderlyPerson.visibility = View.GONE
                }

                Category.HearingImpairment -> {
                    chipGroupHearingImpairment.visibility = View.VISIBLE
                    chipGroupPhysicalDisability.visibility = View.GONE
                    chipGroupVisualImpairment.visibility = View.GONE
                    chipGroupInfantFamily.visibility = View.GONE
                    chipGroupElderlyPerson.visibility = View.GONE

                }

                Category.VisualImpairment -> {
                    chipGroupVisualImpairment.visibility = View.VISIBLE
                    chipGroupPhysicalDisability.visibility = View.GONE
                    chipGroupHearingImpairment.visibility = View.GONE
                    chipGroupInfantFamily.visibility = View.GONE
                    chipGroupElderlyPerson.visibility = View.GONE

                }

                Category.InfantFamily -> {
                    chipGroupInfantFamily.visibility = View.VISIBLE
                    chipGroupPhysicalDisability.visibility = View.GONE
                    chipGroupVisualImpairment.visibility = View.GONE
                    chipGroupHearingImpairment.visibility = View.GONE
                    chipGroupElderlyPerson.visibility = View.GONE

                }
                Category.ElderlyPeople ->{
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
                    Category.PhysicalDisability -> {
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

                    Category.VisualImpairment -> {
                        if(allChip.isChecked) allChip.isChecked = false
                        chipGroupVisualImpairment.check(R.id.braileblock_chip)
                        chipGroupVisualImpairment.check(R.id.help_dog_chip)
                        chipGroupVisualImpairment.check(R.id.guide_chip)
                        chipGroupVisualImpairment.check(R.id.audio_guide_chip)
                        chipGroupVisualImpairment.check(R.id.big_print_chip)
                        chipGroupVisualImpairment.check(R.id.braile_promotion_chip)
                        chipGroupVisualImpairment.check(R.id.guide_system_chip)
                    }

                    Category.HearingImpairment -> {
                        if(allChip.isChecked) allChip.isChecked = false
                        chipGroupHearingImpairment.check(R.id.sign_guide_chip)
                        chipGroupHearingImpairment.check(R.id.video_guide_chip)
                    }

                    Category.InfantFamily -> {
                        if(allChip.isChecked) allChip.isChecked = false
                        chipGroupInfantFamily.check(R.id.stroller_chip)
                        chipGroupInfantFamily.check(R.id.lactation_room_chip)
                        chipGroupInfantFamily.check(R.id.baby_spare_chair_chip)
                    }
                    Category.ElderlyPeople ->{
                        if(allChip.isChecked) allChip.isChecked = false
                        chipGroupInfantFamily.check(R.id.elderly_wheelchair_chip)
                        chipGroupInfantFamily.check(R.id.lend_chip)
                    }
                }
            }

            btnReset.setOnClickListener {
                when (selectedCategory) {
                    Category.PhysicalDisability-> {
                        allChip.isChecked = false
                        chipGroupPhysicalDisability.clearCheck()
                    }

                    Category.HearingImpairment -> {
                        allChip.isChecked = false
                        chipGroupHearingImpairment.clearCheck()
                    }

                    Category.VisualImpairment -> {
                        allChip.isChecked = false
                        chipGroupVisualImpairment.clearCheck()
                    }

                    Category.InfantFamily -> {
                        allChip.isChecked = false
                        chipGroupInfantFamily.clearCheck()
                    }
                    Category.ElderlyPeople -> {
                        allChip.isChecked = false
                        chipGroupInfantFamily.clearCheck()
                    }
                }
            }

            btnSubmit.setOnClickListener {
                val selectedChipIds = when (selectedCategory) {
                    is Category.PhysicalDisability -> {
                        chipGroupPhysicalDisability.checkedChipIds
                    }

                    is Category.HearingImpairment -> {
                        allChip.isChecked = false
                        chipGroupHearingImpairment.checkedChipIds
                    }

                    is Category.VisualImpairment -> {
                        allChip.isChecked = false
                        chipGroupVisualImpairment.checkedChipIds
                    }

                    is Category.InfantFamily -> {
                        allChip.isChecked = false
                        chipGroupInfantFamily.checkedChipIds
                    }

                    is Category.ElderlyPeople -> {
                        allChip.isChecked = false
                        chipGroupElderlyPerson.checkedChipIds
                    }
                }
                if (selectedChipIds.isNotEmpty()) {
                    itemClick.invoke(selectedChipIds)
                }
                dismiss()
            }

        }
    }

    override fun getTheme(): Int {
        return R.style.category_bottom_sheet_dialog_theme
    }
}
