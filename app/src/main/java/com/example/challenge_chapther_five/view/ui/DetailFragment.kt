package com.example.challenge_chapther_five.view.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.challenge_chapther_five.R
import com.example.challenge_chapther_five.data.response.CastItem
import com.example.challenge_chapther_five.databinding.FragmentDetailBinding
import com.example.challenge_chapther_five.view.adapter.CastAdapter
import com.example.challenge_chapther_five.view.viewmodel.DetailViewModel

class DetailFragment : Fragment() {
    private lateinit var adapter: CastAdapter
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private val detailViewModel: DetailViewModel by viewModels()
    private val args: DetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentDetailBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        detailViewModel.getMovieDetail(args.id)
        detailViewModel.detailMovie.observe(requireActivity()) { movie ->
            binding.apply {
                val ratingText = getString(R.string.get_rating, movie.voteAverage.toString())
                ratingTextView.text = ratingText

                titleTextView.text = movie.title
                overviewTextView.text = movie.overview
                loadPoster(movie.posterPath, posterImageView)
            }
        }

        detailViewModel.getMovieCast(args.id)
        detailViewModel.castList.observe(requireActivity()) { cast ->
            showList(cast)
        }

        detailViewModel.showLoading.observe(requireActivity()) {
            showLoading(it)
        }

        popBackStack()
    }

    private fun showList(cast: List<CastItem>) {
        binding.apply {
            adapter = CastAdapter(cast)
            castRecylerView.adapter = adapter
            castRecylerView.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }

    private fun loadPoster(url: String, container: ImageView) {
        val baseImageUrl = "https://image.tmdb.org/t/p/original/"
        Glide.with(this)
            .load("$baseImageUrl$url")
            .into(container)
    }

    private fun popBackStack() {
        binding.toolbarId.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}