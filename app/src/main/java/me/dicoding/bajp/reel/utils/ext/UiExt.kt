package me.dicoding.bajp.reel.utils.ext

import android.content.Context
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment

// night mode helper
fun toggleNightMode(value: Boolean) {
  AppCompatDelegate.setDefaultNightMode(
    if (value) AppCompatDelegate.MODE_NIGHT_YES
    else AppCompatDelegate.MODE_NIGHT_NO
  )
}

//search view on fragment
fun Fragment.setOnQueryTextChangeListener(
  searchView: SearchView,
  onlyOnSubmit: Boolean = false,
  f: (text: String?) -> Boolean
) {
  searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
    override fun onQueryTextChange(newText: String?): Boolean {
      if (!onlyOnSubmit) {
        return f(newText)
      }
      return false
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
      val imm =
        activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
          ?: return f(query)
      imm.hideSoftInputFromWindow(searchView.windowToken, 0)
      return f(query)
    }
  })
}