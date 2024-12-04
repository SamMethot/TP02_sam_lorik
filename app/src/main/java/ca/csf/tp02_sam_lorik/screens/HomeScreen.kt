package ca.csf.tp02_sam_lorik.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
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
fun HomeScreen(recipeViewModel: RecipeViewModel, onClick: (Recipe) -> Unit, onLike: () -> Unit) {
    val recipes = recipeViewModel.recipes
    val isLoading = recipeViewModel.isLoading
    val searchQuery = recipeViewModel.searchQuery

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
            Image(
                painter = painterResource(id = R.drawable.appicon),
                contentDescription = stringResource(R.string.app_icon),
                modifier = Modifier
                    .padding(bottom = 16.dp, top = 16.dp)
                    .size(80.dp)
            )

            Text(
                text = stringResource(R.string.homepage),
                fontSize = 24.sp,
                color = Color.White,
                textAlign = TextAlign.Center
            )

            Image(
                painter = painterResource(id = R.drawable.heart_filled),
                contentDescription = stringResource(R.string.app_icon),
                modifier = Modifier
                    .padding(end = 16.dp)
                    .size(40.dp)
                    .clickable { onLike() }
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
                .background(Color.Black),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                value = searchQuery,
                onValueChange = { recipeViewModel.searchQuery = it },
                label = { Text(stringResource(R.string.search)) },
                trailingIcon = {
                    IconButton(onClick = { recipeViewModel.updateSearchQuery() }) {
                        Icon(
                            painter = painterResource(R.drawable.search),
                            contentDescription = stringResource(R.string.search_icon),
                            modifier = Modifier.size(24.dp)
                        )
                    }
                },
                modifier = Modifier
                    .weight(1f)
                    .padding(bottom = 16.dp)
            )
        }


        if (isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            if (recipes.isEmpty() || recipes.all { it.image.isEmpty() && it.name.isEmpty() }) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = stringResource(R.string.nothing_found),
                        fontSize = 25.sp,
                        color = Color.Gray
                    )
                }
            } else {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(recipes) { recipe ->
                        RecipeItem(
                            recipe = recipe,
                            onClick = { onClick(recipe) },
                            recipeViewModel = recipeViewModel
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun RecipeItem(recipe: Recipe, onClick: (Recipe) -> Unit, recipeViewModel: RecipeViewModel) {
    var isFavorite by remember { mutableStateOf(false) }

    LaunchedEffect(recipe) {
        isFavorite = recipeViewModel.isFavorite(recipe)
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f)
            .clip(RoundedCornerShape(16.dp))
            .clickable { onClick(recipe) }
    ) {
        val imagePainter = rememberImagePainter(
            data = if (recipe.image.isNotEmpty()) recipe.image else R.drawable.no_image_placeholder // Source image : https://uptownprinters.ca/index.php/laptop-collections/laptops/products/surface-lptp-go2-i5-8-128-eng-comm-plat
        )
        Image(
            painter = imagePainter,
            contentDescription = stringResource(R.string.recipe_image),
            modifier = Modifier
                .fillMaxSize()
                .alpha(0.8f),
            contentScale = androidx.compose.ui.layout.ContentScale.Crop,
        )

        Text(
            text = recipe.name,
            modifier = Modifier.align(Alignment.Center),
            color = Color.White,
            fontSize = 16.sp,
            textAlign = TextAlign.Center
        )

        IconButton(
            onClick = {
                recipeViewModel.toggleFavorite(recipe)
                isFavorite = !isFavorite
            },
            modifier = Modifier
                .align(Alignment.TopEnd)
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
}
