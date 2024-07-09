package kr.tekit.lion.daongil.presentation.report

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import com.google.android.material.textfield.TextInputLayout
import kr.tekit.lion.daongil.R
import kr.tekit.lion.daongil.databinding.ActivityReportBinding
import kr.tekit.lion.daongil.presentation.ext.showSoftInput

class ReportActivity : AppCompatActivity() {

    private val binding: ActivityReportBinding by lazy {
        ActivityReportBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        settingToolbar()
        settingRadioBtn()
        settingReportButton()
        settingErrorHandling()
    }

    private fun settingToolbar() {
        binding.toolbarReport.setNavigationOnClickListener {
            finish()
        }
    }

    private fun settingRadioBtn() {
        with(binding) {
            textFieldEtc.isEnabled = false
            val enabledTextColor = textFieldEtc.currentTextColor
            val disabledTextColor = ContextCompat.getColor(this@ReportActivity, R.color.text_quaternary)

            radioGroupReport.setOnCheckedChangeListener { _, checkedId ->
                if(checkedId == R.id.radioButtonEtc) {
                    textFieldEtc.isEnabled = true
                    textFieldEtc.isFocusableInTouchMode = true
                    textFieldEtc.setTextColor(enabledTextColor)
                } else {
                    textFieldEtc.isEnabled = false
                    textFieldEtc.setTextColor(disabledTextColor)
                    if (textInputLayoutEtc.error != null) {
                        clearErrorMessage(textInputLayoutEtc)
                    }
                }
            }
        }
    }

    private fun settingReportButton() {
        with(binding) {
            buttonReport.setOnClickListener {
                // isFormValid 테스트 코드
                if (radioGroupReport.checkedRadioButtonId == R.id.radioButtonEtc) {
                    if (isFormValid()) {
                        finish()
                    }
                } else {
                    finish()
                }
            }
        }
    }

    private fun isFormValid(): Boolean {
       return if (binding.textFieldEtc.text.isNullOrBlank()) {
           binding.textInputLayoutEtc.error = getString(R.string.text_report_error_message)
           binding.textFieldEtc.requestFocus()
           this@ReportActivity.showSoftInput(binding.textFieldEtc)
           false
       } else {
           true
       }
    }

    private fun settingErrorHandling() {
        binding.textFieldEtc.addTextChangedListener {
            clearErrorMessage(binding.textInputLayoutEtc)
        }
    }

    private fun clearErrorMessage(textInputLayout: TextInputLayout) {
        textInputLayout.error = null
        textInputLayout.boxStrokeColor = ContextCompat.getColor(this, R.color.button_quaternary)
    }
}