package kr.tekit.lion.daongil.presentation.emergency

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kr.tekit.lion.daongil.R
import kr.tekit.lion.daongil.databinding.ActivityEmergencyMapBinding

class EmergencyMapActivity : AppCompatActivity() {

    private val binding: ActivityEmergencyMapBinding by lazy {
        ActivityEmergencyMapBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val mapType = intent.getStringExtra("mapType").toString()
        setToolbar(mapType)


    }

    private fun setToolbar(type: String){
        with(binding){

            toolbarEmergencyMap.setNavigationOnClickListener {
                finish()
            }

            if(type.equals("Emergency")){
                titleEmergencyMap.setText(R.string.text_emergency_aed)
            }

            if(type.equals("Pharmacy")){
                titleEmergencyMap.setText(R.string.text_emergency_pharmacy)
            }
        }
    }

    /*private fun testDetail(){
        binding.detailButton.setOnClickListener {
            val intent = Intent(this, EmergencyInfoActivity::class.java)
            intent.putExtra("infoType", "Emergency")
            startActivity(intent)
        }

        binding.detailButton2.setOnClickListener {
            val intent = Intent(this, EmergencyInfoActivity::class.java)
            intent.putExtra("infoType", "Aed")
            startActivity(intent)
        }

        binding.detailButton3.setOnClickListener {
            val intent = Intent(this, EmergencyInfoActivity::class.java)
            intent.putExtra("infoType", "Pharmacy")
            startActivity(intent)
        }
    }*/
}