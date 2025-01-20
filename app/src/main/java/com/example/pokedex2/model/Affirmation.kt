package com.example.pokedex2.model

import com.example.pokedex2.data.remote.json.Ability

data class Affirmation (
    val id: Int,
    val name: String,
    val imageResourceId: String,
    val typeIcon: List<String>,
    var isLiked: Boolean,
    val number: Int,
    val ability: List<String>,
    val heldItem: List<String>,
    val stats: List<Pair<String, Int>>

)
