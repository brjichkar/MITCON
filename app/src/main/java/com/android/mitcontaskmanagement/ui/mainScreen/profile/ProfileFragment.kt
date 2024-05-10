package com.android.mitcontaskmanagement.ui.mainScreen.profile

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.android.mitcontaskmanagement.R
import com.android.mitcontaskmanagement.databinding.FragmentProfileBinding
import com.android.mitcontaskmanagement.ui.adapters.TaskCountAdapter
import com.android.mitcontaskmanagement.ui.auth.AuthActivity
import com.android.mitcontaskmanagement.util.TaskStateCount
import com.android.mitcontaskmanagement.util.setProfileImage
import com.android.mitcontaskmanagement.util.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private val binding by viewBinding(FragmentProfileBinding::bind)
    private val viewModel: ProfileViewModel by viewModels()
    private lateinit var taskCountAdapter: TaskCountAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        taskCountAdapter = TaskCountAdapter()

        binding.taskTypeRv.apply {
            adapter = taskCountAdapter
            setHasFixedSize(false)
        }

        viewModel.getUserData()?.let {
            binding.nameText.text = it.username
            binding.emailText.text = it.email
            binding.profileImg.setProfileImage(it.profileImage)
        }

        binding.logout.setOnClickListener {
            viewModel.onLogoutPressed()
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.taskCounts.collect {
                binding.noTasks.isVisible = it.isEmpty()
                taskCountAdapter.submitList(it)
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.shouldLogout.collect {
                if (it)
                    goBackToAuth()
            }
        }
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.taskStates.collect {
                setTaskStateCounts(it)
            }
        }
    }

    private fun setTaskStateCounts(data: TaskStateCount) {
        binding.apply {
            completedTaskCount.text = data.completed.toString()
            IncompleteTaskCount.text = data.incomplete.toString()
            totalTaskCount.text = data.total.toString()
        }
    }

    private fun goBackToAuth() {
        Intent(requireContext(), AuthActivity::class.java).also {
            startActivity(it)
            requireActivity().finish()
        }
    }
}
