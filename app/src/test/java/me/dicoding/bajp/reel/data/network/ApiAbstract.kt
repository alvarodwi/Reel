package me.dicoding.bajp.reel.data.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import junit.framework.TestCase
import me.dicoding.bajp.reel.utils.JsonHelper
import me.dicoding.bajp.reel.utils.TestUtils
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Retrofit
import java.io.IOException

@RunWith(JUnit4::class)
abstract class ApiAbstract<T> : TestCase() {
    lateinit var mockWebServer: MockWebServer

    @Throws(IOException::class)
    @Before
    fun mockServer() {
        mockWebServer = MockWebServer()
        mockWebServer.start()
    }

    @Throws(IOException::class)
    @After
    fun stopServer() {
        mockWebServer.shutdown()
    }

    @Throws(IOException::class)
    fun enqueueResponse(fileName: String) {
        enqueueResponse(fileName, emptyMap())
    }

    @Throws(IOException::class)
    private fun enqueueResponse(fileName: String, headers: Map<String, String>) {
        val mockResponse = MockResponse()
        for ((key, value) in headers) {
            mockResponse.addHeader(key, value)
        }
        mockWebServer.enqueue(mockResponse.setBody(TestUtils.parseStringFromJsonResource(fileName)))
    }

    fun createService(clazz: Class<T>): T {
        return Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(JsonHelper.jsonBuilder.asConverterFactory("application/json".toMediaType()))
            .build()
            .create(clazz)
    }
}