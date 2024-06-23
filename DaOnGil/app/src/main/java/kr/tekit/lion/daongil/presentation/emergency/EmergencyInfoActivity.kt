package kr.tekit.lion.daongil.presentation.emergency

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.fragment.app.activityViewModels
import kr.tekit.lion.daongil.R
import kr.tekit.lion.daongil.databinding.ActivityEmergencyInfoBinding
import kr.tekit.lion.daongil.domain.model.EmergencyMapInfo
import kr.tekit.lion.daongil.presentation.emergency.fragment.AedInfoFragment
import kr.tekit.lion.daongil.presentation.emergency.fragment.EmergencyInfoFragment
import kr.tekit.lion.daongil.presentation.emergency.fragment.PharmacyInfoFragment
import kr.tekit.lion.daongil.presentation.emergency.vm.EmergencyInfoViewModel
import kr.tekit.lion.daongil.presentation.emergency.vm.EmergencyInfoViewModelFactory

class EmergencyInfoActivity : AppCompatActivity() {

    private val binding: ActivityEmergencyInfoBinding by lazy {
        ActivityEmergencyInfoBinding.inflate(layoutInflater)
    }

    private val viewModel: EmergencyInfoViewModel by viewModels{ EmergencyInfoViewModelFactory() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val infoType = intent.getStringExtra("infoType")
        val data = intent.getSerializableExtra("data") as EmergencyMapInfo
        if(infoType.equals("hospital")){
            data.emergencyId?.let { viewModel.getEmergencyMessage(it) }
        }
        replaceFragment()
    }

    private fun replaceFragment(){

        val name = intent.getStringExtra("infoType")
        Log.d("test", name.toString())
        val fragmentManager = supportFragmentManager.beginTransaction()

        when(name) {
            "hospital" -> fragmentManager.replace(R.id.emergency_info_container, EmergencyInfoFragment())
            "aed" -> fragmentManager.replace(R.id.emergency_info_container, AedInfoFragment())
            "pharmacy" -> fragmentManager.replace(R.id.emergency_info_container, PharmacyInfoFragment())
        }

        fragmentManager.commit()
    }
}