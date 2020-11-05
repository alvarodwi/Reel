package me.dicoding.bajp.reel.data.network

import junit.framework.TestCase
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class NetworkResultTest : TestCase() {
    @Test
    fun `network result containing error`() {
        val exception = Exception("foo")
        val networkResult = NetworkResult.Error(exception)
        assertEquals(networkResult.exception.message, "foo")
    }

    @Test
    fun `network result containing data`() {
        val data = "foo"
        val networkResult = NetworkResult.Success(data)
        assertEquals(networkResult.data, "foo")
    }
}