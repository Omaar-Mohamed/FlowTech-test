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
import com.example.flowtechticstest.data.authentication.AuthRepo
import com.example.flowtechticstest.data.authentication.AuthRepositoryImpl
import com.example.flowtechticstest.data.authentication.AuthState
import com.example.flowtechticstest.databinding.FragmentRegestrationBinding
import com.example.flowtechticstest.presentation.viewmodel.AuthViewModel.AuthViewModel
import com.example.flowtechticstest.presentation.viewmodel.AuthViewModel.AuthViewModelFactory

class RegistrationFragment : Fragment() {

    private lateinit var binding: FragmentRegestrationBinding
    private var isPhoneNumberVisible: Boolean = false
    private var isPasswordVisible: Boolean = false
    private lateinit var authViewModel: AuthViewModel
    private lateinit var authViewModelFactory: AuthViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentRegestrationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        authViewModelFactory = AuthViewModelFactory(AuthRepositoryImpl)
        authViewModel = ViewModelProvider(this, authViewModelFactory).get(AuthViewModel::class.java)

        // Observe authState
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            authViewModel.authState.collect { state ->
                when (state) {
                    is AuthState.Loading -> {
                        // Show a progress bar or loading dialog
                        Toast.makeText(requireContext(), "Loading", Toast.LENGTH_SHORT).show()
                    }
                    is AuthState.Success -> {
                        // Navigate to the next screen
                        Toast.makeText(requireContext(), "Registration successful", Toast.LENGTH_SHORT).show()
                        findNavController().navigate(R.id.action_regestration_to_loginFragment)
                    }
                    is AuthState.Error -> {
                        // Show an error message
                        Toast.makeText(requireContext(), "Registration failed: ${state.exception?.message}", Toast.LENGTH_SHORT).show()
                    }
                    AuthState.SignedOut -> {
                        // Handle sign out state if needed
                    }
                }
            }
        }

        // Handle the toggle of password visibility
        binding.etPassword.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                if (event.rawX >= (binding.etPassword.right - binding.etPassword.compoundDrawables[2].bounds.width())) {
                    togglepassworVisbility()
                    return@setOnTouchListener true
                }
            }
            false
        }

        // Handle the toggle of confirm password visibility
        binding.etConfirmPassword.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                if (event.rawX >= (binding.etConfirmPassword.right - binding.etConfirmPassword.compoundDrawables[2].bounds.width())) {
                    toggleConfirmPasswordVisibility()
                    return@setOnTouchListener true
                }
            }
            false
        }

        binding.btnSignup.setOnClickListener {
            val password = binding.etPassword.text.toString().trim()
            val confirmPassword = binding.etConfirmPassword.text.toString().trim()
            val userName = binding.etUsername.text.toString().trim()
            val email = binding.etEmail.text.toString().trim()
            val age = binding.etAge.text.toString().trim()

            // Perform validation checks
            if (age.isEmpty()) {
                binding.etAge.error = "Age is required"
                return@setOnClickListener
            }
            if (age.toInt() < 18) {
                binding.etAge.error = "Age must be greater than 18"
                return@setOnClickListener
            }
            if (age.toInt() > 100) {
                binding.etAge.error = "Age must be less than 100"
                return@setOnClickListener
            }
            if (userName.isEmpty()) {
                binding.etUsername.error = "Username is required"
                return@setOnClickListener
            }
            if (email.isEmpty()) {
                binding.etEmail.error = "Email is required"
                return@setOnClickListener
            }
            if (!email.matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+".toRegex())) {
                binding.etEmail.error = "Invalid email format"
                return@setOnClickListener
            }
            if (password.isEmpty()) {
                binding.etPassword.error = "Password is required"
                return@setOnClickListener
            }
            if (confirmPassword.isEmpty()) {
                binding.etConfirmPassword.error = "Confirm password is required"
                return@setOnClickListener
            }
            if (!password.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#\$%^&+=!])(?=\\S+\$).{8,}\$".toRegex())) {
                binding.etPassword.error = "Password must contain at least 8 characters, including a number, an uppercase letter, a lowercase letter, and a special character."
                return@setOnClickListener
            }
            if (password != confirmPassword) {
                binding.etConfirmPassword.error = "Passwords do not match"
            } else {
                // Passwords match and all validations passed, proceed with signup logic
                authViewModel.register(userName, age.toInt(), email, password)
            }
        }
    }






    private fun togglepassworVisbility() {
        if (isPhoneNumberVisible) {
            // Hide the phone number (set it to phone type)
            binding.etPassword.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            binding.etPassword.setCompoundDrawablesWithIntrinsicBounds(0, 0,
                R.drawable.visibility_off, 0)
        } else {
            // Show the phone number (set it to text type)
            binding.etPassword.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            binding.etPassword.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.visibilit, 0)
        }
        // Move the cursor to the end of the text
        binding.etPassword.setSelection(binding.etPassword.text.length)
        isPhoneNumberVisible = !isPhoneNumberVisible
    }

    private fun toggleConfirmPasswordVisibility() {
        if (isPasswordVisible) {
            // Hide the password
            binding.etConfirmPassword.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            binding.etConfirmPassword.setCompoundDrawablesWithIntrinsicBounds(0, 0,
                R.drawable.visibility_off, 0)
        } else {
            // Show the password
            binding.etConfirmPassword.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            binding.etConfirmPassword.setCompoundDrawablesWithIntrinsicBounds(0, 0,
                R.drawable.visibilit, 0)
        }
        // Move the cursor to the end of the text
        binding.etConfirmPassword.setSelection(binding.etConfirmPassword.text.length)
        isPasswordVisible = !isPasswordVisible
    }


}
