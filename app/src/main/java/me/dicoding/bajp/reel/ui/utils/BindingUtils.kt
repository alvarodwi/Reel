package me.dicoding.bajp.reel.ui.utils

import android.os.Handler
import android.os.Looper
import android.util.ArrayMap
import android.view.View
import androidx.viewbinding.ViewBinding
import java.lang.reflect.Method
import timber.log.Timber

// from
internal object MainHandler {
    private val handler = Handler(Looper.getMainLooper())

    internal fun post(action: () -> Unit): Boolean = handler.post(action)
}

@PublishedApi
internal fun ensureMainThread() = check(Looper.getMainLooper() == Looper.myLooper()) {
    "Expected to be called on the main thread but was " + Thread.currentThread().name
}

internal object GetBindMethod {
    init {
        ensureMainThread()
    }

    private val methodSignature = View::class.java
    private val methodMap = ArrayMap<Class<out ViewBinding>, Method>()

    internal operator fun <T : ViewBinding> invoke(clazz: Class<T>): Method =
        methodMap
            .getOrPut(clazz) { clazz.getMethod("bind", methodSignature) }
            .also { Timber.d("methodMap.size: ${methodMap.size}") }
}
