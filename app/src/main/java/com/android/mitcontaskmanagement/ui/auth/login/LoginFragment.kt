package com.android.mitcontaskmanagement.ui.auth.login

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.android.mitcontaskmanagement.R
import com.android.mitcontaskmanagement.databinding.FragmentLoginBinding
import com.android.mitcontaskmanagement.ui.auth.AuthActivity
import com.android.mitcontaskmanagement.util.ErrorTYpe
import com.android.mitcontaskmanagement.util.Resource
import com.android.mitcontaskmanagement.util.setLargeImage
import com.android.mitcontaskmanagement.util.showToast
import com.android.mitcontaskmanagement.util.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment(R.layout.fragment_login) {

    private val binding by viewBinding(FragmentLoginBinding::bind)
    private val viewModel: LoginViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.emailInput.doOnTextChanged { text, _, _, _ ->
            viewModel.onEmailTextChange(email = text.toString())
        }
        binding.otpInput.doOnTextChanged { text, _, _, _ ->
            viewModel.onPasswordTextChange(password = text.toString())
        }
        binding.loginImage.setLargeImage(R.drawable.login_illustration)
        binding.emailInput.setText(viewModel.email.value)
        binding.otpInput.setText(viewModel.password.value)

        binding.signInBtn.setOnClickListener {
            binding.tvResendotp.visibility=View.VISIBLE
            binding.otpInput.visibility=View.VISIBLE
            viewModel.onLoginButtonPressed()
        }
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.loginState.collect {
                binding.loadingAnim.isVisible = it is Resource.Loading
                when (it) {
                    is Resource.Empty -> Unit
                    is Resource.Error -> {
                        if (it.errorType == ErrorTYpe.NO_INTERNET) (requireActivity() as AuthActivity).showErrorDialog(
                            it.errorType
                        )
                        else requireContext().showToast(it.message)
                    }

                    is Resource.Loading -> Unit
                    is Resource.Success -> {
                        navigateToHomeScreen()
                    }
                }
            }
        }
    }

    private fun navigateToHomeScreen() {
        findNavController().navigate(R.id.action_loginFragment_to_mainActivity)
        requireActivity().finish()
    }

}
