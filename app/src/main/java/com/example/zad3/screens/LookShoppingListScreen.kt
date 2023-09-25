package com.example.zad3.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button

import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api

import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment

import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.zad3.models.Product

import com.example.zad3.viewModels.ProductViewModel
import com.example.zad3.viewModels.ProductViewModelFactor
import com.example.zad3.viewModels.ShoppingListState
import com.example.zad3.viewModels.ShoppingListViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LookShoppingListScreen(
    shoppingListState: ShoppingListState,
    setName: (String) -> Unit,
    modifier: Modifier = Modifier,
    shoppingListViewModel: ShoppingListViewModel,
) {
    shoppingListViewModel.setName(shoppingListState.name)
    shoppingListViewModel.loadShoppingList(shoppingListState.shoppingList)
    shoppingListViewModel.setPriceFromProducts(shoppingListState.shoppingListId)
    val productViewModel = viewModel<ProductViewModel>(factory = ProductViewModelFactor(0))

    val hashMap = remember { mutableStateMapOf<Product, Boolean>() }
    shoppingListState.productList.map { product ->
        hashMap.set(product,product.isChecked)
    }
    Scaffold(
        modifier = Modifier.padding(16.dp),
    ) {it
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = shoppingListState.name,
                    fontSize = 20.sp,
                )
                Text(
                    text = "cena wszystkich produktów: " + shoppingListState.price.toString(),
                    fontSize = 20.sp,
                )
            }

            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(shoppingListState.productList) { product ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(text = product.kind, fontSize = 15.sp)
                            Text(text = product.name, fontSize = 15.sp)
                            Text(text = "ilość: " + product.amount.toString(), fontSize = 15.sp)
                            Text(text = "jednostka: " + product.kind, fontSize = 15.sp)
                            Text(text = "cena: " + product.price.toString(), fontSize = 15.sp)
                        }

                        Checkbox(
                            checked = hashMap[product] == true,
                            onCheckedChange = { isChecked ->
                                hashMap[product] = isChecked
                            },
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { shoppingListViewModel.setHashMap(hashMap) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(text = "Zapisz")
            }
        }
    }}