package me.dicoding.bajp.reel.ui.movie.list

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import coil.ImageLoader
import com.jintin.bindingextension.BindingFragment
import me.dicoding.bajp.reel.databinding.FragmentSimpleListBinding
import me.dicoding.bajp.reel.ui.home.HomeFragmentDirections
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

class MovieListFragment : BindingFragment<FragmentSimpleListBinding>() {
    private val viewModel by viewModel<MovieListViewModel>()
    private val coilLoader by inject<ImageLoader>()

    private val recyclerView get() = binding.list.rvList
    private val swipeRefresh get() = binding.list.srlList
    private val cardError get() = binding.info.cardContainer
    private val errorText get() = binding.info.txtDescription

    private var rvAdapter: MovieAdapter? = null

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.fetchPopularMovie()

        setupList()
        viewModel.movies.observe(viewLifecycleOwner) { data ->
            rvAdapter?.submitList(data)
        }

        viewModel.errorMessage.observe(viewLifecycleOwner) { message ->
            cardError.isVisible = message.isNotBlank()
            errorText.text = message
            recyclerView.isVisible = message.isBlank()
        }

        swipeRefresh.setOnRefreshListener { viewModel.fetchPopularMovie() }
        viewModel.loading.observe(viewLifecycleOwner) { swipeRefresh.isRefreshing = it }
    }

    private fun setupList() {
        rvAdapter = MovieAdapter(
            coilLoader
        ) { id ->
            navigateToMovieDetail(id)
        }
        recyclerView.adapter = rvAdapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun navigateToMovieDetail(movieId: Long) {
        findNavController().navigate(
            HomeFragmentDirections.actionHomeToMovieDetail(movieId)
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        rvAdapter = null
    }
}
