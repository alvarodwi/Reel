package me.dicoding.bajp.reel.ui.favorite

import androidx.fragment.app.Fragment
import me.dicoding.bajp.reel.R
import me.dicoding.bajp.reel.databinding.FragmentFavoriteBinding
import me.dicoding.bajp.reel.utils.ext.viewBinding

class FavoriteFragment : Fragment(R.layout.fragment_favorite){
    private val binding by viewBinding { FragmentFavoriteBinding.bind(requireView()) }

}