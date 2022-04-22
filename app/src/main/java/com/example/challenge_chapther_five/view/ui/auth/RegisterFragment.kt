package com.example.challenge_chapther_five.view.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.challenge_chapther_five.R
import com.example.challenge_chapther_five.data.local.response.User
import com.example.challenge_chapther_five.databinding.FragmentRegisterBinding
import com.example.challenge_chapther_five.view.viewmodel.AuthViewModel
import com.example.challenge_chapther_five.view.viewmodel.ViewModelFactory

class RegisterFragment : Fragment() {
    private lateinit var authViewModel: AuthViewModel
    private var user: User? = null
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentRegisterBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        authViewModel = obtainViewModel(requireActivity() as AppCompatActivity)

        binding.registerButton.setOnClickListener {
            addUser()
            findNavController().navigate(R.id.action_registerFragment_to_mainFragment)
        }

        binding.toLoginButton.setOnClickListener {
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }
    }

    private fun addUser() {
        val username = binding.usernameEditText.text.toString().trim()
        val password = binding.passwordEditText.text.toString().trim()
        val email = binding.emailEditText.text.toString().trim()

        when {
            username.isEmpty() -> {
                binding.usernameEditText.error = "Tidak boleh kosong"
            }
            password.isEmpty() -> {
                binding.passwordEditText.error = "Tidak boleh kosong"
            }
            email.isEmpty() -> {
                binding.emailEditText.error = "Tidak boleh kosong"
            }
            else -> {
                user.let { user ->
                    user?.username = username
                    user?.password = password
                    user?.email = email
                    authViewModel.insert(username, email, password)
                }
            }
        }
    }

    private fun obtainViewModel(activity: AppCompatActivity): AuthViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[AuthViewModel::class.java]
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}