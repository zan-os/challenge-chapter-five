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
import com.example.challenge_chapther_five.databinding.FragmentLoginBinding
import com.example.challenge_chapther_five.view.viewmodel.AuthViewModel
import com.example.challenge_chapther_five.view.viewmodel.ViewModelFactory
import com.google.android.material.snackbar.Snackbar

class LoginFragment : Fragment() {
    private lateinit var authViewModel: AuthViewModel
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentLoginBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        authViewModel = obtainViewModel(requireActivity() as AppCompatActivity)
        binding.toRegisterButton.setOnClickListener {
            navigateToRegister()
        }
        binding.loginButton.setOnClickListener {
            navigateToHome()
            showSnackbar("Fitur login masih error")
        }
    }


    //TODO: Fitur masih error
    private fun login() {
        binding.loginButton.setOnClickListener {
            val email = binding.emailEditText.text.toString().trim()
            val password = binding.passwordEditText.text.toString().trim()

            when {
                password.isEmpty() -> {
                    binding.passwordEditText.error = "Tidak boleh kosong"
                }
                email.isEmpty() -> {
                    binding.emailEditText.error = "Tidak boleh kosong"
                }
                else -> {
                    authViewModel.login(email, password)
                    authViewModel.checkCridential(email, password)
                }
            }
        }
    }

    private fun obtainViewModel(activity: AppCompatActivity): AuthViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[AuthViewModel::class.java]
    }

    private fun checkUser(user: User) {
        if (authViewModel.checkCridential(user.email, user.password)) {
            navigateToHome()
        } else {
            binding.passwordEditText.error = "Password salah"
        }
    }

    private fun showSnackbar(message: String) {
        Snackbar.make(binding.parent, message, Snackbar.LENGTH_LONG).show()
    }

    private fun navigateToHome() {
        findNavController().navigate(R.id.action_loginFragment_to_mainFragment)
    }

    private fun navigateToRegister() {
        findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}