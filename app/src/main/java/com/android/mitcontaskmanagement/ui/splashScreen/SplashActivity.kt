package com.android.mitcontaskmanagement.ui.splashScreen

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.android.mitcontaskmanagement.R
import com.android.mitcontaskmanagement.ui.auth.AuthActivity
import com.android.mitcontaskmanagement.ui.auth.login.LoginFragment
import com.android.mitcontaskmanagement.ui.mainScreen.MainActivity
import com.android.mitcontaskmanagement.util.makeStatusBarTransparent
import dagger.hilt.android.AndroidEntryPoint
import com.android.mitcontaskmanagement.ui.onBoardingScreen.OnBoardingActivity

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    private val viewModel by viewModels<SplashViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        makeStatusBarTransparent()
        Handler().postDelayed(
            {
                val intent = if (!viewModel.isOnBoardingComplete()) {
                    Intent(this, OnBoardingActivity::class.java)
                } else if (viewModel.isUserLogged()) {
                    Intent(this, MainActivity::class.java)
                } else {
                    Intent(this, AuthActivity::class.java)
                }

                startActivity(intent)
                finish()
            },
            2000L
        )
    }
}
