package kr.tekit.lion.daongil.presentation.emergency

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kr.tekit.lion.daongil.R
import kr.tekit.lion.daongil.databinding.ActivityEmergencyInfoBinding
import kr.tekit.lion.daongil.presentation.emergency.fragment.AedInfoFragment
import kr.tekit.lion.daongil.presentation.emergency.fragment.EmergencyInfoFragment
import kr.tekit.lion.daongil.presentation.emergency.fragment.PharmacyInfoFragment

class EmergencyInfoActivity : AppCompatActivity() {

    private val binding: ActivityEmergencyInfoBinding by lazy {
        ActivityEmergencyInfoBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        replaceFragment()
    }

    fun replaceFragment(){

        val name = intent.getStringExtra("infoType")
        Log.d("test", name.toString())
        val fragmentManager = supportFragmentManager.beginTransaction()

        when(name) {
            "Emergency" -> fragmentManager.replace(R.id.emergency_info_container, EmergencyInfoFragment())
            "Aed" -> fragmentManager.replace(R.id.emergency_info_container, AedInfoFragment())
            "Pharmacy" -> fragmentManager.replace(R.id.emergency_info_container, PharmacyInfoFragment())
        }

        fragmentManager.commit()
    }
}