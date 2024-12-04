package ca.csf.tp02_sam_lorik.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ca.csf.tp02_sam_lorik.R
import ca.csf.tp02_sam_lorik.model.Recipe
import ca.csf.tp02_sam_lorik.viewModel.RecipeViewModel
import coil.compose.rememberImagePainter

@Composable
fun DetailsScreen(
    recipe: Recipe,
    onBack: () -> Unit,
    recipeViewModel: RecipeViewModel
) {
    var isFavorite by remember { mutableStateOf(false) }

    LaunchedEffect(recipe) {
        isFavorite = recipeViewModel.isFavorite(recipe)
    }

    val imagePainter = rememberImagePainter(
        data = if (recipe.image.isNotEmpty()) recipe.image else R.drawable.no_image_placeholder // Source image : https://uptownprinters.ca/index.php/laptop-collections/laptops/products/surface-lptp-go2-i5-8-128-eng-comm-plat
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.DarkGray)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Black),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            IconButton(
                onClick = { onBack() },
                modifier = Modifier.padding(bottom = 16.dp, top = 16.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_arrow_back),
                    contentDescription = stringResource(R.string.back_button),
                    tint = Color.White,
                    modifier = Modifier
                        .size(80.dp)

                )
            }

            Text(
                text = stringResource(R.string.details),
                fontSize = 24.sp,
                color = Color.White,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(bottom = 16.dp, top = 16.dp)
            )

            IconButton(
                onClick = {
                    recipeViewModel.toggleFavorite(recipe)
                    isFavorite = !isFavorite
                },
                modifier = Modifier
                    .padding(8.dp)
            ) {
                Icon(
                    painter = painterResource(
                        id = if (isFavorite) R.drawable.heart_filled
                        else R.drawable.heart_outline
                    ),
                    contentDescription = stringResource(R.string.favorite_button),
                    tint = if (isFavorite) Color.Red else Color.White
                )
            }
        }

        Text(
            text = recipe.name,
            fontSize = 20.sp,
            color = Color.White,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp, top = 16.dp)
        )

        Image(
            painter = imagePainter,
            contentDescription = stringResource(R.string.recipe_image),
            modifier = Modifier
                .fillMaxWidth()
                .size(300.dp)
        )

        Text(
            text = stringResource(R.string.ingredients),
            fontSize = 20.sp,
            color = Color.White,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        LazyColumn {
            item {
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
    }
}