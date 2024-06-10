package kr.tekit.lion.daongil.presentation.emergency.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import kr.tekit.lion.daongil.R
import kr.tekit.lion.daongil.databinding.FragmentAedInfoBinding
import kr.tekit.lion.daongil.domain.model.EmergencyBottom


class AedInfoFragment : Fragment(R.layout.fragment_aed_info) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentAedInfoBinding.bind(view)

        binding.toolbarAedInfo.setNavigationOnClickListener {
            requireActivity().finish()
        }

        val data = requireActivity().intent.getSerializableExtra("data") as? EmergencyBottom

        with(binding) {
            aedName.text = data?.aedList?.aedName
            aedAddress.text = data?.aedList?.aedAdress
            aedLocationText.text = data?.aedList?.aedLocation
            aedMonTime.text = data?.aedList?.monTime
            aedTueTime.text = data?.aedList?.tueTime
            aedWedTime.text = data?.aedList?.wedTime
            aedThuTime.text = data?.aedList?.thuTime
            aedFriTime.text = data?.aedList?.friTime
            aedSatTime.text = data?.aedList?.satTime
            aedSunTime.text = data?.aedList?.sunTime
            aedHolTime.text = data?.aedList?.holTime

            aedSunWeekText.text = "일요일은 ${data?.aedList?.sunAvailable}에 사용이 가능합니다."

            emergencyCall.setOnClickListener {
                val phoneNumber = data?.aedList?.aedTel
                if (!phoneNumber.isNullOrBlank() || !phoneNumber.isNullOrEmpty()) {
                    val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phoneNumber"))
                    startActivity(intent)
                }
            }

            mainEmergencyCall.setOnClickListener {
                val phoneNumber = data?.aedList?.managerTel
                if (!phoneNumber.isNullOrBlank() || !phoneNumber.isNullOrEmpty()) {
                    val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phoneNumber"))
                    startActivity(intent)
                }
            }
        }

    }

    private fun showSnackbar(binding: FragmentAedInfoBinding, message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG)
            .setBackgroundTint(ContextCompat.getColor(requireContext(), R.color.text_secondary))
            .show()
    }
}