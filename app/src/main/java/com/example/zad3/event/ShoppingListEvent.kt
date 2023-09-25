package com.example.zad3.event

sealed interface ShoppingListEvent{
    object HideDialog: ShoppingListEvent
}