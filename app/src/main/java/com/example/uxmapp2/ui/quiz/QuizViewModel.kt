package com.example.uxmapp2.ui.quiz

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.uxmapp2.data.characterData.QuizQuestion
import com.example.uxmapp2.ui.heroes.SavedHeroesManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.example.uxmapp2.data.characterData.Result

@HiltViewModel
class QuizViewModel @Inject constructor(application: Application) : AndroidViewModel(application) {

    private val sharedPreferences = application.getSharedPreferences("QuizPrefs", Context.MODE_PRIVATE)
    private val _quizQuestions = MutableLiveData<List<QuizQuestion>>()
    val quizQuestions: LiveData<List<QuizQuestion>> get() = _quizQuestions

    val canNavigateToQuiz: LiveData<Boolean>
        get() = MutableLiveData(sharedPreferences.getBoolean("canNavigateToQuiz", false))

    fun allowQuizNavigation() {
        sharedPreferences.edit().putBoolean("canNavigateToQuiz", true).apply()
    }

    fun resetQuizNavigation() {
        sharedPreferences.edit().putBoolean("canNavigateToQuiz", false).apply()
    }

    fun generateQuizQuestions() {
        val heroes = SavedHeroesManager.getSavedHeroes()
        val questions = mutableListOf<QuizQuestion>()

        for (hero in heroes) {
            questions.add(
                QuizQuestion(
                    questionText = "What is the name of this hero?",
                    options = getOptionsExcludingCurrentHero(hero.name, heroes),
                    correctAnswer = hero.name,
                    imageUrl = "${hero.thumbnail.path}.${hero.thumbnail.extension}".replace("http://", "https://")
                )
            )
            questions.add(
                QuizQuestion(
                    questionText = "How many comics does ${hero.name} appear in?",
                    options = generateNumberOptions(hero.comics.available),
                    correctAnswer = hero.comics.available.toString(),
                    imageUrl = "${hero.thumbnail.path}.${hero.thumbnail.extension}".replace("http://", "https://")
                )
            )
            questions.add(
                QuizQuestion(
                    questionText = "How many stories does ${hero.name} appear in?",
                    options = generateNumberOptions(hero.stories.available),
                    correctAnswer = hero.stories.available.toString(),
                    imageUrl = "${hero.thumbnail.path}.${hero.thumbnail.extension}".replace("http://", "https://")
                )
            )
            questions.add(
                QuizQuestion(
                    questionText = "How many events does ${hero.name} appear in?",
                    options = generateNumberOptions(hero.events.available),
                    correctAnswer = hero.events.available.toString(),
                    imageUrl = "${hero.thumbnail.path}.${hero.thumbnail.extension}".replace("http://", "https://")
                )
            )
            questions.add(
                QuizQuestion(
                    questionText = "How many series does ${hero.name} appear in?",
                    options = generateNumberOptions(hero.series.available),
                    correctAnswer = hero.series.available.toString(),
                    imageUrl = "${hero.thumbnail.path}.${hero.thumbnail.extension}".replace("http://", "https://")
                )
            )
        }

        _quizQuestions.value = questions.shuffled().take(7)
    }

    private fun getOptionsExcludingCurrentHero(correctAnswer: String, heroes: List<Result>): List<String> {
        val incorrectAnswers = heroes.filter { it.name != correctAnswer }.map { it.name }
        val options = incorrectAnswers.shuffled().take(3).toMutableList()
        options.add(correctAnswer)
        return options.shuffled()
    }

    private fun generateNumberOptions(correctAnswer: Int): List<String> {
        val options = mutableListOf<String>()
        options.add(correctAnswer.toString())
        options.add((correctAnswer + 1).toString())
        options.add((correctAnswer - 1).toString())
        options.add((correctAnswer + 2).toString())
        return options.shuffled()
    }
}







