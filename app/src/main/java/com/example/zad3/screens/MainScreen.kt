package com.example.zad3.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.zad3.models.ShoppingList
import com.example.zad3.viewModels.ShoppingListViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val shoppingListViewModel = viewModel(modelClass = ShoppingListViewModel::class.java)
    var shoppingListState = shoppingListViewModel.state
    val shoppingList = ShoppingList()
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "mainPage"
    ) {

        composable("mainPage") { entry ->
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = { Text(text = "Listy zakupów") },

                    )
                },
                floatingActionButton = {
                    FloatingActionButton(
                        onClick = { navController.navigate("AddShoppingListViewRoute") }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Dodaj nową liste"
                        )
                    }
                },
                content =
            {
                it
                LazyColumn(
                    contentPadding = PaddingValues(16.dp),
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {

                    items(shoppingListState.shoppingListSet) { shoppingListItem ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color.LightGray)
                                .padding(8.dp)
                        ) {
                            Column(
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(4.dp)
                            ) {
                                Text(text = shoppingListItem.name)
                            }

                            IconButton(onClick = {
                                shoppingListViewModel.deleteShoppingList(shoppingListItem, shoppingListState, shoppingListViewModel)
                            }) {
                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = "Usuń"
                                )
                            }

                            IconButton(onClick = {
                                shoppingListViewModel.setName(shoppingListItem.name)
                                shoppingListViewModel.setShoppingList(shoppingListItem)
                                shoppingListViewModel.loadShoppingList(shoppingListItem)
                                navController.navigate("EditListViewRoute")
                            }) {
                                Icon(
                                    imageVector = Icons.Default.Edit,
                                    contentDescription = "Edytuj"
                                )
                            }

                            IconButton(onClick = {
//                                shoppingListViewModel.setName(shoppingListItem.name)
                                shoppingListViewModel.setShoppingList(shoppingListItem)
//                                shoppingListViewModel.loadShoppingList(shoppingListItem)
//                                shoppingListViewModel.setPriceFromProducts(shoppingListItem.id)
                                navController.navigate("LookShoppingListViewRoute")
                            }) {
                                Icon(
                                    imageVector = Icons.Default.Search,
                                    contentDescription = "Podgląd"
                                )
                            }
                        }
                    }
                }
            }
            )
        }
        val shoppingListEdit  = shoppingListState.shoppingList
        composable("EditListViewRoute") {
            Column {
                Button(onClick = {
                    navController.popBackStack()
                    shoppingListViewModel.clearState()
                }) {
                    Text(text = "Powrót")
                }
                EditShoppingListScreen(
                    shoppingList=shoppingListEdit,
                    shoppingListState =shoppingListState ,
                    shoppingListViewModel = shoppingListViewModel,
                    navController = navController,
                )
            }

        }
        composable("AddShoppingListViewRoute") {
            Column {
                Button(onClick = {
                    navController.popBackStack()
                    shoppingListViewModel.clearState()
                }) {
                    Text(text = "Powrót")
                }

                AddShoppingListScreen(shoppingListViewModel=shoppingListViewModel,shoppingList=shoppingList,
                    shoppingListState = shoppingListState,navController=navController)

            }
        }
        composable("LookShoppingListViewRoute") {
            Button(onClick = {

                navController.popBackStack()
                shoppingListViewModel.clearState()
            }) {
                Text(text = "Powrót")
            }
            LookShoppingListScreen(shoppingListState = shoppingListState,
                setName = shoppingListViewModel::setName,
                shoppingListViewModel =shoppingListViewModel )
        }
    }

}

