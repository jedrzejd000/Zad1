package com.example.zad3.others
import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.ImageBitmap

import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Modifier

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.graphicsLayer
import java.io.File


@Composable
fun AddPhoto(filePath: String) {
    val imageBitmap: ImageBitmap? = loadImageFromFile(filePath)


    imageBitmap?.let {
        Image(
            bitmap = it,
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
                .border(1.dp, Color.White)
                .size(100.dp)
                .scale(0.5f)
                .graphicsLayer(rotationZ = 30f)
                .padding(16.dp)
                .border(2.dp, Color.Red)
        )
    }
}


fun loadImageFromFile(filePath: String): ImageBitmap? {
    try {

        val inputStream = File(filePath).inputStream()
        val bitmap = BitmapFactory.decodeStream(inputStream)

        return bitmap.asImageBitmap()
    } catch (e: Exception) {
        e.printStackTrace()
    }

    return null
}




