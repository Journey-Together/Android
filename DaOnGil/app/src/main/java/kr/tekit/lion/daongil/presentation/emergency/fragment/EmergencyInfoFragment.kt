package kr.tekit.lion.daongil.presentation.emergency.fragment

import android.content.Intent
import android.content.res.ColorStateList
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import com.google.android.material.snackbar.Snackbar
import kr.tekit.lion.daongil.R
import kr.tekit.lion.daongil.databinding.FragmentEmergencyInfoBinding
import kr.tekit.lion.daongil.domain.model.EmergencyBottom
import kr.tekit.lion.daongil.presentation.emergency.adapter.EmergencyMessageAdapter
import kr.tekit.lion.daongil.presentation.emergency.vm.EmergencyInfoViewModel
import kr.tekit.lion.daongil.presentation.emergency.vm.EmergencyInfoViewModelFactory
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class EmergencyInfoFragment : Fragment(R.layout.fragment_emergency_info) {

    private val hospitalId by lazy {
        (requireActivity().intent.getSerializableExtra("data") as EmergencyBottom).emergencyId
    }
    private val viewModel: EmergencyInfoViewModel by viewModels{ EmergencyInfoViewModelFactory(
        hospitalId.toString()
    ) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentEmergencyInfoBinding.bind(view)

        val data = requireActivity().intent.getSerializableExtra("data") as EmergencyBottom

        with(binding){

            viewModel.messageList.observe(viewLifecycleOwner) { messageList ->

                if(messageList.size < 1) {
                    emrDefaultMessage.visibility = View.VISIBLE
                    emergencyMessageCount.text = messageList.size.toString()
                    emergencyMessageDate.text = getCurrentFormattedDateTime()

                } else {
                    emergencyMessageRV.visibility = View.VISIBLE
                    val emergencyMessageadapter =  EmergencyMessageAdapter(messageList)
                    emergencyMessageCount.text = messageList.size.toString()
                    emergencyMessageRV.adapter = emergencyMessageadapter
                }

            }

            emergencyName.text = data.emergencyList?.hospitalName
            emergencyAddress.text = data.emergencyList?.hospitalAddress
            emergencyUpdate.text = data.emergencyList?.lastUpdateDate
            emergency.text = data.emergencyList?.emergencyCount.toString() + " / " + data.emergencyList?.emergencyAllCount.toString()
            kidEmergency.text =
                if (data.emergencyList?.emergencyKid.isNullOrEmpty()) "운영하지 않습니다."
                else data.emergencyList?.emergencyKidCount.toString() + " / " + data.emergencyList?.emergencyKidAllCount.toString()
            emergencyDialysis.text = data.emergencyList?.dialysis
            emergencyBirth.text = data.emergencyList?.earlyBirth
            emergencyBurns.text = data.emergencyList?.burns

            emergencyCall.setOnClickListener {
                val phoneNumber = data.emergencyList?.emergencyTel
                if (!phoneNumber.isNullOrBlank() || !phoneNumber.isNullOrEmpty()) {
                    val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phoneNumber"))
                    startActivity(intent)
                } else {
                    showSnackbar(this, "전화번호가 존재하지 않습니다.")
                }
            }

            mainEmergencyCall.setOnClickListener {
                val phoneNumber = data.emergencyList?.hospitalTel
                if (!phoneNumber.isNullOrBlank() || !phoneNumber.isNullOrEmpty()) {
                    val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phoneNumber"))
                    startActivity(intent)
                } else {
                    showSnackbar(this, "전화번호가 존재하지 않습니다.")
                }
            }

            data.emergencyList?.emergencyBed?.let { it ->
                val emergencyKidBedIcon = binding.emergencyRadius
                val colorResId = when {
                    it >= 80 -> R.color.emergency_green
                    it >= 50 -> R.color.emergency_yellow
                    else -> R.color.emergency_red
                }
                val colorStateList = ColorStateList.valueOf(ContextCompat.getColor(requireContext(), colorResId))
                emergencyKidBedIcon.setBackgroundTintList(colorStateList)
            }

            data.emergencyList?.emergencyKidBed?.let { it ->
                val emergencyKidBedIcon = binding.kidEmergencyRadius
                val colorResId = when {
                    it >= 80 -> R.color.emergency_green
                    it >= 50 -> R.color.emergency_yellow
                    else -> R.color.emergency_red
                }
                val colorStateList = ColorStateList.valueOf(ContextCompat.getColor(requireContext(), colorResId))
                emergencyKidBedIcon.setBackgroundTintList(colorStateList)
            }

            toolbarEmergencyInfo.setNavigationOnClickListener {
                requireActivity().finish()
            }
        }
    }

    private fun getCurrentFormattedDateTime(): String {
        // 현재 시간 가져오기
        val currentDateTime = LocalDateTime.now()

        // 원하는 형식 정의
        val formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss")

        // 포맷팅된 문자열 반환
        return currentDateTime.format(formatter)
    }

    private fun showSnackbar(binding: FragmentEmergencyInfoBinding, message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG)
            .setBackgroundTint(ContextCompat.getColor(requireContext(), R.color.text_secondary))
            .show()
    }

}