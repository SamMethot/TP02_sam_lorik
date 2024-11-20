package ca.csf.tp02_sam_lorik.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ca.csf.tp02_sam_lorik.model.Recipe

@Database(entities = [Recipe::class], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase()  {
    abstract fun recipeDao(): RecipeDao
}

fun connectDatabase(applicationContext: Context): AppDatabase {
    return Room.databaseBuilder(
        applicationContext,
        AppDatabase::class.java, "examen-database"
    ).fallbackToDestructiveMigration().build()
}