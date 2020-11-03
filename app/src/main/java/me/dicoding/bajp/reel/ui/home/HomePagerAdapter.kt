package me.dicoding.bajp.reel.ui.home

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import me.dicoding.bajp.reel.ui.movie.list.MovieListFragment
import me.dicoding.bajp.reel.ui.tvshow.list.TvShowListFragment

class HomePagerAdapter(activity: FragmentActivity) :
    FragmentStateAdapter(activity) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> MovieListFragment()
            else -> TvShowListFragment()
        }
    }
}