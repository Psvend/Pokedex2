package com.example.pokedex2.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes


//Hver affirmation består af et billede og en string text
data class Pokemon(
    @StringRes val stringResourceId: Int,
    @DrawableRes val imageResourceId: Int
)



