package kr.tekit.lion.daongil.presentation.login

import android.app.Activity
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import kr.tekit.lion.daongil.R
import kr.tekit.lion.daongil.databinding.ActivityLoginBinding
import kr.tekit.lion.daongil.presentation.login.fragment.LoginFragment
import kr.tekit.lion.daongil.presentation.login.vm.LoginViewModel

class LoginActivity : AppCompatActivity() {

    private val binding: ActivityLoginBinding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }

    // private val viewModels: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}