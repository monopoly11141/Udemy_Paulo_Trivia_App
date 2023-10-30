package com.example.udemy_paulo_trivia_app.data

data class DataOrException<T, Boolean, E : Exception> (
    var data : T? = null,
    var isLoading : Boolean? = null,
    var e : E? = null
)
