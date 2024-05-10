package com.android.mitcontaskmanagement.ui.mainScreen

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.PopupMenu
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.android.mitcontaskmanagement.R
import com.android.mitcontaskmanagement.data.models.entity.TaskEntity
import com.android.mitcontaskmanagement.databinding.ActivityMainBinding
//import com.android.mitcontaskmanagement.databinding.DrawerMenuBinding
import com.android.mitcontaskmanagement.service.TimerService
import com.android.mitcontaskmanagement.ui.ErrorDialogFragment
import com.android.mitcontaskmanagement.ui.auth.AuthActivity
import com.android.mitcontaskmanagement.ui.mainScreen.home.HomeFragment
import com.android.mitcontaskmanagement.ui.mainScreen.stats.StatsFragment
import com.android.mitcontaskmanagement.util.*
import com.android.mitcontaskmanagement.util.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import androidx.fragment.app.Fragment

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val binding by viewBinding(ActivityMainBinding::inflate)
    private lateinit var navController: NavController
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        navController = findNavController(R.id.fragment2)
        binding.bottomBar.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.first_fragment -> handleNavigation(TopLevelScreens.HOME)
                R.id.second_fragment -> handleNavigation(TopLevelScreens.STATS)
                R.id.third_fragment -> handleNavigation(TopLevelScreens.PROFILE)
                R.id.fourth_fragment -> handleNavigation(TopLevelScreens.ABOUt)
            }
            true
        }
        viewModel.fetchAllTasks()
        lifecycleScope.launchWhenStarted {
            viewModel.runningTask.collect {
                Timber.d("Running task $it")
                if (it.isNotEmpty()) {
                    startService(it[0])
                } else {
                    stopService()
                }
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.shouldLogout.collect {
                if (it)
                    goBackToAuth()
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.taskFetchState.collect {
                binding.root.isEnabled = it !is Resource.Loading
                when (it) {
                    is Resource.Empty -> Unit
                    is Resource.Error -> {
                        it.errorType?.let { error ->
                            showErrorDialog(error)
                        } ?: showToast(it.message)
                    }

                    is Resource.Loading -> Unit
                    is Resource.Success -> Unit
                }
            }
        }


        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.timerFragment)
                supportActionBar?.hide()
            else
                supportActionBar?.show()
        }
    }

    private fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    private fun handleNavigation(screen: TopLevelScreens) {
        if (navController.currentDestination?.id == screen.fragmentId)
            return
        val navigationId = when (screen) {
            TopLevelScreens.HOME -> R.id.homeFragmentGlobal
            TopLevelScreens.PROFILE -> R.id.profileFragmentGlobal
            TopLevelScreens.STATS -> R.id.statsFragmentGlobal
            TopLevelScreens.ABOUt -> R.id.aboutFragmentGlobal
            TopLevelScreens.TIMER -> R.id.timerFragmentGlobal
        }
        Timber.d(navigationId.toString())
        if (screen != TopLevelScreens.TIMER)
            navController.popBackStack()
        navController.navigate(navigationId)
    }

    private fun startService(task: TaskEntity) {
        Timber.d("StartService ${viewModel.isServiceRunning()}")
        if (!viewModel.isServiceRunning()) {
            Timber.d("Starting service")
            Intent(this, TimerService::class.java).also {
                it.putExtra(TASK, task)
                it.putExtra(DURATION, task.timeLeft)
                startService(it)
            }
            viewModel.saveServiceStarted()
        }
    }

    private fun stopService() {
        Timber.d("StopService ${viewModel.isServiceRunning()}")
        if (viewModel.isServiceRunning()) {
            Intent(this, TimerService::class.java).also {
                stopService(it)
            }
            viewModel.saveServiceStopped()
        }
    }

    fun showTimer(stopWatchFor: StopWatchFor, task: TaskEntity) {
        viewModel.task = task
        viewModel.stopWatchFor = stopWatchFor
    }

    private fun goBackToAuth() {
        Intent(this, AuthActivity::class.java).also {
            startActivity(it)
            finish()
        }
    }

    fun showErrorDialog(errorTYpe: ErrorTYpe) {
        ErrorDialogFragment(errorTYpe).show(supportFragmentManager, SHOW_ERROR_DIALOG)
    }
}
