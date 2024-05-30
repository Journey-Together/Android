package kr.tekit.lion.daongil.presentation.emergency.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kr.tekit.lion.daongil.R
import kr.tekit.lion.daongil.databinding.FragmentEmergencyInfoBinding
import kr.tekit.lion.daongil.domain.model.EmergencyMessage
import kr.tekit.lion.daongil.presentation.emergency.adapter.EmergencyMessageAdapter

class EmergencyInfoFragment : Fragment(R.layout.fragment_emergency_info) {

    val emergencyMessageList: List<EmergencyMessage> = listOf(
        EmergencyMessage("소아전담 전문의 부재로 소아환자 진료제한 있으니 사전 연락 필히 요청 부탁드립니다.", "2024.05.31 12:15:01"),
        EmergencyMessage("안과 평일 야간(18:00~19:00), 주말 해당 과 사정으로 수용 불가", "2024.05.31 12:15:01"),
        EmergencyMessage("해당 과 사정으로 인해 두부외상 환자 수용 불가", "2024.05.31 12:15:01"),
        EmergencyMessage("모든 성형외과 진료 불가 (안면외상, 안면 골절, 열상 봉합 등)- 성형외과 의료진 부재", "2024.05.31 12:15:01"),
        EmergencyMessage("[부인과] 해당 과 사정으로 F/U포함 수용 불가", "2024.05.31 12:15:01"),
        EmergencyMessage("간담췌외과 인력부족으로 진료제한 있습니다", "2024.05.31 12:15:01"),
        EmergencyMessage("해당과 인력 부족으로 장중첩/폐색(영유아), 소아 수술 불가합니다.", "2024.05.31 12:15:01"),
        EmergencyMessage("비뇨기과 인력부족으로 진료 제한 있습니다. 사전연락 부탁드립니다", "2024.05.31 12:15:01"),
        EmergencyMessage("OS 진료 제한있으니 이송 전 사전연락 부탁드립니다.", "2024.05.31 12:15:01"),
        EmergencyMessage("신과 인력부족으로 입원 불가하오니 참고하여 주시기 바랍니다.", "2024.05.31 12:15:01")
    )

    private val emergencyMessageadapter: EmergencyMessageAdapter by lazy {
        EmergencyMessageAdapter(emergencyMessageList)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentEmergencyInfoBinding.bind(view)

        with(binding){
            emergencyMessageCount.text = emergencyMessageList.size.toString()
            emergencyMessageRV.adapter = emergencyMessageadapter

        }
    }
}