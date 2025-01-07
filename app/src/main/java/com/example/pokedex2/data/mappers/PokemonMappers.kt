package com.example.pokedex2.data.mappers

import com.example.pokedex2.data.local.PokemonEntity
import com.example.pokedex2.data.remote.PokemonDto
import com.example.pokedex2.data.remote.PokemonType
import com.example.pokedex2.data.remote.TypeDetail
import com.example.pokedex2.data.remote.json.Ability
import com.example.pokedex2.data.remote.json.Animated
import com.example.pokedex2.data.remote.json.BlackWhite
import com.example.pokedex2.data.remote.json.Cries
import com.example.pokedex2.data.remote.json.Crystal
import com.example.pokedex2.data.remote.json.DiamondPearl
import com.example.pokedex2.data.remote.json.DreamWorld
import com.example.pokedex2.data.remote.json.Emerald
import com.example.pokedex2.data.remote.json.FireredLeafgreen
import com.example.pokedex2.data.remote.json.GenerationI
import com.example.pokedex2.data.remote.json.GenerationIi
import com.example.pokedex2.data.remote.json.GenerationIii
import com.example.pokedex2.data.remote.json.GenerationIv
import com.example.pokedex2.data.remote.json.GenerationV
import com.example.pokedex2.data.remote.json.GenerationVi
import com.example.pokedex2.data.remote.json.GenerationVii
import com.example.pokedex2.data.remote.json.GenerationViii
import com.example.pokedex2.data.remote.json.Gold
import com.example.pokedex2.data.remote.json.HeartgoldSoulsilver
import com.example.pokedex2.data.remote.json.Home
import com.example.pokedex2.data.remote.json.Icons
import com.example.pokedex2.data.remote.json.OfficialArtwork
import com.example.pokedex2.data.remote.json.OmegarubyAlphasapphire
import com.example.pokedex2.data.remote.json.Other
import com.example.pokedex2.data.remote.json.Platinum
import com.example.pokedex2.data.remote.json.RedBlue
import com.example.pokedex2.data.remote.json.RubySapphire
import com.example.pokedex2.data.remote.json.Showdown
import com.example.pokedex2.data.remote.json.Silver
import com.example.pokedex2.data.remote.json.Species
import com.example.pokedex2.data.remote.json.Sprites
import com.example.pokedex2.data.remote.json.Type
import com.example.pokedex2.data.remote.json.TypeX
import com.example.pokedex2.data.remote.json.UltraSunUltraMoon
import com.example.pokedex2.data.remote.json.Versions
import com.example.pokedex2.data.remote.json.XY
import com.example.pokedex2.data.remote.json.Yellow
import com.example.pokedex2.data.remote.json.testPokemon
import com.example.pokedex2.model.Pokemon

