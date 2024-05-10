package com.android.mitcontaskmanagement.ui.onBoardingScreen

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.android.mitcontaskmanagement.R
import com.android.mitcontaskmanagement.data.models.OnBoarding
import com.android.mitcontaskmanagement.databinding.ActivityOnBoardingBinding
import com.android.mitcontaskmanagement.ui.adapters.OnBoardingAdapter
import com.android.mitcontaskmanagement.ui.auth.AuthActivity
import com.android.mitcontaskmanagement.util.makeStatusBarTransparent
import com.android.mitcontaskmanagement.util.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OnBoardingActivity : AppCompatActivity() {

    private val viewModel by viewModels<OnBoardingViewModel>()
    private val binding by viewBinding(ActivityOnBoardingBinding::inflate)
    private lateinit var onBoardingAdapter: OnBoardingAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        makeStatusBarTransparent()
        val onBoardingList = listOf(
            OnBoarding(
                R.drawable.on_boarding_1,
                getString(R.string.onBoarding1Title),
                getString(R.string.onBoarding1Description),
            ),
            OnBoarding(
                R.drawable.on_boarding_2,
                getString(R.string.onBoarding2Title),
                getString(R.string.onBoarding2Description),
            ),
            OnBoarding(
                R.drawable.on_boarding_3,
                getString(R.string.onBoarding3Title),
                getString(R.string.onBoarding3Description),
                true
            )
        )

        onBoardingAdapter = OnBoardingAdapter(onBoardingList) {
            viewModel.onContinuePressed()
        }
        binding.onboardingViewpager.adapter = onBoardingAdapter
        binding.wormDotsIndicator.setViewPager2(binding.onboardingViewpager)

        lifecycleScope.launchWhenStarted {
            viewModel.navigateToAuth.collect {
                if (it)
                    navigateToAuth()
            }
        }
    }

    private fun navigateToAuth() {
        Intent(this, AuthActivity::class.java).also {
            startActivity(it)
            finish()
        }
    }
}
