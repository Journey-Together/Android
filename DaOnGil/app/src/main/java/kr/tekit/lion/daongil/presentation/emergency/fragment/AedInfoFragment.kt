package kr.tekit.lion.daongil.presentation.emergency.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

        binding.aedName.text = data?.aedList?.aedName
        binding.aedAddress.text = data?.aedList?.aedAdress
        binding.aedLocationText.text = data?.aedList?.aedLocation
        binding.aedMonTime.text = data?.aedList?.monTime
        binding.aedTueTime.text = data?.aedList?.tueTime
        binding.aedWedTime.text = data?.aedList?.wedTime
        binding.aedThuTime.text = data?.aedList?.thuTime
        binding.aedFriTime.text = data?.aedList?.friTime
        binding.aedSatTime.text = data?.aedList?.satTime
        binding.aedSunTime.text = data?.aedList?.sunTime
        binding.aedHolTime.text = data?.aedList?.holTime

        binding.aedSunWeekText.text = "일요일은 ${data?.aedList?.sunAvailable}에 사용이 가능합니다."

        binding.emergencyCall.setOnClickListener {
            val phoneNumber = data?.aedList?.aedTel
            if (!phoneNumber.isNullOrBlank()) {
                val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phoneNumber"))
                startActivity(intent)
            }
        }

        binding.mainEmergencyCall.setOnClickListener {
            val phoneNumber = data?.aedList?.managerTel
            if (!phoneNumber.isNullOrBlank()) {
                val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phoneNumber"))
                startActivity(intent)
            }
        }
    }
}