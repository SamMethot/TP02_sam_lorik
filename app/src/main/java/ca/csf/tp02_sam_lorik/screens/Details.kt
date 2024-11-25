package ca.csf.tp02_sam_lorik.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import ca.csf.tp02_sam_lorik.model.Recipe
import ca.csf.tp02_sam_lorik.viewModel.RecipeViewModel

@Composable
fun DetailsScreen(recipeViewModel: RecipeViewModel, onClick: (Recipe) -> Unit) {
    Column {
        Text(text = "Details")
    }
}