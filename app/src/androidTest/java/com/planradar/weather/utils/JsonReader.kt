package com.planradar.weather.utils

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import java.io.IOException
import java.io.InputStream

object JsonReader {
  fun getJson(path: String): String {
    return try {
      val context = ApplicationProvider.getApplicationContext<Context>()
      val jsonStream: InputStream = context.assets.open("networkresponses/$path")
      String(jsonStream.readBytes())
    } catch (exception: IOException) {
      throw exception
    }
  }
}