fun PokemonEntity.toTestPokemon(): testPokemon {
    return testPokemon(
        id = id,
        name = name,
        height = height,
        weight = weight,
        sprites = Sprites(
            front_default = imageUrl,
            back_default = "",
            back_female = "",
            back_shiny = "",
            back_shiny_female = "",
            front_female = "",
            front_shiny = "",
            front_shiny_female = "",
            other = Other(dream_world = DreamWorld("", ""),
                officialartwork = OfficialArtwork( "", ""),
                home = Home("","","",""),
                showdown = Showdown( "",
                    "" ,
                    "",
                    "",
                    "",
                    "",
                    "","")
            ),
            versions = Versions(
                generationi = GenerationI(
                    redblue = RedBlue(
                        back_default = "",
                        back_gray = "",
                        front_default = "",
                        front_gray = "",
                        back_transparent = "",
                        front_transparent = ""
                    ),
                    yellow = Yellow(
                        back_default = "",
                        back_gray = "",
                        front_default = "",
                        front_gray = "",
                        back_transparent = "",
                        front_transparent = ""
                    )
                ),
                generationii = GenerationIi(
                    crystal = Crystal(
                        back_default = "",
                        back_shiny = "",
                        front_default = "",
                        front_shiny = "",
                        back_shiny_transparent = "",
                        front_shiny_transparent = "",
                        back_transparent = "",
                        front_transparent = ""
                    ),
                    gold = Gold(
                        back_default = "",
                        back_shiny = "",
                        front_default = "",
                        front_shiny = "",
                        front_transparent = ""
                    ),
                    silver = Silver(
                        back_default = "",
                        back_shiny = "",
                        front_default = "",
                        front_shiny = "",
                        front_transparent = ""
                    )
                ),
                generationiii = GenerationIii(
                    emerald = Emerald(
                        front_default = "",
                        front_shiny = ""
                    ),
                    fireredleafgreen = FireredLeafgreen(
                        back_default = "",
                        back_shiny = "",
                        front_default = "",
                        front_shiny = ""
                    ),
                    rubysapphire = RubySapphire(
                        back_default = "",
                        back_shiny = "",
                        front_default = "",
                        front_shiny = ""
                    )
                ),
                generationiv = GenerationIv(
                    diamondpearl = DiamondPearl(
                        back_shiny = "",
                        back_shiny_female = "",
                        front_female = "",
                        front_shiny = "",
                        front_shiny_female = "",
                        back_default = "",
                        back_female = "",
                        front_default = "",

                        ),
                    heartgoldsoulsilver = HeartgoldSoulsilver(
                        back_shiny = "",
                        back_shiny_female = "",
                        front_female = "",
                        front_shiny = "",
                        front_shiny_female = "",
                        back_default = "",
                        back_female = "",
                        front_default = "",

                    ),
                    platinum = Platinum(
                        back_shiny = "",
                        back_shiny_female = "",
                        front_female = "",
                        front_shiny = "",
                        front_shiny_female = "",
                        back_default = "",
                        back_female = "",
                        front_default = "",
                    )
                 ),
                generationv = GenerationV(
                    blackwhite = BlackWhite(
                        animated = Animated(
                            back_default = "",
                            back_female = "",
                            back_shiny = "",
                            back_shiny_female = "",
                            front_default = "",
                            front_female = "",
                            front_shiny = "",
                            front_shiny_female = ""
                        ),
                        back_default = "",
                        back_female = "",
                        back_shiny = "",
                        back_shiny_female = "",
                        front_default = "",
                        front_female = "",
                        front_shiny = "",
                        front_shiny_female = ""
                    )
                ),
                generationvi = GenerationVi(
                    omegarubyalphasapphire = OmegarubyAlphasapphire(
                        front_default = "",
                        front_female = "",
                        front_shiny = "",
                        front_shiny_female = ""
                    ),
                    xy = XY(
                        front_default = "",
                        front_female = "",
                        front_shiny = "",
                        front_shiny_female = ""
                    )
                ),
                generationvii = GenerationVii(
                    icons = Icons(
                        front_default = "",
                        front_female = ""
                    ),
                    ultrasunultramoon = UltraSunUltraMoon(
                        front_default = "",
                        front_female = "",
                        front_shiny = "",
                        front_shiny_female = ""
                    )
                ),
                generationviii = GenerationViii(
                    icons = Icons(
                        front_default = "",
                        front_female = ""
                    )
                )
            )
        ),
        types = types.map { Type(slot = 0, type = TypeX(name = it, url = "")) },
        base_experience = baseExperience,
        is_default = isDefault,
        order = order,
        isLiked = isLiked,
        abilities = listOf(),
        cries = Cries("", ""),
        forms = listOf(),
        game_indices = listOf(),
        held_items = listOf(),
        location_area_encounters = "",
        moves = listOf(),
        past_abilities = listOf(),
        past_types = listOf(),
        species = Species("", ""),
        stats = listOf()




    )
}

fun testPokemon.toPokemonEntity(): PokemonEntity {
    return PokemonEntity(
        id = id,
        name = name,
        height = height,
        weight = weight,
        imageUrl = sprites.front_default ?: "",
        types = types.map { it.type.name },
        baseExperience = base_experience,
        isDefault = is_default,
        order = order,
        isLiked = isLiked
    )
}