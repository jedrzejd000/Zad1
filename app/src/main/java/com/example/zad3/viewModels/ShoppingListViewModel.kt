package com.example.zad3.viewModels


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateMap

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope

import com.example.zad3.daos.ShoppingListWithProducts

import com.example.zad3.models.Product
import com.example.zad3.models.ShoppingList
import com.example.zad3.others.Graph
import com.example.zad3.repository.Repository

import kotlinx.coroutines.flow.collectLatest

import kotlinx.coroutines.launch



class ShoppingListViewModel(
    private val repository: Repository = Graph.repository
): ViewModel() {




   var state by mutableStateOf(ShoppingListState())
       private set


    init {
        getShoppingListSetWithProducts()
        getShoppingListsSet()
    }


    fun setPriceFromProducts(id: Long) {
        val flowProductList = repository.getProductList(id)

        viewModelScope.launch {
            val productList: MutableList<Product> = mutableListOf()

            flowProductList.collect { products ->
                productList.addAll(products)

                var price = 0
                productList.forEach { product ->
                    price += product.price
                }

                state = state.copy(price = price)
            }
        }
    }
    private fun getShoppingListsSet() {
        viewModelScope.launch {
            repository.getShoppingListSet.collectLatest {
                state = state.copy(
                    shoppingListSet = it
                )
            }
        }

    }

    private fun getShoppingListSetWithProducts() {
        viewModelScope.launch {
            repository.getShoppingListsWithProducts.collectLatest {
                state = state.copy(
                    shoppingListSetWithProducts = it
                )
            }
        }
    }


    fun createShoppingListAndProducts(shoppingListState: ShoppingListState) {
        viewModelScope.launch {
            val shoppingList = ShoppingList(name=shoppingListState.name)

            val shoppingListId = repository.insertShoppingListAndGetId(shoppingList)


            shoppingListState.productList.map { product ->
                repository.insertProduct(product=product.copy(shoppingListId = shoppingListId))
            }

        }
    }









    fun deleteShoppingList(shoppingList: ShoppingList,shoppingListState:ShoppingListState,shoppingListViewModel:ShoppingListViewModel) {
        viewModelScope.launch {
            repository.deleteShoppingList(shoppingList)

            repository.deleteAllProductsById(shoppingListState.shoppingListId)

        }

    }


    fun setName(it: String) {
        state = state.copy(name = it)
    }

    fun deleteProductClass(productToDelete: Product) {
        val updatedProductList = state.productList.filter { it != productToDelete }
        state = state.copy(productList = updatedProductList)
    }

    fun deleteProduct(it: Product) {
        viewModelScope.launch {

            repository.deleteProduct(it)
        }
    }
    fun clearState() {
        state = state.copy(
            name="",
            price = 0,
            productList = emptyList()
        )
    }

    fun loadShoppingList(shoppingList: ShoppingList){

        viewModelScope.launch {
            repository.updateList(shoppingList)
            repository.getProductList(shoppingList.id).collectLatest{
                state = state.copy(productList = it)
            }

        }

    }

    fun updateShopingList(shoppingListState: ShoppingListState,shoppingList:ShoppingList) {
        viewModelScope.launch {
            repository.updateList(
                shoppingList =shoppingList.copy(
                    name=shoppingListState.name, productsPrice = shoppingListState.price, id=shoppingList.id
                )
            )
            repository.deleteAllProductsById(shoppingList.id)


            shoppingListState.productList.map { product ->
                repository.insertProduct(product=product.copy(shoppingListId = shoppingList.id))
            }


        }

    }

    fun setShoppingList(shoppingList: ShoppingList) {
        state =state.copy(shoppingList =shoppingList )
    }

    fun addProductToList(p: List<Product>) {
        state = state.copy(productList = p)
    }

    fun setHashMap(hashMap: SnapshotStateMap<Product, Boolean>) {
        viewModelScope.launch {
            for ((key, value) in hashMap) {
               repository.updateProduct(
                  product = key.copy(isChecked = value)
               )
            }
        }
    }


    @Suppress("UNCHECKED_CAST")
    class ShoppingListViewModelFactor: ViewModelProvider.Factory{
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return ShoppingListViewModel() as T
        }
    }

}
    data class ShoppingListState(
        val name:String="",
        val hashMap: HashMap<Product, Boolean> = HashMap<Product, Boolean>(),
        val shoppingList: ShoppingList = ShoppingList("name",1,0),
        var shoppingListId: Long =0,
        var shoppingListSetWithProducts: List<ShoppingListWithProducts> = emptyList(),
        val productList: List<Product> = emptyList(),
        val productList2: List<Product> = productList,
        var  shoppingListSet: List<ShoppingList> = emptyList(),
        val isAddProductScreen: Boolean = false,
        val price: Int=0,
)
