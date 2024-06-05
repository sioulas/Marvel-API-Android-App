package com.example.uxmapp2.ui.heroes

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.uxmapp2.R
import com.example.uxmapp2.data.characterData.Result
import com.example.uxmapp2.databinding.FragmentHeroDetailBinding
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

class HeroDetailFragment : Fragment() {

    private var _binding: FragmentHeroDetailBinding? = null
    private val binding get() = _binding!!

    private lateinit var hero: Result

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHeroDetailBinding.inflate(inflater, container, false)

        // Check the Android version and use the appropriate method to get the parcelable
        hero = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requireArguments().getParcelable("hero", Result::class.java)!!
        } else {
            @Suppress("DEPRECATION")
            requireArguments().getParcelable("hero")!!
        }

        Log.d("HeroDetailFragment", "Hero: $hero")

        binding.heroName.text = hero.name
        binding.heroDescription.text = hero.description
        binding.heroComics.text = "Comics Available: " + hero.comics.available.toString()
        binding.heroStories.text = "Stories Available: " + hero.stories.available.toString()
        binding.heroEvents.text = "Events Available: " + hero.events.available.toString()
        binding.heroSeries.text = "Series Available: " + hero.series.available.toString()
        val imageUrl = "${hero.thumbnail.path}.${hero.thumbnail.extension}".replace("http://", "https://")
        Log.d("HeroDetailFragment", "Image URL: $imageUrl")

        // Simplify Picasso loading
        loadImage(imageUrl)

        binding.goBackButton.setOnClickListener {
            findNavController().navigateUp()
        }

        return binding.root
    }

    private fun loadImage(imageUrl: String) {
        Picasso.get()
            .load(imageUrl)
            .placeholder(R.drawable.placeholder)
            .error(R.drawable.error_image)
            .into(binding.heroImage, object : Callback {
                override fun onSuccess() {
                    Log.d("HeroDetailFragment", "Image loaded successfully")
                }

                override fun onError(e: Exception?) {
                    Log.e("HeroDetailFragment", "Error loading image", e)
                }
            })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

















