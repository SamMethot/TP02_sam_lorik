package ca.csf.tp02_sam_lorik.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ca.csf.tp02_sam_lorik.database.RecipeDao
import ca.csf.tp02_sam_lorik.model.Recipe
import ca.csf.tp02_sam_lorik.service.RecipeService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RecipeViewModel(private val recipeDao: RecipeDao) : ViewModel() {

    val randomRecipes: List<Recipe>? = generateRandom()
    var recipes: List<Recipe>? = null
    var isLoading: Boolean by mutableStateOf(false)

    init {
        refresh()
        generateRandom()
    }

    private fun refresh() {
        isLoading = true
        viewModelScope.launch(Dispatchers.IO) {
            recipes = RecipeService.fetchRecipes()
            isLoading = false
        }
    }

    fun generateRandom () : List<Recipe>? {
        recipes = recipes?.shuffled()
        return recipes?.take(10)
    }
}