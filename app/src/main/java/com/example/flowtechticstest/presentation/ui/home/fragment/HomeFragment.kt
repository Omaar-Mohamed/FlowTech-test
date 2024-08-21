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
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.flowtechticstest.R
import com.example.flowtechticstest.data.authentication.AuthRepositoryImpl
import com.example.flowtechticstest.data.authentication.AuthState
import com.example.flowtechticstest.data.repo.CharacterRepoImpl
import com.example.flowtechticstest.data.repo.CharactersRepo
import com.example.flowtechticstest.data.source.remote.ApiState
import com.example.flowtechticstest.data.source.remote.AppRemoteDataSourseImpl
import com.example.flowtechticstest.databinding.FragmentHomeBinding
import com.example.flowtechticstest.presentation.ui.home.adapter.CharacterAdapter
import com.example.flowtechticstest.presentation.viewmodel.AuthViewModel.AuthViewModel
import com.example.flowtechticstest.presentation.viewmodel.AuthViewModel.AuthViewModelFactory
import com.example.flowtechticstest.presentation.viewmodel.CharactersViewModel.CharactersViewModel
import com.example.flowtechticstest.presentation.viewmodel.CharactersViewModel.CharactersViewModelFactory
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: CharactersViewModel
    private lateinit var viewModelFactory: CharactersViewModelFactory
    private lateinit var authViewModel: AuthViewModel
    private lateinit var authViewModelFactory: AuthViewModelFactory
    private lateinit var characterAdapter: CharacterAdapter
    private lateinit var gridLayoutManager: GridLayoutManager

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.refresh()
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        // Initialize adapter and layout manager
        characterAdapter = CharacterAdapter()
        gridLayoutManager = GridLayoutManager(requireContext(), 2)
        binding.recyclerView.apply {
            adapter = characterAdapter
            layoutManager = gridLayoutManager
        }
        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            private var isLoadingData = false

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                if (isLoadingData) return

                val layoutManager = recyclerView.layoutManager as? GridLayoutManager
                layoutManager?.let {
                    val visibleItemCount = it.childCount
                    val totalItemCount = it.itemCount
                    val firstVisibleItemPosition = it.findFirstVisibleItemPosition()

                    // Check if we're at the end of the list
                    if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount &&
                        firstVisibleItemPosition >= 0 &&
                        !viewModel.isLoading && viewModel.currentPage < viewModel.totalPages) {
                        isLoadingData = true
                        viewModel.loadNextPage()
                    }

                    // Check if we're at the top of the list
                    if (firstVisibleItemPosition == 0 && dy < 0 &&
                        !viewModel.isLoading && viewModel.currentPage > 1) {
                        isLoadingData = true
                        viewModel.loadPreviousPage()
                    }
                }
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    isLoadingData = false
                }
            }
        })



        // Initialize ViewModel and ViewModelFactory
        authViewModelFactory = AuthViewModelFactory(AuthRepositoryImpl)
        authViewModel = ViewModelProvider(this, authViewModelFactory).get(AuthViewModel::class.java)

        viewModelFactory = CharactersViewModelFactory(CharacterRepoImpl(AppRemoteDataSourseImpl))
        viewModel = ViewModelProvider(this, viewModelFactory).get(CharactersViewModel::class.java)

        // Observe the state from ViewModel
        lifecycleScope.launch {
            viewModel.allCharacters.collectLatest { state ->
                when (state) {
                    is ApiState.Loading -> {
                        // Show a progress bar or loading dialog
                        binding.progressCircular.visibility = View.VISIBLE
                    }
                    is ApiState.Success -> {
                        // Update the adapter with the data
                        characterAdapter.submitList(state.data.results)
                        binding.progressCircular.visibility = View.GONE
                    }
                    is ApiState.Failure -> {
                        // Show an error message
                        Toast.makeText(requireContext(), "Data fetch failed", Toast.LENGTH_SHORT).show()
                        binding.progressCircular.visibility = View.GONE
                    }
                }
            }
        }


            binding.toolbarIcon.setOnClickListener {
                authViewModel.signOut()
//                findNavController().navigate(R.id.action_homeFragment_to_loginFragment)
            }
        binding.tvName.text = "Rick and Morty Characters"

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



        // If using SwipeRefreshLayout
        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.refresh()
            binding.swipeRefreshLayout.isRefreshing = false
        }
            return binding.root
        }
}