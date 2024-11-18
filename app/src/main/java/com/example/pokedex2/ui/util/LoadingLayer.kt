package com.example.pokedex2.ui.util

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue


@Composable
fun LoadingLayer() {
    var isLoading by remember { mutableStateOf(false) }
    var showState by remember { mutableStateOf(false) }

