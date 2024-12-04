package ca.csf.tp02_sam_lorik.service

import ca.csf.tp02_sam_lorik.model.Recipe
import java.net.HttpURLConnection
import java.net.URL

object RecipeService {
    private const val API_URL = "https://api.spoonacular.com/recipes/random?number=10&apiKey=164f2c05e9e4423bb7b25b932c4ad8ce"

    fun getSearchApiUrl(ingredients: String): String {
        return "https://api.spoonacular.com/recipes/findByIngredients?ingredients=$ingredients&number=10&apiKey=164f2c05e9e4423bb7b25b932c4ad8ce"
    }

    fun fetchRecipes(): List<Recipe> {
        val url = URL(API_URL)
        val connection = url.openConnection() as HttpURLConnection

        val data = connection.run {
            requestMethod = "GET"
            inputStream.bufferedReader().readText()
        }
        return Recipe.fromJson(data)
    }

    fun fetchRecipesByIngredients(ingredients: String): List<Recipe> {
        return if (ingredients.isEmpty()) {
            fetchRecipes()
        } else {
            val url = URL(getSearchApiUrl(ingredients))
            val connection = url.openConnection() as HttpURLConnection

            val data = connection.run {
                requestMethod = "GET"
                inputStream.bufferedReader().readText()
            }
            Recipe.fromJsonAlternative(data)
        }
    }
}