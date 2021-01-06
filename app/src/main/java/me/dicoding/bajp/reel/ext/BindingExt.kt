package me.dicoding.bajp.reel.ext

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.MainThread
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import me.dicoding.bajp.reel.ui.utils.FragmentViewBindingDelegate

@MainThread
fun <T : ViewBinding> Fragment.viewBinding(bind: (View) -> T): FragmentViewBindingDelegate<T> =
    FragmentViewBindingDelegate.from(
        fragment = this,
        viewBindingBind = bind
    )

@MainThread
inline fun <reified T : ViewBinding> Fragment.viewBinding(): FragmentViewBindingDelegate<T> =
    FragmentViewBindingDelegate.from(
        fragment = this,
        viewBindingClazz = T::class.java
    )

@MainThread
inline fun <T : ViewBinding> ViewGroup.viewBinding(
    viewBindingFactory: (LayoutInflater, ViewGroup, Boolean) -> T
) =
    viewBindingFactory.invoke(LayoutInflater.from(this.context), this, false)
