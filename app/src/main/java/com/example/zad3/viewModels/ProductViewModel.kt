package com.example.zad3.viewModels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import com.example.zad3.models.Product
import com.example.zad3.models.ShoppingList
import com.example.zad3.others.Graph
import com.example.zad3.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Date


class ProductViewModel
constructor(
    private val itemId: Int,
    private val repository: Repository = Graph.repository,
) : ViewModel() {



    fun onNameChange(s: String) {
    state = state.copy(name = s)
    }

    fun onPhotoChange(s: String) {
        state = state.copy(photo = s)
    }

    fun onKindChange(s: String) {
        state = state.copy(kind = s)
    }

    fun onAmountChange(i: Int) {
        state = state.copy(amount= i)
    }


    fun clearState() {
        state = state.copy(
            name = "",
            kind = "",
            amount = 0,
            price = 0,
            photo = "",

        )
    }




    var state by mutableStateOf(ProductState())
        private set


    fun onPriceChange(i: Int) {
state = state.copy(price = i)
    }






}



@Suppress("UNCHECKED_CAST")
class ProductViewModelFactor(private  val id: Int): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ProductViewModel(itemId = id) as T
    }
}

data class ProductState(
    val name:String ="",
    val shoppingListId: Int=0,
    val amount: Int=0,
    val kind:String="",
    val photo:String="",
    val isChecked:Boolean=false,
    val price:Int=0,
    val shoppingList: List<ShoppingList> = emptyList(),
)