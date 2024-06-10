package kr.tekit.lion.daongil.presentation.login

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kr.tekit.lion.daongil.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private val binding: ActivityLoginBinding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}