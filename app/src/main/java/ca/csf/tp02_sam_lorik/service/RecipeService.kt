package ca.csf.tp02_sam_lorik.service

import ca.csf.tp02_sam_lorik.model.Recipe
import java.net.HttpURLConnection
import java.net.URL

object RecipeService {
    private const val API_URL =
        "https://api.spoonacular.com/recipes/random?number=10&apiKey=167769ea95324c128dbe7dd51d08f420"

    fun fetchRecipes(): List<Recipe> {
        val url = URL(API_URL)
        val connection = url.openConnection() as HttpURLConnection

        val data = connection.run {
            requestMethod = "GET"
            inputStream.bufferedReader().readText()
        }
        return Recipe.fromJson(data)
    }
}