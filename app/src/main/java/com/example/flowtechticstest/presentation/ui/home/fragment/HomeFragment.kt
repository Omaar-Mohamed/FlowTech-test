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
import com.example.flowtechticstest.data.repo.CharacterRepoImpl
import com.example.flowtechticstest.data.repo.CharactersRepo
import com.example.flowtechticstest.data.source.remote.ApiState
import com.example.flowtechticstest.data.source.remote.AppRemoteDataSourseImpl
import com.example.flowtechticstest.databinding.FragmentHomeBinding
import com.example.flowtechticstest.presentation.viewmodel.CharactersViewModel.CharactersViewModel
import com.example.flowtechticstest.presentation.viewmodel.CharactersViewModel.CharactersViewModelFactory
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {
    lateinit var binding: FragmentHomeBinding
    lateinit var viewModel: CharactersViewModel
    lateinit var viewModelFactory: CharactersViewModelFactory
        override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            // Inflate the layout for this fragment
            binding = FragmentHomeBinding.inflate(inflater, container, false)
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
                            Toast.makeText(requireContext(), "Loading", Toast.LENGTH_SHORT).show()
                        }
                        is ApiState.Success -> {
                            // Navigate to the next screen
                            Toast.makeText(requireContext(), "Registration successful", Toast.LENGTH_SHORT).show()
                            Log.i("charactersdata", "onCreateView: " + viewModel.allCharacters.value)
                        }
                        is ApiState.Failure -> {
                            // Show an error message
                            Toast.makeText(requireContext(), "Registration failed: ", Toast.LENGTH_SHORT).show()
                        }
                    }

                }
            }

            return binding.root
        }
}