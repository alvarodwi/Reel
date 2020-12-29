package me.dicoding.bajp.reel.core.utils

import androidx.test.espresso.idling.CountingIdlingResource

object EspressoIdlingResource {
    private const val RESOURCE_NAME = "GLOBAL"
    val idleResource = CountingIdlingResource(RESOURCE_NAME)

    fun increment() {
        idleResource.increment()
    }

    fun decrement() {
        idleResource.decrement()
    }
}
