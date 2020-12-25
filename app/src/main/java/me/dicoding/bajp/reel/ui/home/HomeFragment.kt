package me.dicoding.bajp.reel.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import me.dicoding.bajp.reel.R
import me.dicoding.bajp.reel.databinding.FragmentHomeBinding
import me.dicoding.bajp.reel.ext.viewBinding

class HomeFragment : Fragment(R.layout.fragment_home) {
  private val binding by viewBinding { FragmentHomeBinding.bind(requireView()) }

  private val toolbar get() = binding.toolbar
  private val tabLayout get() = binding.tabLayout
  private val viewPager get() = binding.viewPager

  override fun onViewCreated(
    view: View,
    savedInstanceState: Bundle?
  ) {
    super.onViewCreated(view, savedInstanceState)

    with(toolbar) {
      title = getString(R.string.app_name)
      inflateMenu(R.menu.main)

      setOnMenuItemClickListener {
        when (it.itemId) {
          R.id.action_settings -> {
            navigateToSettings()
          }
          R.id.action_favorite -> {
            navigateToFavorite()
          }
        }
        true
      }
    }
    viewPager.adapter = HomePagerAdapter(requireActivity())
    val titles = arrayOf("Movies", "Tv Shows")
    TabLayoutMediator(tabLayout, viewPager) { tab: TabLayout.Tab, position: Int ->
      tab.text = titles[position]
    }.attach()
  }

  private fun navigateToSettings() {
    findNavController().navigate(
      HomeFragmentDirections.actionHomeToSettings()
    )
  }

  private fun navigateToFavorite() {
    findNavController().navigate(
      HomeFragmentDirections.actionHomeToFavorite()
    )
  }
}