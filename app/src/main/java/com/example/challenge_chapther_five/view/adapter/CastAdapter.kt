package com.example.challenge_chapther_five.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.challenge_chapther_five.data.response.CastItem
import com.example.challenge_chapther_five.databinding.CastItemContainerBinding

class CastAdapter(private val listCast: List<CastItem>) :
    RecyclerView.Adapter<CastAdapter.ViewHolder>() {
    inner class ViewHolder(private val binding: CastItemContainerBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(cast: CastItem) {
            binding.apply {
                loadImage(itemView, cast.profilePath, castImageView)
                nameTextView.text = cast.originalName
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            CastItemContainerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listCast[position])
    }

    override fun getItemCount(): Int = listCast.size

    private fun loadImage(binding: View, url: String, container: ImageView) {
        val baseImageUrl = "https://image.tmdb.org/t/p/original/"
        Glide.with(binding)
            .load("$baseImageUrl$url")
            .into(container)
    }
}