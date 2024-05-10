package com.android.mitcontaskmanagement.ui.mainScreen.home.upComing

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import coil.load
import com.android.mitcontaskmanagement.R
import com.android.mitcontaskmanagement.data.models.entity.TaskEntity
import com.android.mitcontaskmanagement.databinding.FragmentUpComingBinding
import com.android.mitcontaskmanagement.ui.adapters.TaskAdapter
import com.android.mitcontaskmanagement.ui.mainScreen.MainActivity
import com.android.mitcontaskmanagement.util.ErrorTYpe
import com.android.mitcontaskmanagement.util.StopWatchFor
import com.android.mitcontaskmanagement.util.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UpComingFragment : Fragment(R.layout.fragment_up_coming) {

    companion object {
        fun newInstance() = UpComingFragment()
    }

    private val binding by viewBinding(FragmentUpComingBinding::bind)
    private val viewModel: UpComingViewModel by viewModels()
    private lateinit var upComingTaskAdapter: TaskAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        upComingTaskAdapter = TaskAdapter {
            navigateToStopWatchActivity(StopWatchFor.UPCOMING, it)
        }
        binding.upcomingTaskRv.apply {
            adapter = upComingTaskAdapter
            setHasFixedSize(false)
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.upComingTasks.collect {
                if (it.isEmpty())
                    configureErrorImage()
                binding.errorLayout.root.isVisible = it.isEmpty()
                upComingTaskAdapter.submitList(it)
            }
        }
    }

    private fun navigateToStopWatchActivity(stopWatchFor: StopWatchFor, task: TaskEntity) {
        (requireActivity() as MainActivity).showTimer(stopWatchFor, task)
    }

    private fun configureErrorImage() {
        binding.errorLayout.errorImage.load(resources.getDrawable(ErrorTYpe.NO_TASKS.image)) {
            crossfade(true)
        }
        binding.errorLayout.errorTitle.text = getString(ErrorTYpe.NO_TASKS.title)
        binding.errorLayout.errorDescription.text = getString(ErrorTYpe.NO_TASKS.message)
    }
}
