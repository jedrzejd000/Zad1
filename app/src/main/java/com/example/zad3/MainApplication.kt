package com.example.zad3

import android.app.Application
import com.example.zad3.others.Graph


class MainApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        Graph.provide(this)
    }
}