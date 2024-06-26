package com.example.uxmapp2.ui.query

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.uxmapp2.data.characterData.Result
import com.example.uxmapp2.databinding.FragmentQueryBinding
import com.example.uxmapp2.source.State
import com.example.uxmapp2.ui.heroes.SavedHeroesManager
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class QueryFragment : Fragment() {

    private var _binding: FragmentQueryBinding? = null
    private val binding get() = _binding!!

    private val viewModel: QueryViewModel by viewModels()
    private lateinit var adapter: HeroAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.d("QueryFragment", "onCreateView: Inflating layout")
        _binding = FragmentQueryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("QueryFragment", "onViewCreated: View created")

        adapter = HeroAdapter { hero -> onHeroSelected(hero) }
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter

        binding.searchButton.setOnClickListener {
            Log.d("QueryFragment", "Search button clicked")
            val query = binding.searchEditText.text.toString()
            if (query.isNotEmpty()) {
                viewModel.searchHeroes(query)
            } else {
                viewModel.fetchAllHeroes()
            }
        }

        viewModel.searchResults.observe(viewLifecycleOwner) { state ->
            Log.d("QueryFragment", "State observed: $state")
            when (state) {
                is State.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.errorMessage.visibility = View.GONE
                }
                is State.Success -> {
                    binding.progressBar.visibility = View.GONE
                    adapter.submitList(state.data)
                }
                is State.Error -> {
                    binding.progressBar.visibility = View.GONE
                    binding.errorMessage.visibility = View.VISIBLE
                    binding.errorMessage.text = state.message
                }
            }
        }
    }

    private fun onHeroSelected(hero: Result) {
        if (SavedHeroesManager.isHeroSaved(hero)) {
            Toast.makeText(requireContext(), "Hero Already Saved", Toast.LENGTH_SHORT).show()
        } else {
            SavedHeroesManager.addHero(hero)
            Toast.makeText(requireContext(), "${hero.name} saved!", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}






























