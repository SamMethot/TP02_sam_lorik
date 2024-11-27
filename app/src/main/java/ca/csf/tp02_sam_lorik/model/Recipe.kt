package ca.csf.tp02_sam_lorik.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.json.JSONObject

@Entity
data class Recipe(
    val name: String,
    val ingredients: String,
    val image: String
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    companion object {
        fun fromJson(json: String): List<Recipe> {
            val recipeJson = JSONObject(json)
            val recipesArray = recipeJson.getJSONArray("recipes")
            val recipes = mutableListOf<Recipe>()

            for (i in 0 until recipesArray.length()) {
                val recipe = recipesArray.getJSONObject(i)
                val name = recipe.getString("title")
                val ingredientsJsonArray = recipe.getJSONArray("extendedIngredients")
                val ingredients = mutableListOf<String>()
                for (j in 0 until ingredientsJsonArray.length()) {
                    val ingredient = ingredientsJsonArray.getJSONObject(j)
                    ingredients.add(ingredient.getString("original"))
                }
                val ingredientsString = ingredients.joinToString(", ")
                val image = recipe.optString("image", "")
                recipes.add(Recipe(name, ingredientsString, image))
            }
            return recipes
        }
    }
}