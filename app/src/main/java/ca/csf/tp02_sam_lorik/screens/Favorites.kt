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
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import ca.csf.tp02_sam_lorik.screens.CustomIcon
import ca.csf.tp02_sam_lorik.screens.CustomIconVector
import ca.csf.tp02_sam_lorik.screens.CustomText
import ca.csf.tp02_sam_lorik.screens.CustomImage
import ca.csf.tp02_sam_lorik.viewModel.RecipeViewModel
import coil.compose.rememberImagePainter

@Composable
fun FavoritesScreen(
    recipeViewModel: RecipeViewModel,
    onBack: () -> Unit,
    onView: (Recipe) -> Unit
) {
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
                CustomIcon(
                    painter = painterResource(id = R.drawable.ic_arrow_back),
                    contentDescription = stringResource(R.string.back_button),
                    tint = Color.White,
                    modifier = Modifier.size(80.dp),
                )
            }

            Text(
                text = stringResource(R.string.favorites),
                fontSize = 24.sp,
                color = Color.White,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 16.dp, top = 16.dp)
            )

            IconButton(
                onClick = { recipeViewModel.removeAllFavorites() },
                modifier = Modifier.padding(bottom = 16.dp, top = 16.dp)
            ) {
                CustomIconVector(
                    imageVector = Icons.Default.Delete,
                    contentDescription = stringResource(R.string.delete_all),
                    modifier = Modifier.size(40.dp),
                    tint = Color.White
                )
            }
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(recipeViewModel.favorites) { recipe ->
                FavoriteItem(
                    recipe = recipe,
                    onView = { onView(recipe) },
                    onDelete = { recipeViewModel.removeFavorite(recipe) }
                )
            }
        }
    }
}

@Composable
fun FavoriteItem(recipe: Recipe, onView: () -> Unit, onDelete: () -> Unit) {
    val imagePainter = rememberImagePainter(
        data = if (recipe.image.isNotEmpty()) recipe.image else R.drawable.no_image_placeholder
    )
    val showButtons = remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { showButtons.value = !showButtons.value }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(
                modifier = Modifier.size(80.dp)
            ) {
                CustomImage(
                    painter = imagePainter,
                    contentDescription = stringResource(R.string.recipe_image),
                    modifier = Modifier.fillMaxSize()
                )
            }

            CustomText(
                recipe.name,
                20.sp,
                Color.White,
                Modifier.padding(start = 16.dp)
            )
        }

        if (showButtons.value) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
            ) {
                Button(
                    onClick = { onView() },
                    modifier = Modifier.weight(1f)
                ) {
                    Text(text = stringResource(R.string.view))
                }
                Button(
                    onClick = { onDelete() },
                    colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                        containerColor = Color.Red
                    ),
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = stringResource(R.string.delete),
                        color = Color.White
                    )
                }
            }
        }
    }
}
