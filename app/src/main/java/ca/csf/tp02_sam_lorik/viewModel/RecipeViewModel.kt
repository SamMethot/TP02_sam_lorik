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
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class RecipeViewModel(private val recipeDao: RecipeDao) : ViewModel() {

    var recipes by mutableStateOf<List<Recipe>>(emptyList())
        private set
    var isLoading: Boolean by mutableStateOf(false)

    private var favoriteRecipes by mutableStateOf<List<Recipe>>(emptyList())

    init {
        refresh()
        generateRandomRecipes()
        //loadFavorites()
    }

    private fun refresh() {
        isLoading = true
        viewModelScope.launch(Dispatchers.IO) {
            recipes = RecipeService.fetchRecipes()
            isLoading = false
        }
    }

    fun generateRandomRecipes(): List<Recipe> {
        return recipes.shuffled().take(10)
    }

    fun toggleFavorite(recipe: Recipe) {
        viewModelScope.launch(Dispatchers.IO) {
            if (favoriteRecipes.contains(recipe)) {
                recipeDao.remove(recipe)
                favoriteRecipes = favoriteRecipes.filter { it.id != recipe.id }

            } else {
                recipeDao.insert(recipe)
                favoriteRecipes = favoriteRecipes + recipe
            }
            //loadFavorites()
        }
    }

    private fun loadFavorites() {
        viewModelScope.launch(Dispatchers.IO) {
            recipeDao.getAll().collect { favoriteList ->
                favoriteRecipes = favoriteList
            }
        }
    }

    fun isFavorite(recipe: Recipe): Boolean {
        return favoriteRecipes.contains(recipe)
    }
}