package com.example.zad3.others

import android.content.Context
import com.example.zad3.daos.ProductDao
import com.example.zad3.database.ShoppingListDatabase
import com.example.zad3.repository.Repository

object Graph {
    lateinit var db: ShoppingListDatabase
        private set

    val repository by lazy {
        Repository(
            shoppingListDao = db.shoppingListDao(),
            productDao = db.productDao()
        )
    }

    fun provide(context: Context){
        db = ShoppingListDatabase.getDatabase(context)
    }










}