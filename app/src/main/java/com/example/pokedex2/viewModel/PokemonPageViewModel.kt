package com.example.pokedex2.viewModel


import android.util.Log
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedex2.data.remote.EvolutionDetailUI
import com.example.pokedex2.data.remote.PokemonApiService
import com.example.pokedex2.data.remote.PokemonSpecies
import com.example.pokedex2.data.remote.json.PokemonResult
import com.example.pokedex2.ui.Filters.addSpaceAndCapitalize
import com.example.pokedex2.ui.Filters.capitalizeFirstLetter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class PokemonPageViewModel @Inject constructor(
    private val pokemonApiService: PokemonApiService
) : ViewModel() {

    //Add here for creating endpoints
    private val _pokemonDetail = MutableStateFlow<PokemonResult?>(null)
    val pokemonDetail: StateFlow<PokemonResult?> = _pokemonDetail

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    private val _pokemonImage = MutableStateFlow<String?>(null)
    val pokemonImage: StateFlow<String?> = _pokemonImage

    private val _pokemonId = MutableStateFlow<String?>(null)
    val pokemonId: StateFlow<String?> = _pokemonId

    private val _pokemonLocations = MutableStateFlow<List<String>>(emptyList())
    val pokemonLocations: StateFlow<List<String>> = _pokemonLocations

    private val _abilities = MutableStateFlow<List<String>>(emptyList())
    val abilities: StateFlow<List<String>> = _abilities

    private val _pokemonSpecies = MutableStateFlow<PokemonSpecies?>(null)
    val pokemonSpecies: StateFlow<PokemonSpecies?> = _pokemonSpecies

    private val _growthRate = MutableStateFlow<String>("Unknown")
    val growthRate: StateFlow<String> = _growthRate

    private val _pokemonStats = MutableStateFlow<List<Pair<String, Int>>>(emptyList())
    val pokemonStats: StateFlow<List<Pair<String, Int>>> = _pokemonStats

    private val _evolvesTo = MutableStateFlow<List<EvolutionDetailUI>>(emptyList())
    val evolvesTo: StateFlow<List<EvolutionDetailUI>> = _evolvesTo

    private val _characteristicDescription = MutableStateFlow<String>("")
    val characteristicDescription: StateFlow<String> = _characteristicDescription


    //Then add it here and then at PokemonPage
    fun fetchPokemonDetail(nameOrId: String) {
        viewModelScope.launch {
            try {
                _errorMessage.value = null
                val detail = pokemonApiService.getPokemonDetail(nameOrId)
                // Set pokemon details
                _pokemonDetail.value = detail

            } catch (e: Exception) {
                _errorMessage.value = "Failed to fetch Pokemon details: ${e.message}"
                _pokemonDetail.value = null
                _pokemonImage.value = null

            }
        }
    }


    fun fetchPokemonEncounters(encountersUrl: String) {
        viewModelScope.launch {
            try {
                val encounters = pokemonApiService.getPokemonEncounters(encountersUrl)
                _pokemonLocations.value =
                    encounters.map {
                        it.location_area.name.capitalizeFirstLetter().addSpaceAndCapitalize()
                    }
            } catch (e: Exception) {
                _pokemonLocations.value = listOf("No locations available")
            }
        }
    }


    fun fetchPokemonAbilities(pokemonName: String) {
        viewModelScope.launch {
            try {
                val pokemonDetail = pokemonApiService.getPokemonDetail(pokemonName)
                val abilityNames = pokemonDetail.abilities.map { it.ability.name }

                _abilities.value = abilityNames
            } catch (e: Exception) {
                _abilities.value = listOf("Error fetching abilities")
            }
        }
    }

    fun fetchPokemonSpecies(nameOrId: String) {
        viewModelScope.launch {
            try {
                val species = pokemonApiService.getPokemonSpecies(nameOrId)
                _pokemonSpecies.value = species
                _growthRate.value = species.growth_rate.name // Normalize here
                Log.d("PokemonGrowthRate", "Fetched Growth Rate: $growthRate")
            } catch (e: Exception) {
                _growthRate.value = "unknown"
            }
        }
    }

    fun fetchEvolutionChain(pokemonIdOrName: String) {
        viewModelScope.launch {
            try {
                // Fetch Pokémon species to get the evolution chain URL
                val species = pokemonApiService.getPokemonSpecies(pokemonIdOrName)
                val evolutionChainUrl = species.evolution_chain.url

                // Extract evolution chain ID from the URL
                val evolutionChainId = evolutionChainUrl.split("/").last { it.isNotEmpty() }.toInt()

                // Fetch the evolution chain using the extracted ID
                val evolutionChain = pokemonApiService.getEvolutionChain(evolutionChainId)

                // Find the current Pokémon in the chain
                val currentPokemon = EvolutionDetailUI(
                    name = species.name.capitalizeFirstLetter().addSpaceAndCapitalize(),
                    imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${species.id}.png",
                    requirement = "This is the final form"
                )
                val evolvesToList = mutableListOf<EvolutionDetailUI>()

                var currentChainLink = evolutionChain.chain

                // Traverse the evolution chain to find evolves_to for the current Pokémon
                while (currentChainLink.species.name.lowercase() != pokemonIdOrName.lowercase()) {
                    if (currentChainLink.evolves_to.isEmpty()) {
                        break // No further evolution links
                    }
                    currentChainLink =
                        currentChainLink.evolves_to.first() // Continue to the next link
                }

                // Process the "evolves_to" list for the current Pokémon
                for (evolution in currentChainLink.evolves_to) {
                    val speciesName =
                        evolution.species.name.capitalizeFirstLetter().addSpaceAndCapitalize()
                    val minLevel = evolution.evolution_details.firstOrNull()?.min_level
                    val imageUrl =
                        "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${
                            evolution.species.url.split(
                                "/"
                            ).last { it.isNotEmpty() }
                        }.png"

                    val requirement = if (minLevel != null) {
                        "Level $minLevel"
                    } else {
                        "Unknown requirement"
                    }

                    evolvesToList.add(
                        EvolutionDetailUI(
                            name = speciesName,
                            imageUrl = imageUrl,
                            requirement = requirement
                        )
                    )
                }

                // Handle the case where the Pokémon has no further evolutions
                if (evolvesToList.isEmpty()) {
                    evolvesToList.add(currentPokemon) // Add the current Pokémon as its own max evolution
                }

                _evolvesTo.value = evolvesToList

            } catch (e: Exception) {
                _evolvesTo.value = listOf(
                    EvolutionDetailUI(
                        name = "Error",
                        imageUrl = "",
                        requirement = "Error fetching evolution data"
                    )
                )
                Log.e("fetchEvolutionChain", "Error fetching evolution chain: ${e.message}")
            }
        }
    }


    fun fetchPokemonStats(name: String) {
        viewModelScope.launch {
            try {
                val pokemon = pokemonApiService.getPokemonStats(name)
                val stats = pokemon.stats.map {
                    it.stat.name.capitalizeFirstLetter().addSpaceAndCapitalize() to it.base_stat
                }
                _pokemonStats.value = stats
            } catch (e: Exception) {
                Log.e("fetchPokemonStats", "Error fetching stats: ${e.message}")
                _pokemonStats.value = emptyList()
            }
        }
    }

    fun fetchCharacteristic(id: Int) {
        viewModelScope.launch {
            try {
                val characteristic = pokemonApiService.getCharacteristic(id)

                // Filter the descriptions to find the one in English
                val englishDescription = characteristic.descriptions
                    .find { it.language.name == "en" }
                    ?.description

                _characteristicDescription.value = englishDescription ?: "Description not available"
            } catch (e: Exception) {
                _characteristicDescription.value = "Error fetching description"
                Log.e("fetchCharacteristic", "Error: ${e.message}")
            }
        }
    }


    fun getGrowthRateProgress(growthRate: String): Float {
        return when (growthRate.trim().lowercase()) {
            "slow" -> 0.2f
            "medium-slow" -> 0.4f
            "medium" -> 0.6f
            "fast" -> 0.8f
            "very fast" -> 1.0f
            else -> 0.0f // Default for unknown rates
        }
    }


    fun getGrowthRateColor(growthRate: String): Color {
        return when (growthRate.trim().lowercase()) {
            "slow" -> Color.Blue // Slow = Blue
            "medium-slow" -> Color.Green // Medium-Slow = Green
            "medium" -> Color.Yellow // Medium = Yellow
            "fast" -> Color(0xFFFFA500) // Fast = Orange
            "very fast" -> Color.Red // Very Fast = Red
            else -> Color.Gray // Default for unknown rates
        }
    }


    fun getStatColor(statName: String): Color {
        return when (statName.lowercase()) {
            "hp" -> Color(0xFFFF0000) // Red for HP
            "attack" -> Color(0xFFFFA500) // Orange for Attack
            "defense" -> Color(0xFF00FF00) // Green for Defense
            "special-attack" -> Color(0xFF1E90FF) // Blue for Special Attack
            "special-defense" -> Color(0xFF8A2BE2) // Purple for Special Defense
            "speed" -> Color(0xFFFFD700) // Gold for Speed
            else -> Color(0xFF808080) // Gray for unknown or unhandled stats
        }
    }


}



