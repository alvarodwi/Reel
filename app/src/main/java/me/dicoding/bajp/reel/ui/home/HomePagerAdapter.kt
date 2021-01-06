package me.dicoding.bajp.reel.ui.home

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import me.dicoding.bajp.reel.ui.movie.list.MovieListFragment
import me.dicoding.bajp.reel.ui.tvshow.list.TvShowListFragment

class HomePagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> MovieListFragment()
            else -> TvShowListFragment()
        }
    }
}
