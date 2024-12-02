package ca.csf.tp02_sam_lorik

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ca.csf.tp02_sam_lorik.database.connectDatabase
import ca.csf.tp02_sam_lorik.model.Recipe
import ca.csf.tp02_sam_lorik.screens.DetailsScreen
import ca.csf.tp02_sam_lorik.screens.FavoritiesScreen
import ca.csf.tp02_sam_lorik.screens.HomeScreen
import ca.csf.tp02_sam_lorik.screens.Screens
import ca.csf.tp02_sam_lorik.ui.theme.TP02_sam_lorikTheme
import ca.csf.tp02_sam_lorik.viewModel.RecipeViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TP02_sam_lorikTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Navigation(
                        innerPadding = innerPadding
                   )
                }
            }
        }
    }
}

@Composable
fun Navigation(innerPadding : PaddingValues) {
    val navigationController = rememberNavController()
    val db = connectDatabase(applicationContext = LocalContext.current)

    val recipeViewModel: RecipeViewModel = viewModel(factory = viewModelFactory {
        initializer { RecipeViewModel(db.recipeDao()) }
    })

    var recipe = Recipe("", "", "")


    NavHost(
        navController = navigationController,
        startDestination = Screens.HOME.title,
        modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize(),
    ) {
        composable(Screens.HOME.title) {
            HomeScreen(
                recipeViewModel = recipeViewModel,
                onClick = {
                    recipe = it
                    navigationController.navigate(Screens.DETAILS.title)
                },
                onLike = { navigationController.navigate(Screens.FAVORITES.title) }
            )
        }

        composable(Screens.DETAILS.title) {
            DetailsScreen(
                onLike = { navigationController.navigate(Screens.FAVORITES.title) },
                onBack = { navigationController.popBackStack() },
                recipe = recipe
            )
        }

        composable(Screens.FAVORITES.title) {
            FavoritiesScreen(
                onBack = { navigationController.popBackStack() }
            )
        }

    }
}