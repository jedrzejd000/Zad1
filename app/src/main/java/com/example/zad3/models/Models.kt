package com.example.zad3.models

import androidx.room.ColumnInfo
import androidx.room.Entity

import androidx.room.PrimaryKey



@Entity(tableName = "shoppingLists",
)
data class ShoppingList(
    val name: String="",
    val productsPrice: Int=0,
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true) val id: Long=0
)



@Entity(tableName = "products",)
data class Product(
    @ColumnInfo(name = "productId")
    @PrimaryKey(autoGenerate = true)
    val productId: Long= 0,
    val name: String,
    @ColumnInfo(name = "shoppingListId")
    val shoppingListId: Long,
    val amount: Int,
    val kind:String,
    val photo:String,
    var isChecked:Boolean=false,
    val price:Int,
)