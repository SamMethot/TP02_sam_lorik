package ca.csf.tp02_sam_lorik.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.json.JSONArray
import org.json.JSONObject

@Entity
data class Recipe(
    val name: String,
    val ingredients: String,
    val image: String,
    @PrimaryKey val id: Int
) {
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
                val id = recipe.getInt("id")
                recipes.add(Recipe(name, ingredientsString, image, id))
            }
            return recipes
        }

        fun fromJsonAlternative(json: String): List<Recipe> {
            val recipesArray = JSONArray(json)
            val recipes = mutableListOf<Recipe>()

            for (i in 0 until recipesArray.length()) {
                val recipeJson = recipesArray.getJSONObject(i)
                val name = recipeJson.getString("title")

                val usedIngredientsArray = recipeJson.getJSONArray("usedIngredients")
                val missedIngredientsArray = recipeJson.getJSONArray("missedIngredients")

                val allIngredients = mutableListOf<String>()
                for (j in 0 until usedIngredientsArray.length()) {
                    val ingredientJson = usedIngredientsArray.getJSONObject(j)
                    allIngredients.add(ingredientJson.getString("original"))
                }

                for (k in 0 until missedIngredientsArray.length()) {
                    val missedIngredientJson = missedIngredientsArray.getJSONObject(k)
                    allIngredients.add(missedIngredientJson.getString("original"))
                }

                val ingredientsString = allIngredients.joinToString(", ")

                val image = recipeJson.getString("image")

                val id = recipeJson.getInt("id")

                recipes.add(Recipe(name, ingredientsString, image, id))
            }
            return recipes
        }
    }
}