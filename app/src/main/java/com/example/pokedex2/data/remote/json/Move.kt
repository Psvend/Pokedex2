package com.example.pokedex2.data.remote.json

data class Move(
    val move: MoveX,
    val version_group_details: List<VersionGroupDetail>
)