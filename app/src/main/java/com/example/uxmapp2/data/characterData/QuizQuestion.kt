package com.example.uxmapp2.data.characterData

data class QuizQuestion(
    val questionText: String,
    val options: List<String>,
    val correctAnswer: String,
    val imageUrl: String
)
