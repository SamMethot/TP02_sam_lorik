package ca.csf.tp02_sam_lorik.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ca.csf.tp02_sam_lorik.model.Recipe
import ca.csf.tp02_sam_lorik.viewModel.RecipeViewModel
import coil.compose.rememberImagePainter

@Composable
fun DetailsScreen(recipe: Recipe, onLike: (Recipe) -> Unit, recipeViewModel: RecipeViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "DETAILS",
            fontSize = 24.sp,
            color = Color.Black,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )

        Image(
            painter = rememberImagePainter(data = recipe.image),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )

        Text(
            text = "Ingredients:",
            fontSize = 20.sp,
            color = Color.Black,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        recipe.ingredients.split(", ").forEach {
            Text(
                text = "- $it",
                fontSize = 16.sp,
                color = Color.Gray,
                modifier = Modifier.padding(start = 16.dp, bottom = 4.dp)
            )
        }
    }
}