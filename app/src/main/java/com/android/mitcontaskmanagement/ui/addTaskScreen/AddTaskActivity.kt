package com.android.mitcontaskmanagement.ui.addTaskScreen

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.android.mitcontaskmanagement.R
import com.android.mitcontaskmanagement.databinding.ActivityAddTaskBinding
import com.android.mitcontaskmanagement.ui.ErrorDialogFragment
import com.android.mitcontaskmanagement.util.ErrorTYpe
import com.android.mitcontaskmanagement.util.SHOW_ERROR_DIALOG
import com.android.mitcontaskmanagement.util.makeStatusBarTransparent
import com.android.mitcontaskmanagement.util.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddTaskActivity : AppCompatActivity() {

    private val binding by viewBinding(ActivityAddTaskBinding::inflate)
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        navController = findNavController(R.id.fragment3)
        makeStatusBarTransparent()
    }

    fun showErrorDialog(errorTYpe: ErrorTYpe) {
        ErrorDialogFragment(errorTYpe).show(supportFragmentManager, SHOW_ERROR_DIALOG)
    }
}
