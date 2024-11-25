package ca.csf.tp02_sam_lorik.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import ca.csf.tp02_sam_lorik.model.Recipe
import kotlinx.coroutines.flow.Flow

@Dao
interface RecipeDao {

    @Query("SELECT * FROM Recipe")
    fun getAll(): Flow<List<Recipe>>

    @Insert
    suspend fun insert(recipe: Recipe)

}