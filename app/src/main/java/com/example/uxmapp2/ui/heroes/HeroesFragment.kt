package com.example.uxmapp2.ui.heroes

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.uxmapp2.R
import com.example.uxmapp2.data.characterData.Result
import com.example.uxmapp2.databinding.FragmentHeroesBinding
import com.example.uxmapp2.source.State
import com.example.uxmapp2.ui.query.HeroAdapter
import com.example.uxmapp2.ui.quiz.QuizViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HeroesFragment : Fragment() {

    private var _binding: FragmentHeroesBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HeroesViewModel by viewModels()
    private val quizViewModel: QuizViewModel by activityViewModels()
    private lateinit var adapter: HeroAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHeroesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = HeroAdapter { hero -> onHeroSelected(hero) }
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter

        viewModel.heroes.observe(viewLifecycleOwner) { state ->
            when (state) {
                is State.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.errorMessage.visibility = View.GONE
                }
                is State.Success -> {
                    binding.progressBar.visibility = View.GONE
                    adapter.submitList(state.data)
                    updateQuizButtonVisibility(state.data.size)
                }
                is State.Error -> {
                    binding.progressBar.visibility = View.GONE
                    binding.errorMessage.visibility = View.VISIBLE
                    binding.errorMessage.text = state.message
                }
            }
        }

        binding.startQuizButton.setOnClickListener {
            Log.d("HeroesFragment", "Start Quiz Button Clicked")
            quizViewModel.allowQuizNavigation()
            findNavController().navigate(R.id.action_heroesFragment_to_quizFragment)
        }

        // Trigger loading of saved heroes
        viewModel.getSavedHeroes()
    }

    private fun updateQuizButtonVisibility(heroCount: Int) {
        binding.startQuizButton.visibility = if (heroCount >= 5) View.VISIBLE else View.GONE
    }

    private fun onHeroSelected(hero: Result) {
        val bundle = Bundle().apply {
            putParcelable("hero", hero)
        }
        findNavController().navigate(R.id.action_heroesFragment_to_heroDetailFragment, bundle)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}






























