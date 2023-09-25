package com.example.zad3.entry


import android.net.Uri
import android.provider.ContactsContract.DisplayPhoto
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable


import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource

import androidx.compose.ui.unit.dp
import com.example.zad3.R
import com.example.zad3.models.Product
import com.example.zad3.others.AddPhoto

import com.example.zad3.viewModels.ProductState
import com.example.zad3.viewModels.ProductViewModel
import com.example.zad3.viewModels.ShoppingListState
import com.example.zad3.viewModels.ShoppingListViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductEntry(
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    onNameChange: (String) ->Unit,
    state: ProductState,
    onPhotoChange: (String)->Unit,
    onKindChange: (String) -> Unit,
    onAmountChange: (Int) -> Unit,
    shoppingListState: ShoppingListState,
    shoppingListViewModel: ShoppingListViewModel,
    productViewModel:ProductViewModel,
    onPriceChange: (Int) -> Unit,
) {
    var productList = shoppingListState.productList
    AlertDialog(modifier = modifier,
        onDismissRequest = {
        },
        text = {
            Column(
                modifier = modifier.padding(16.dp)
            ) {
                TextField(
                    value = state.name,
                    onValueChange = { onNameChange(it) },
                    label = { Text(text = "nazwa") },
                    modifier = Modifier.fillMaxWidth(),
                )
                Spacer(modifier = Modifier.size(12.dp))

                TextField(
                    value = state.amount.toString(),
                    onValueChange = {
                        try {
                            onAmountChange(it.toInt())
                        } catch (e: Exception) {
                            print(e)
                        }

                    },
                    label = { Text(text = "liczba") },
                    modifier = Modifier.fillMaxWidth(),
                )
                Spacer(modifier = Modifier.size(12.dp))


                TextField(
                    value = state.kind,
                    onValueChange = { onKindChange(it) },
                    label = { Text(text = "jednostki") },
                    modifier = Modifier.fillMaxWidth(),
                )
                Spacer(modifier = Modifier.size(12.dp))
                TextField(
                    value = state.price.toString(),

                    onValueChange = {
                        try {
                            onPriceChange(it.toInt())
                        } catch (e: Exception) {
                            print(e)
                        }
                       },
                    label = { Text(text = "całkowita cena") },
                    modifier = Modifier.fillMaxWidth(),
                )
                Spacer(modifier = Modifier.size(12.dp))
                TextField(
                    value = state.photo,
                    onValueChange = { onPhotoChange(it) },
                    label = { Text(text = "scieżka dla zdęcia") },
                    modifier = Modifier.fillMaxWidth(),
                )

            }

        },
        confirmButton = {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.CenterEnd
            ) {
                Button(onClick = {
//                    saveProduct()
                  val p =  Product(name = state.name, amount = state.amount,kind=state.kind, photo = state.photo, price = state.price, shoppingListId = 0)
                    productList =productList+p
                    shoppingListViewModel.addProductToList(productList)
                    productViewModel.clearState()
                    onDismissRequest()

                }) {
                    Text(text = "Zapisz")
                }
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    productViewModel.clearState()
                    onDismissRequest()
                }
            ) {
                Text("Anuluj")
            }
        }

    )

}





