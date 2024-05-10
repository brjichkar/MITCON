package com.android.mitcontaskmanagement.ui.mainScreen.home.completed

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import coil.load
import com.android.mitcontaskmanagement.R
import com.android.mitcontaskmanagement.databinding.FragmentCompletedBinding
import com.android.mitcontaskmanagement.ui.adapters.TaskAdapter
import com.android.mitcontaskmanagement.util.ErrorTYpe
import com.android.mitcontaskmanagement.util.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CompletedFragment : Fragment(R.layout.fragment_completed) {

    companion object {
        fun newInstance() = CompletedFragment()
    }

    private lateinit var completedTaskAdapter: TaskAdapter
    private val binding by viewBinding(FragmentCompletedBinding::bind)
    private val viewModel: CompletedViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        completedTaskAdapter = TaskAdapter {
        }
        binding.completedTaskRv.apply {
            adapter = completedTaskAdapter
            setHasFixedSize(false)
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.completedTasks.collect {
                if (it.isEmpty())
                    configureErrorImage()
                binding.errorLayout.root.isVisible = it.isEmpty()
                completedTaskAdapter.submitList(it)
            }
        }
    }

    private fun configureErrorImage() {
        binding.errorLayout.errorImage.load(resources.getDrawable(ErrorTYpe.NO_COMPLETED_TASKS.image)) {
            crossfade(true)
        }
        binding.errorLayout.errorTitle.text = getString(ErrorTYpe.NO_COMPLETED_TASKS.title)
        binding.errorLayout.errorDescription.text = getString(ErrorTYpe.NO_COMPLETED_TASKS.message)
    }
}
