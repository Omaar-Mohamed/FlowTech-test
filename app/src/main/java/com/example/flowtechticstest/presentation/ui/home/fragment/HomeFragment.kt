package com.example.flowtechticstest.presentation.ui.home.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.flowtechticstest.R
import com.example.flowtechticstest.data.authentication.AuthRepositoryImpl
import com.example.flowtechticstest.data.authentication.AuthState
import com.example.flowtechticstest.data.repo.CharacterRepoImpl
import com.example.flowtechticstest.data.repo.CharactersRepo
import com.example.flowtechticstest.data.source.remote.ApiState
import com.example.flowtechticstest.data.source.remote.AppRemoteDataSourseImpl
import com.example.flowtechticstest.databinding.FragmentHomeBinding
import com.example.flowtechticstest.presentation.viewmodel.AuthViewModel.AuthViewModel
import com.example.flowtechticstest.presentation.viewmodel.AuthViewModel.AuthViewModelFactory
import com.example.flowtechticstest.presentation.viewmodel.CharactersViewModel.CharactersViewModel
import com.example.flowtechticstest.presentation.viewmodel.CharactersViewModel.CharactersViewModelFactory
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {
    lateinit var binding: FragmentHomeBinding
    lateinit var viewModel: CharactersViewModel
    lateinit var viewModelFactory: CharactersViewModelFactory
    lateinit var authViewModel: AuthViewModel
    lateinit var authViewModelFactory: AuthViewModelFactory
        override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            // Inflate the layout for this fragment
            binding = FragmentHomeBinding.inflate(inflater, container, false)
            authViewModelFactory = AuthViewModelFactory(AuthRepositoryImpl)
            authViewModel = ViewModelProvider(this, authViewModelFactory).get(AuthViewModel::class.java)


            viewModelFactory = CharactersViewModelFactory(CharacterRepoImpl(
                AppRemoteDataSourseImpl
            ))
            viewModel = ViewModelProvider(this, viewModelFactory).get(CharactersViewModel::class.java)
            viewModel.getCharacters()
            lifecycleScope.launch {
                viewModel.allCharacters.collectLatest {
                    when (viewModel.allCharacters.value) {
                        is ApiState.Loading -> {
                            // Show a progress bar or loading dialog
                            Toast.makeText(requireContext(), "Loading data", Toast.LENGTH_SHORT).show()
                        }
                        is ApiState.Success -> {
                            // Navigate to the next screen
                            Toast.makeText(requireContext(), "data fetched succfully", Toast.LENGTH_SHORT).show()
                            Log.i("charactersdata", "onCreateView: " + viewModel.allCharacters.value)
                        }
                        is ApiState.Failure -> {
                            // Show an error message
                            Toast.makeText(requireContext(), "Registration failed: ", Toast.LENGTH_SHORT).show()
                        }
                    }

                }
            }
            binding.toolbarIcon.setOnClickListener {
                authViewModel.signOut()
//                findNavController().navigate(R.id.action_homeFragment_to_loginFragment)
            }

            lifecycleScope.launch {
                authViewModel.logoutState.collectLatest { state ->
                    when (state) {
                        is AuthState.Loading -> {
                            // Show a loading toast if the user is being logged out
                            Toast.makeText(requireContext(), "Logging out...", Toast.LENGTH_SHORT).show()
                        }
                        is AuthState.Error -> {
                            // Show an error message if there's an error during logout
                            Toast.makeText(requireContext(), "Logout failed", Toast.LENGTH_SHORT).show()
                        }
                        AuthState.SignedOut -> {
                            // Show a toast when successfully logged out
                            Toast.makeText(requireContext(), "Logged out successfully", Toast.LENGTH_SHORT).show()
                            // Navigate to the LoginFragment
                            findNavController().navigate(R.id.action_homeFragment_to_loginFragment)
                        }
                        is AuthState.Success -> {
                            // Handle other successful auth states if needed
                            Toast.makeText(requireContext(), "Operation successful", Toast.LENGTH_SHORT).show()
                        }

                        else -> {}
                    }
                }
            }


            return binding.root
        }
}