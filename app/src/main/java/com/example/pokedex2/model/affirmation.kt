package com.example.pokedex2.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes


data class affirmation (
    @StringRes val stringResourceId: Int,
    @DrawableRes val imageResourceId: Int
)