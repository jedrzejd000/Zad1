package com.example.zad3.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.zad3.daos.ProductDao
import com.example.zad3.models.Product
import com.example.zad3.models.ShoppingList
import com.example.zad3.daos.ShoppingListDao
//@TypeConverters(value = [DateConverter::class])
@Database(
    entities = [ShoppingList::class, Product::class],
    version = 1,
    exportSchema = false
)
abstract class ShoppingListDatabase: RoomDatabase() {
    abstract fun shoppingListDao(): ShoppingListDao
    abstract fun productDao():ProductDao


    companion object{
        @Volatile
        var INSTANCE:ShoppingListDatabase? = null
        fun getDatabase(context: Context):ShoppingListDatabase{
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context,
                    ShoppingListDatabase::class.java,
                    "shopping_db"
                ).build()
                INSTANCE = instance
                return instance
            }
        }

    }

}
