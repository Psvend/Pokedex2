package com.example.pokedex2.model

import androidx.compose.ui.unit.dp
import androidx.room.Query
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

){
    fun doesMatchQuery(query: String):Boolean {
        val matchingCombination = listOf(
            "${name.first()}",
            "${id.toString().first()}",
            number.toString(),
            "$name ${id.toString()}",
            "$name${id.toString()}",
        )
        val typeIconMatch = typeIcon.any {it.contains(query, ignoreCase = true)}

        return matchingCombination.any() {
            it.contains(query, ignoreCase = true) || typeIconMatch
        }
    }
}
