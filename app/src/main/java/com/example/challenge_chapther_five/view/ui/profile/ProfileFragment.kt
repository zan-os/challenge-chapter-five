package com.example.challenge_chapther_five.view.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.challenge_chapther_five.R
import com.example.challenge_chapther_five.databinding.FragmentProfileBinding
import com.google.android.material.snackbar.Snackbar

class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentProfileBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbarId.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
        binding.logoutButton.setOnClickListener {
            showSnackbar("Berhasil logout")
            findNavController().navigate(R.id.action_profileFragment_to_loginFragment)
        }
    }

    private fun showSnackbar(message: String) {
        Snackbar.make(binding.parent, message, Snackbar.LENGTH_LONG).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}