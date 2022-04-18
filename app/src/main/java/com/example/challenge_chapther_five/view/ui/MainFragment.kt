package com.example.challenge_chapther_five.view.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.challenge_chapther_five.data.response.ResultsItem
import com.example.challenge_chapther_five.databinding.FragmentMainBinding
import com.example.challenge_chapther_five.view.adapter.MovieAdapter
import com.example.challenge_chapther_five.view.viewmodel.MainViewModel

class MainFragment : Fragment() {
    private lateinit var adapter: MovieAdapter
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentMainBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainViewModel.listMovie.observe(requireActivity()) { movie ->
            showList(movie)
        }

        mainViewModel.showLoading.observe(requireActivity()) {
            showLoading(it)
        }
    }

    private fun showList(movie: List<ResultsItem>) {
        val layoutManager = LinearLayoutManager(requireContext())

        binding.apply {
            itemRecyclerView.layoutManager = layoutManager
            adapter = MovieAdapter(movie)
            adapter.setMovieList(movie)
            itemRecyclerView.adapter = adapter
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}