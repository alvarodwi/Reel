package me.dicoding.bajp.reel.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import me.dicoding.bajp.reel.R
import me.dicoding.bajp.reel.databinding.FragmentHomeBinding
import me.dicoding.bajp.reel.utils.ext.viewBinding

class HomeFragment : Fragment(R.layout.fragment_home) {
    private val binding by viewBinding(FragmentHomeBinding::bind)

    private val toolbar get() =  binding.toolbar
    private val tabLayout get() = binding.tabLayout
    private val viewPager get() = binding.viewPager

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(toolbar){
            title = getString(R.string.app_name)
        }
        viewPager.adapter = HomePagerAdapter(requireActivity())
    }
}