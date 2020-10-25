package me.dicoding.bajp.reel.ui.movie.list

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import coil.ImageLoader
import me.dicoding.bajp.reel.R
import me.dicoding.bajp.reel.databinding.FragmentSimpleListBinding
import me.dicoding.bajp.reel.ui.home.HomeFragmentDirections
import me.dicoding.bajp.reel.utils.ext.viewBinding
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

class MovieListFragment : Fragment(R.layout.fragment_simple_list) {
    private val binding by viewBinding(FragmentSimpleListBinding::bind)

    private val viewModel by viewModel<MovieListViewModel>()
    private val coilLoader by inject<ImageLoader>()

    private val recyclerView get() = binding.list.rvList
    private val swipeRefresh get() = binding.list.srlList
    private val cardError get() = binding.info.cardContainer
    private val errorText get() = binding.info.txtDescription

    private lateinit var rvAdapter: MovieAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupList()
        viewModel.movies.observe(viewLifecycleOwner) { data ->
            rvAdapter.submitList(data)
        }

        viewModel.errorMessage.observe(viewLifecycleOwner) { message ->
            cardError.isVisible = message.isNotBlank()
            errorText.text = message
        }
    }

    private fun setupList() {
        rvAdapter = MovieAdapter(
            coilLoader
        ) { id ->
            navigateToMovieDetail(id)
        }
        recyclerView.adapter = rvAdapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        swipeRefresh.isEnabled = false
    }

    private fun navigateToMovieDetail(movieId: Long) {
        findNavController().navigate(
            HomeFragmentDirections.actionHomeToMovieDetail(movieId)
        )
    }
}