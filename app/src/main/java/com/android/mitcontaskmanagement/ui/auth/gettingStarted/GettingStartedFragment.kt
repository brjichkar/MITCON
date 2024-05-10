package com.android.mitcontaskmanagement.ui.auth.gettingStarted

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.android.mitcontaskmanagement.R
import com.android.mitcontaskmanagement.databinding.FragmentGettingStartedBinding
import com.android.mitcontaskmanagement.util.setLargeImage
import com.android.mitcontaskmanagement.util.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GettingStartedFragment : Fragment(R.layout.fragment_getting_started) {

    private val binding by viewBinding(FragmentGettingStartedBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.gettingStartedImage.setLargeImage(R.drawable.getting_started_illustration)
        binding.signInBtn.setOnClickListener {
            findNavController().navigate(R.id.action_gettingStartedFragment_to_loginFragment)
        }

    }
}
