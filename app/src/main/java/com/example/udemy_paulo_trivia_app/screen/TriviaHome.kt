package com.example.udemy_paulo_trivia_app.screen

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.udemy_paulo_trivia_app.component.QuestionComposable

@Composable
fun TriviaHome(questionViewModel : QuestionViewModel = hiltViewModel()) {
    QuestionComposable(questionViewModel)
}

