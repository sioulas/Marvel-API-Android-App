package com.example.uxmapp2.ui.quiz

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.uxmapp2.R
import com.example.uxmapp2.data.characterData.QuizQuestion
import com.example.uxmapp2.databinding.FragmentQuizBinding
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class QuizFragment : Fragment() {

    private var _binding: FragmentQuizBinding? = null
    private val binding get() = _binding!!
    private val viewModel: QuizViewModel by activityViewModels()

    private var currentQuestionIndex = 0
    private var questions: List<QuizQuestion> = emptyList()
    private var correctAnswersCount = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentQuizBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.canNavigateToQuiz.observe(viewLifecycleOwner, Observer { canNavigate ->
            Log.d("QuizFragment", "Can Navigate to Quiz: $canNavigate")
            if (canNavigate) {
                binding.placeholderText.visibility = View.GONE
                binding.quizContent.visibility = View.VISIBLE

                viewModel.generateQuizQuestions()
                viewModel.quizQuestions.observe(viewLifecycleOwner) { questions ->
                    if (questions.isNotEmpty()) {
                        this.questions = questions
                        currentQuestionIndex = 0
                        correctAnswersCount = 0
                        displayQuestion(questions[currentQuestionIndex])
                        resetQuizState()
                    }
                }
            } else {
                binding.placeholderText.visibility = View.VISIBLE
                binding.quizContent.visibility = View.GONE
            }
        })

        binding.nextButton.setOnClickListener {
            if (currentQuestionIndex < questions.size - 1) {
                currentQuestionIndex++
                displayQuestion(questions[currentQuestionIndex])
                resetAnswerButtons()
            } else {
                binding.nextButton.visibility = View.GONE
                binding.retakeButton.visibility = View.VISIBLE
                binding.resultText.text = "You got $correctAnswersCount out of ${questions.size} correct!"
                binding.resultText.visibility = View.VISIBLE
                Toast.makeText(requireContext(), "Quiz Finished!", Toast.LENGTH_SHORT).show()
            }
        }

        binding.retakeButton.setOnClickListener {
            viewModel.generateQuizQuestions()
            resetQuizState()
        }

        binding.endQuizButton.setOnClickListener {
            viewModel.resetQuizNavigation()  // Reset the flag when ending the quiz
            findNavController().navigate(R.id.action_quizFragment_to_heroesFragment)
        }
    }

    private fun displayQuestion(question: QuizQuestion) {
        binding.questionText.text = question.questionText
        binding.answer1.text = question.options[0]
        binding.answer2.text = question.options[1]
        binding.answer3.text = question.options[2]
        binding.answer4.text = question.options[3]

        Picasso.get().load(question.imageUrl).into(binding.heroImage)

        val correctAnswer = question.correctAnswer

        val answerClickListener = View.OnClickListener { view ->
            val selectedAnswer = (view as? Button)?.text.toString()
            if (selectedAnswer == correctAnswer) {
                correctAnswersCount++
                Toast.makeText(requireContext(), "Correct!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "Incorrect!", Toast.LENGTH_SHORT).show()
            }
            disableAnswerButtons()
        }

        binding.answer1.setOnClickListener(answerClickListener)
        binding.answer2.setOnClickListener(answerClickListener)
        binding.answer3.setOnClickListener(answerClickListener)
        binding.answer4.setOnClickListener(answerClickListener)
    }

    private fun disableAnswerButtons() {
        binding.answer1.isEnabled = false
        binding.answer2.isEnabled = false
        binding.answer3.isEnabled = false
        binding.answer4.isEnabled = false

        binding.answer1.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.dark_grey))
        binding.answer2.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.dark_grey))
        binding.answer3.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.dark_grey))
        binding.answer4.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.dark_grey))
    }

    private fun resetAnswerButtons() {
        binding.answer1.isEnabled = true
        binding.answer2.isEnabled = true
        binding.answer3.isEnabled = true
        binding.answer4.isEnabled = true

        binding.answer1.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.maroon))
        binding.answer2.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.maroon))
        binding.answer3.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.maroon))
        binding.answer4.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.maroon))
    }

    private fun resetQuizState() {
        resetAnswerButtons()
        binding.nextButton.visibility = View.VISIBLE
        binding.retakeButton.visibility = View.GONE
        binding.resultText.visibility = View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}




















