package com.example.pokedex2.data.local

import androidx.room.TypeConverter

class BooleanConverters {

    @TypeConverter
    fun fromBoolean(value: Boolean): Int {
        return if (value) 1 else 0
    }

    @TypeConverter
    fun toBoolean(value: Int): Boolean {
        return value == 1
    }

}