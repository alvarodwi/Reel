package me.dicoding.bajp.reel.ui.favorite

import androidx.fragment.app.Fragment
import me.dicoding.bajp.reel.R
import me.dicoding.bajp.reel.databinding.FragmentFavoriteBinding
import me.dicoding.bajp.reel.utils.ext.viewBinding

class FavoriteFragment : Fragment(R.layout.fragment_favorite) {
  private val binding by viewBinding { FragmentFavoriteBinding.bind(requireView()) }

  private val toolbar get() = binding.toolbar
  private val recyclerView get() = binding.listLayout.list.rvList
  private val swipeRefresh get() = binding.listLayout.list.srlList
  private val cardError get() = binding.listLayout.info.cardContainer
  private val errorText get() = binding.listLayout.info.txtDescription
  private val filterButton get() = binding.filterLayout.btnFilterToggle
}