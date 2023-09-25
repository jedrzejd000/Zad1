package com.example.zad3.repository


import com.example.zad3.daos.ProductDao
import com.example.zad3.daos.ShoppingListDao

import com.example.zad3.models.Product
import com.example.zad3.models.ShoppingList

import androidx.room.Insert
import kotlinx.coroutines.flow.Flow


class Repository(
    private val shoppingListDao: ShoppingListDao,
    private val productDao: ProductDao,
) {


    val getShoppingListSet = shoppingListDao.getShoppingListSet()

    val getShoppingListsWithProducts = shoppingListDao.getShoppingListsWithProducts()


    @Insert
    suspend fun insertProduct(product: Product) {
        productDao.insert(product)
    }

    suspend fun deleteProduct(product: Product) {
        productDao.delete(product)
    }


    suspend fun deleteShoppingList(shoppingList: ShoppingList) {
        shoppingListDao.delete(shoppingList)
    }

    suspend fun updateProduct(product: Product) {
        productDao.update(product)
    }

    suspend fun updateList(shoppingList: ShoppingList) {
        shoppingListDao.update(shoppingList)

    }


    suspend fun insertShoppingListAndGetId(shoppingList: ShoppingList): Long {
        return shoppingListDao.insert(shoppingList)
    }


    fun getProductList(id: Long): Flow<List<Product>> {
        return productDao.getProductListById(id)
    }

  suspend fun deleteAllProductsById(id: Long) {
        productDao.deleteAllProductsById(id)
    }

  fun getProductById(productId: Long): Flow<Product> {
        return productDao.getProduct(productId)
    }

    fun queryUpdate(product: Product, checked: Boolean) {

    productDao.queryUpdate(product.productId, checked)
    }


}


