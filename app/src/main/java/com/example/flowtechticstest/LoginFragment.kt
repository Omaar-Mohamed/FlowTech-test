package com.example.flowtechticstest

import android.os.Bundle
import android.text.InputType
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.flowtechticstest.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private var isPasswordVisible: Boolean = false

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
    }

    private fun togglePasswordVisibility() {
        if (isPasswordVisible) {
            binding.loginPassword.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            binding.loginPassword.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.visibility_off, 0)
        } else {
            binding.loginPassword.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            binding.loginPassword.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.visibilit, 0)
        }
        binding.loginPassword.setSelection(binding.loginPassword.text.length)
        isPasswordVisible = !isPasswordVisible
    }
}
