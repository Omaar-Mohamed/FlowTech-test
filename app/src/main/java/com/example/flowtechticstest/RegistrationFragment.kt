package com.example.flowtechticstest

import android.os.Bundle
import android.text.InputType
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import com.example.flowtechticstest.databinding.FragmentRegestrationBinding

class RegistrationFragment : Fragment() {

    private lateinit var binding: FragmentRegestrationBinding
    private var isPhoneNumberVisible: Boolean = false
    private var isPasswordVisible: Boolean = false

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

        // Handle the toggle of phone number visibility
        binding.etPassword.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                if (event.rawX >= (binding.etPassword.right - binding.etPassword.compoundDrawables[2].bounds.width())) {
                    togglepassworVisbility()
                    return@setOnTouchListener true
                }
            }
            false
        }

        // Handle the toggle of password visibility
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
            val passwordet = binding.etPassword.text.toString().trim()
            val confirmpassword = binding.etPassword.text.toString().trim()
            val confirmPassword = binding.etConfirmPassword.text.toString().trim()
            val userNameet = binding.etUsername.text.toString().trim()
            val email = binding.etEmail.text.toString().trim()

            if (userNameet.isEmpty()) {
                binding.etUsername.error = "Username is required"
                return@setOnClickListener
            }
            if (email.isEmpty()) {
                binding.etEmail.error = "Email is required"
                return@setOnClickListener
            }

            // Check if email is well-formatted
            val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
            if (!email.matches(emailPattern.toRegex())) {
                binding.etEmail.error = "Invalid email format"
                return@setOnClickListener
            }
            // Check if any field is empty
            if (passwordet.isEmpty()) {
                binding.etPassword.error = "Phone number is required"
                return@setOnClickListener
            }

            if (confirmpassword.isEmpty()) {
                binding.etPassword.error = "Password is required"
                return@setOnClickListener
            }

            if (confirmPassword.isEmpty()) {
                binding.etConfirmPassword.error = "Confirm password is required"
                return@setOnClickListener
            }

            // Apply regex on password
            val passwordPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#\$%^&+=!])(?=\\S+\$).{8,}\$"
            if (!confirmpassword.matches(passwordPattern.toRegex())) {
                binding.etPassword.error = "Password must contain at least 8 characters, including a number, an uppercase letter, a lowercase letter, and a special character."
                return@setOnClickListener
            }

            // Check if passwords match
            if (confirmpassword != confirmPassword) {
                binding.etConfirmPassword.error = "Passwords do not match"
            } else {
                // Passwords match and all validations passed, proceed with signup logic
                // Example: navigate to the next screen or call a signup function
            }
        }
    }



    private fun togglepassworVisbility() {
        if (isPhoneNumberVisible) {
            // Hide the phone number (set it to phone type)
            binding.etPassword.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            binding.etPassword.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.visibility_off, 0)
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
            binding.etConfirmPassword.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.visibility_off, 0)
        } else {
            // Show the password
            binding.etConfirmPassword.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            binding.etConfirmPassword.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.visibilit, 0)
        }
        // Move the cursor to the end of the text
        binding.etConfirmPassword.setSelection(binding.etConfirmPassword.text.length)
        isPasswordVisible = !isPasswordVisible
    }


}
