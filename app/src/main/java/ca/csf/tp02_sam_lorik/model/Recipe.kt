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
            val recipesJson = recipeJson.getJSONArray("recipes")
            val recipes = mutableListOf<Recipe>()
            val ingredientNames = mutableListOf<String>()
            var ingredient: JSONObject?

            for (i in 0 until recipesJson.length()) {
                val recipe = recipesJson.getJSONObject(i)
                val name = recipe.getString("title")
                val image = recipe.getString("image")
                val analyzedInstruction = recipe.getJSONArray("analyzedInstructions")
                for (j in 0 until analyzedInstruction.length()) {
                    val instruction = analyzedInstruction.getJSONObject(j)
                    val steps = instruction.getJSONArray("steps")
                    for (k in 0 until steps.length()) {
                        val step = steps.getJSONObject(k)
                        val ingredients = step.getJSONArray("ingredients")
                        for (l in 0 until ingredients.length()) {
                            ingredient = ingredients.getJSONObject(l)
                            ingredientNames.add(ingredient.getString("name"))
                        }
                    }
                }
                val ingredients = ingredientNames.joinToString(", ")
                recipes.add(Recipe(name, ingredients, image))
            }
            return recipes
        }
    }
}
