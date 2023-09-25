package com.example.zad3.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController

import com.example.zad3.entry.ProductEntry
import com.example.zad3.viewModels.ProductViewModel
import com.example.zad3.viewModels.ShoppingListState


import com.example.zad3.models.ShoppingList
import com.example.zad3.others.AddPhoto
import com.example.zad3.viewModels.ProductViewModelFactor
import com.example.zad3.viewModels.ShoppingListViewModel
import com.example.zad3.viewModels.ShoppingListViewModel.ShoppingListViewModelFactor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddShoppingListScreen(
    shoppingListState: ShoppingListState,
    modifier: Modifier = Modifier,
    shoppingListViewModel: ShoppingListViewModel,
    shoppingList:ShoppingList,
    navController: NavController
) {




    val viewModelProduct = viewModel<ProductViewModel>(factory = ProductViewModelFactor(0))
    val viewModelShoppingList =  viewModel<ShoppingListViewModel>()
    var openAlertDialog = remember { mutableStateOf(false) }





    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
                openAlertDialog.value = true
            }) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Dodaj produkt"
                )
            }
        },
        modifier = Modifier.padding(16.dp),
    ) {
        it
        when {
            openAlertDialog.value -> {
                ProductEntry(
                    onDismissRequest = { openAlertDialog.value = false },
                    onNameChange = viewModelProduct::onNameChange,
                    state = viewModelProduct.state,
                    onPhotoChange = viewModelProduct::onPhotoChange,
                    onKindChange = viewModelProduct::onKindChange,
                    onAmountChange = viewModelProduct::onAmountChange,
                    shoppingListState = shoppingListState,
                    shoppingListViewModel = shoppingListViewModel,
                    productViewModel = viewModelProduct,
                    onPriceChange = viewModelProduct::onPriceChange,
                )
            }
        }

        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                OutlinedTextField(
                    value = shoppingListState.name,
                    onValueChange = { shoppingListViewModel.setName(it) },
                    label = { Text(text = "name") },
                    modifier = Modifier
                        .weight(1f)
                        .background(Color.LightGray)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Button(onClick = {
                    shoppingListViewModel.createShoppingListAndProducts(shoppingListState = shoppingListState)
                    shoppingListViewModel.clearState()
                    navController.popBackStack()
                }) {
                    Text(text = "Zapisz")
                }
            }

            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(shoppingListState.productList) { item ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                            .background(Color.LightGray)
                            .border(1.dp, Color.Black)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxSize()
                        ) {
                            Column(
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(16.dp)
                                    .height(100.dp)
                            ) {
                                Text(text = item.name, fontSize = 15.sp)
                                Text(text = "ilość: " + item.amount.toString(), fontSize = 15.sp)
                                Text(text = "jednostka: " + item.kind, fontSize = 15.sp)
                                Text(text = "cena: " + item.price.toString(), fontSize = 15.sp)
                            }
                            Box(
                                modifier = Modifier
                                    .size(100.dp)
                                    .padding(16.dp)
                            ) {
                                if (item.photo.isNullOrBlank()) {
                                    AddPhoto(filePath = item.photo)
                                }
                            }
                            IconButton(
                                onClick = {
                                    shoppingListViewModel.deleteProductClass(item)
                                },
                                modifier = Modifier.padding(8.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = "Usuń produkt"
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

