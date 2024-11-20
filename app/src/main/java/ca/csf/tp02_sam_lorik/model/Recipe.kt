package ca.csf.tp02_sam_lorik.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.json.JSONObject

@Entity
data class Recipe(
    val name: String,
    val ingredients: List<String>,
    val image: String
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    companion object {
        fun fromJson(json: String): List<Recipe> {
            val recipeJson = JSONObject(json)

            val recipesJson = recipeJson.getJSONObject("recipes")
            val recipes = mutableListOf<Recipe>()
            recipesJson.keys().forEach {
                val recipe = recipesJson.getJSONObject(it)
                val name = recipe.getString("name")
                val ingredientsJsonArray = recipe.getJSONArray("ingredients")
                val ingredients = mutableListOf<String>()

                for (i in 0 until ingredientsJsonArray.length()) {
                    ingredients.add(ingredientsJsonArray.getString(i))
                }
                val image = recipe.getString("image")

                recipes.add(Recipe(name, ingredients, image))
            }
            return recipes
        }
    }
}
