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

    var recipes by mutableStateOf<List<Recipe>>(emptyList())
        private set
    var isLoading: Boolean by mutableStateOf(false)

    private val favoriteRecipeIds = mutableStateOf<Set<Int>>(emptySet())

    var favorites by mutableStateOf<List<Recipe>>(emptyList())
        private set

    var searchQuery by mutableStateOf("")

    init {
        refresh()
        loadFavorites()
    }

    private fun refresh() {
        isLoading = true
        viewModelScope.launch(Dispatchers.IO) {
            val fetchedRecipes = RecipeService.fetchRecipes()
            recipes = fetchedRecipes
            isLoading = false
        }
    }

    fun toggleFavorite(recipe: Recipe) {
        val isCurrentlyFavorite = isFavorite(recipe)
        if (isCurrentlyFavorite) {
            favoriteRecipeIds.value -= recipe.id
            removeFavorite(recipe)
        } else {
            favoriteRecipeIds.value += recipe.id
            addFavorite(recipe)
        }
    }

    private fun addFavorite(recipe: Recipe) {
        viewModelScope.launch(Dispatchers.IO) {
            recipeDao.insert(recipe)
            loadFavorites()
        }
    }

    fun removeFavorite(recipe: Recipe) {
        viewModelScope.launch(Dispatchers.IO) {
            recipeDao.remove(recipe)
            loadFavorites()
        }
    }

    private fun loadFavorites() {
        viewModelScope.launch(Dispatchers.IO) {
            recipeDao.getAll().collect { favoriteList ->
                favorites = favoriteList
                favoriteRecipeIds.value = favoriteList.map { it.id }.toSet()
            }
        }
    }

    fun isFavorite(recipe: Recipe): Boolean {
        return favoriteRecipeIds.value.contains(recipe.id)
    }

    fun updateSearchQuery() {
        viewModelScope.launch(Dispatchers.IO) {
            val filteredRecipes = RecipeService.fetchRecipesByIngredients(searchQuery)
            recipes = filteredRecipes
        }
    }

    fun removeAllFavorites() {
        viewModelScope.launch(Dispatchers.IO) {
            recipeDao.clearFavorites()
            favorites = emptyList()
        }
    }
}
