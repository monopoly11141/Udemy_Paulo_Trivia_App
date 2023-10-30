package com.example.udemy_paulo_trivia_app.di

import com.example.udemy_paulo_trivia_app.network.QuestionApi
import com.example.udemy_paulo_trivia_app.repository.QuestionRepository
import com.example.udemy_paulo_trivia_app.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun providesQuestionApi() : QuestionApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(QuestionApi::class.java)
    }

    @Singleton
    @Provides
    fun providesQuestionRepository(questionApi: QuestionApi) : QuestionRepository {
        return QuestionRepository(questionApi)
    }

}