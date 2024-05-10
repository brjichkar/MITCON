package com.android.mitcontaskmanagement.ui.auth

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.android.mitcontaskmanagement.R
import com.android.mitcontaskmanagement.databinding.ActivityAuthBinding
import com.android.mitcontaskmanagement.ui.ErrorDialogFragment
import com.android.mitcontaskmanagement.ui.mainScreen.MainActivity
import com.android.mitcontaskmanagement.util.ErrorTYpe
import com.android.mitcontaskmanagement.util.SHOW_ERROR_DIALOG
import com.android.mitcontaskmanagement.util.makeStatusBarTransparent
import com.android.mitcontaskmanagement.util.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthActivity : AppCompatActivity() {

    private val binding by viewBinding(ActivityAuthBinding::inflate)
    private val viewModel: AuthViewModel by viewModels()
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        navController = findNavController(R.id.fragment1)
        makeStatusBarTransparent()
    }

    override fun onStart() {
        super.onStart()
        if (viewModel.isUserLoggedIn()) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

    fun showErrorDialog(errorTYpe: ErrorTYpe) {
        ErrorDialogFragment(errorTYpe).show(supportFragmentManager, SHOW_ERROR_DIALOG)
    }
}
