package com.android.mitcontaskmanagement.ui.splashScreen

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.android.mitcontaskmanagement.R
import com.android.mitcontaskmanagement.ui.mainScreen.MainActivity
import com.android.mitcontaskmanagement.util.makeStatusBarTransparent
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    private val viewModel by viewModels<SplashViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        makeStatusBarTransparent()
        Handler().postDelayed(
            {

                /* Create an Intent that will start the Menu-Activity. */ var  mainIntent: /*@@qhtglw@@*/Intent? = Intent(getApplicationContext(), /*@@owbkzd@@*/MainActivity::class.java)
                startActivity(mainIntent)
                finish()

                startActivity(intent)
                finish()
            },
            2000L
        )
    }
}
