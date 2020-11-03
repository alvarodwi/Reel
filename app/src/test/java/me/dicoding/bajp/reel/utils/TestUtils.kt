package me.dicoding.bajp.reel.utils

import okio.buffer
import okio.source
import java.nio.charset.StandardCharsets

object TestUtils {
    fun parseStringFromJsonResource(fileName : String) : String{
        val inputStream = javaClass.classLoader!!.getResourceAsStream("api-response/$fileName")
        val source = inputStream.source().buffer()
        return source.readString(StandardCharsets.UTF_8)
    }
}