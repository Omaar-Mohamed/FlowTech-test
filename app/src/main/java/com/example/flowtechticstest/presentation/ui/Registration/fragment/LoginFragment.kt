package com.example.flowtechticstest.presentation.ui.Registration.fragment

import android.os.Bundle
import android.text.InputType
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.flowtechticstest.R
import com.example.flowtechticstest.data.authentication.AuthRepositoryImpl
import com.example.flowtechticstest.data.authentication.AuthState
import com.example.flowtechticstest.databinding.FragmentLoginBinding
import com.example.flowtechticstest.presentation.viewmodel.AuthViewModel.AuthViewModel
import com.example.flowtechticstest.presentation.viewmodel.AuthViewModel.AuthViewModelFactory

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private var isPasswordVisible: Boolean = false
    private lateinit var authViewModel: AuthViewModel
    private lateinit var authViewModelFactory: AuthViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        authViewModelFactory = AuthViewModelFactory(AuthRepositoryImpl)
        authViewModel = ViewModelProvider(this, authViewModelFactory).get(AuthViewModel::class.java)

        // Observe authState
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            authViewModel.loginState.collect { state ->
                when (state) {
                    is AuthState.Loading -> {
                        // Show a progress bar or loading dialog
                        binding.progressCircular.visibility = View.VISIBLE
                    }
                    is AuthState.Success -> {
                        // Navigate to the next screen
                        Toast.makeText(requireContext(), "login success", Toast.LENGTH_SHORT).show()
                        binding.progressCircular.visibility = View.GONE
                        findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
                    }
                    is AuthState.Error -> {
                        // Show an error message
                        binding.progressCircular.visibility = View.GONE
                        Toast.makeText(requireContext(), "Registration failed: ${state.exception?.message}", Toast.LENGTH_SHORT).show()
                    }


                    else -> {}
                }
            }
        }


        binding.tvLogin.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_regestration)
        }

        // Handle the toggle of password visibility
        binding.loginPassword.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                // Check if the touch is on the drawableEnd (right side)
                if (event.rawX >= (binding.loginPassword.right - binding.loginPassword.compoundDrawables[2].bounds.width())) {
                    togglePasswordVisibility()
                    return@setOnTouchListener true
                }
            }
            false
        }

        binding.btnLogin.setOnClickListener {
            val email = binding.loginEmail.text.toString()
            val password = binding.loginPassword.text.toString()
            authViewModel.signIn(email, password)
        }


    }


    private fun togglePasswordVisibility() {
        if (isPasswordVisible) {
            binding.loginPassword.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            binding.loginPassword.setCompoundDrawablesWithIntrinsicBounds(0, 0,
                R.drawable.visibility_off, 0)
        } else {
            binding.loginPassword.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            binding.loginPassword.setCompoundDrawablesWithIntrinsicBounds(0, 0,
                R.drawable.visibilit, 0)
        }
        binding.loginPassword.setSelection(binding.loginPassword.text.length)
        isPasswordVisible = !isPasswordVisible
    }
}
