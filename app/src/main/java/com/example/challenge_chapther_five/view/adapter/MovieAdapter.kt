package com.example.challenge_chapther_five.view.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.challenge_chapther_five.data.response.ResultsItem
import com.example.challenge_chapther_five.databinding.MovieItemContainerBinding
import com.example.challenge_chapther_five.view.ui.main.MainFragmentDirections

class MovieAdapter(private val listMovie: List<ResultsItem>) :
    RecyclerView.Adapter<MovieAdapter.ViewHolder>() {

    private val diffCallback = object : DiffUtil.ItemCallback<ResultsItem>() {
        override fun areItemsTheSame(oldItem: ResultsItem, newItem: ResultsItem): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: ResultsItem, newItem: ResultsItem): Boolean =
            oldItem.hashCode() == newItem.hashCode()
    }

    private val differ = AsyncListDiffer(this, diffCallback)

    fun setMovieList(movieArray: List<ResultsItem>) = differ.submitList(movieArray)

    inner class ViewHolder(private val binding: MovieItemContainerBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: ResultsItem) {
            binding.apply {
                titleTextView.text = movie.title
                ratingTextView.text = movie.voteAverage.toString()
                popularityTextView.text = movie.popularity.toString()
                loadImage(itemView, movie.posterPath, posterImageView)
                itemContainer.setOnClickListener {
                    Log.d("TAG", movie.id.toString())
                    val action = MainFragmentDirections.actionMainFragmentToDetailFragment(movie.id)
                    it.findNavController().navigate(action)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            MovieItemContainerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listMovie[position])
    }

    override fun getItemCount(): Int = listMovie.size

    private fun loadImage(binding: View, url: String, container: ImageView) {
        val baseImageUrl = "https://image.tmdb.org/t/p/original/"
        Glide.with(binding)
            .load("$baseImageUrl$url")
            .into(container)
    }
}