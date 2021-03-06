package com.example.vitaura.helpers

import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Build
import android.text.Html
import android.util.Log
import androidx.annotation.RequiresApi
import coil.Coil
import coil.request.GetRequest
import com.example.vitaura.R
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL
import java.time.Instant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter


class ImageGetter(val resources: Resources, val context: Context) : Html.ImageGetter {

    var f: Boolean = false

    @RequiresApi(Build.VERSION_CODES.O)
    override fun getDrawable(source: String): Drawable? {
        var d: Drawable? = null
        CoroutineScope(Dispatchers.IO).launch {
            val imageLoader = Coil.imageLoader(context)
            val path = "https://www.$source"
            val request = GetRequest.Builder(context)
                .data(path)
                .build()
            val drawable = imageLoader.execute(request).drawable
            d = drawable
        }
        return d
    }

    fun collectValue(src: String, callback: suspend (Drawable) -> Unit) = GlobalScope.launch {
        flowOf(get(src)).map(callback).launchIn(this)
    }

    suspend fun get(src: String): Drawable {
        val imageLoader = Coil.imageLoader(context)
        val path = "https://www.$src"
        val request = GetRequest.Builder(context)
            .data(path)
            .build()
        val drawable = imageLoader.execute(request).drawable
        return drawable!!
    }
}