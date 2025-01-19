package com.example.pokedex2.model

import com.example.pokedex2.data.remote.json.Ability


data class Affirmation (
    val id: Int,
    val name: String,
    val imageResourceId: String,
    val typeIcon: List<String>,
    val isLiked: Boolean,
    val number: Int,
    val ability: List<String>,
    val heldItem: List<String>,
    val characteristics: List<String>,
    val growthRate: String,
    val evolutionChainId: Int,
    val stats: List<Pair<String, Int>>

)
