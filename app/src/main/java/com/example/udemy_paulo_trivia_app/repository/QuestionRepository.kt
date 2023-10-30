package com.example.udemy_paulo_trivia_app.repository

import android.content.ContentValues.TAG
import android.util.Log
import com.example.udemy_paulo_trivia_app.data.DataOrException
import com.example.udemy_paulo_trivia_app.model.QuestionItem
import com.example.udemy_paulo_trivia_app.network.QuestionApi
import javax.inject.Inject

class QuestionRepository @Inject constructor(private val questionApi: QuestionApi) {

    private val dataOrException = DataOrException<ArrayList<QuestionItem>, Boolean, Exception>()

    suspend fun getAllQuestion(): DataOrException<ArrayList<QuestionItem>, Boolean, Exception> {
        try {
            dataOrException.isLoading = true
            dataOrException.data = questionApi.getAllQuestion()

            if (dataOrException.data.toString().isNotEmpty()) {
                dataOrException.isLoading = false
            }
        } catch (exception: Exception) {
            dataOrException.e = exception
            Log.d(TAG, "getAllQuestion: exception: ${exception.message}")
        }

        return dataOrException
    }

}