package ca.csf.tp02_sam_lorik.screens

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import ca.csf.tp02_sam_lorik.model.Recipe
import ca.csf.tp02_sam_lorik.viewModel.RecipeViewModel

@Composable
fun HomeScreen(onClick: (Recipe) -> Unit, recipeViewModel: RecipeViewModel) {
    LazyColumn {
        items(recipeViewModel.randomRecipes ?: emptyList()) { recipe -> // Compris avec ChatGPT
            RecipeCard(recipe = recipe, onClick = onClick)
        }
    }
}
