package com.example.pokedex2.data.remote

data class FormsResponse(
    val pokemon_forms: List<Form>
)

data class Form (
    val name: String,
    val url: String
)
