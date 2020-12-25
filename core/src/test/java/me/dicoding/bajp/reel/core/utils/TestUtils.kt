package me.dicoding.bajp.reel.core.utils

import okio.buffer
import okio.source
import java.nio.charset.StandardCharsets

object TestUtils {
  fun parseStringFromJsonResource(fileName: String): String {
    val inputStream = javaClass.classLoader!!.getResourceAsStream("json/$fileName")
    val source = inputStream.source().buffer()
    return source.readString(StandardCharsets.UTF_8)
  }
}