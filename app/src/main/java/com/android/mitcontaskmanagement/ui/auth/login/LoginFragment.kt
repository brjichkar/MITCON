package com.android.mitcontaskmanagement.ui.auth.login

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.inject.Deferred
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment(R.layout.fragment_login) {

    private val binding by viewBinding(FragmentLoginBinding::bind)
    private val viewModel: LoginViewModel by viewModels()
    val TIME_OUT = 60
    lateinit var otp_input: EditText
    lateinit var email_input: EditText
    lateinit var signInBtn: Button

    lateinit var auth: FirebaseAuth
    var job: Deferred<Unit>? = null
    var mCallback: PhoneAuthProvider.OnVerificationStateChangedCallbacks? = null
    var verificationCode: String = ""

    private fun Activity.hideKeyboard() = hideKeyboard(currentFocus ?: View(this))
    private fun Context.hideKeyboard(view: View) =
        (getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
                ).hideSoftInputFromWindow(view.windowToken, 0)

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

            // firebase otp
            binding.otpInput.visibility=View.VISIBLE
            //viewModel.onLoginButtonPressed()
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
                        //navigateToHomeScreen()
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
