package me.dicoding.bajp.reel.utils.ext

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.viewbinding.ViewBinding
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

// view binding helper
fun <T> Fragment.viewBinding(initialise: () -> T): ReadOnlyProperty<Fragment, T> =
  object : ReadOnlyProperty<Fragment, T>, DefaultLifecycleObserver {
    private var binding: T? = null

    override fun onDestroy(owner: LifecycleOwner) {
      binding = null
    }

    override fun getValue(
      thisRef: Fragment,
      property: KProperty<*>
    ): T =
      binding
        ?: initialise().also {
          binding = it
          this@viewBinding.viewLifecycleOwner.lifecycle.addObserver(this)
        }
  }

inline fun <T : ViewBinding> AppCompatActivity.viewBinding(
  crossinline bindingInflater: (LayoutInflater) -> T
) =
  lazy(LazyThreadSafetyMode.NONE) {
    bindingInflater.invoke(layoutInflater)
  }

inline fun <T : ViewBinding> ViewGroup.viewBinding(
  crossinline bindingInflater: (LayoutInflater, ViewGroup, Boolean) -> T,
  attachToParent: Boolean = false
) = bindingInflater.invoke(LayoutInflater.from(context), this, attachToParent)

fun Activity.moveActivity(
  dest: Class<*>,
  args: Bundle? = null
) {
  Intent(this, dest).apply {
    if (args != null) this.putExtras(args)
  }.also { startActivity(it) }
}