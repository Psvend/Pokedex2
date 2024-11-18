package com.example.pokedex2.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes


data class Affirmation (
    @StringRes val stringResourceId: Int,
    @DrawableRes val imageResourceId: Int,
    @DrawableRes val typeIcon: List<Int>,
    val number: String,
    var isLiked: Boolean = false,
    var description: String,
    @DrawableRes val graph: Int
)

