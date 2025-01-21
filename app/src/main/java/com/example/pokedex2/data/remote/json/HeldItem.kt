package com.example.pokedex2.data.remote.json

data class HeldItem(
    val item: Item,
    val version_details: List<VersionDetail>
)