package com.example.uxmapp2.ui.query

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.uxmapp2.R
import com.example.uxmapp2.data.characterData.Result
import com.example.uxmapp2.databinding.ItemHeroBinding
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

class HeroAdapter(private val onClick: (Result) -> Unit) :
    ListAdapter<Result, HeroAdapter.HeroViewHolder>(HeroDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeroViewHolder {
        val binding = ItemHeroBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HeroViewHolder(binding, onClick)
    }

    override fun onBindViewHolder(holder: HeroViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class HeroViewHolder(
        private val binding: ItemHeroBinding,
        private val onClick: (Result) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(hero: Result) {
            binding.heroName.text = hero.name
            val imageUrl = "${hero.thumbnail.path}.${hero.thumbnail.extension}".replace("http://", "https://")
            Log.d("HeroAdapter", "Loading image URL: $imageUrl")
            Picasso.get()
                .load(imageUrl)
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.error_image)
                .into(binding.heroImage, object : Callback {
                    override fun onSuccess() {
                        Log.d("HeroAdapter", "Image loaded successfully")
                    }

                    override fun onError(e: Exception?) {
                        Log.e("HeroAdapter", "Error loading image", e)
                    }
                })
            binding.root.setOnClickListener { onClick(hero) }
        }
    }
}












