package me.dicoding.bajp.reel.favorite.ui

import android.os.Bundle
import android.view.MenuItem
import android.view.MenuItem.OnActionExpandListener
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import coil.ImageLoader
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.list.listItemsSingleChoice
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import me.dicoding.bajp.reel.core.utils.DatabaseConstants.FavoriteTable.Types
import me.dicoding.bajp.reel.ext.setOnQueryTextChangeListener
import me.dicoding.bajp.reel.ext.viewBinding
import me.dicoding.bajp.reel.favorite.R
import me.dicoding.bajp.reel.favorite.databinding.FragmentFavoriteBinding
import me.dicoding.bajp.reel.favorite.di.favoriteModule
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules

class FavoriteFragment : Fragment(R.layout.fragment_favorite) {
  private val binding by viewBinding { FragmentFavoriteBinding.bind(requireView()) }

  private val viewModel by viewModel<FavoriteViewModel>()
  private val coilLoader by inject<ImageLoader>()

  private val toolbar get() = binding.toolbar
  private val recyclerView get() = binding.rvList
  private val swipeRefresh get() = binding.srlList
  private val cardError get() = binding.cardContainer
  private val filterButton get() = binding.btnFilterToggle

  private lateinit var rvAdapter: FavoriteAdapter

  //load koin module, forcefully -_-
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    requireActivity().also {
      loadKoinModules(favoriteModule)
    }
  }

  override fun onViewCreated(
    view: View,
    savedInstanceState: Bundle?
  ) {
    super.onViewCreated(view, savedInstanceState)

    setupList()
    setupFilter()
    with(toolbar) {
      title = getString(R.string.title_favorite)
      navigationIcon = ContextCompat.getDrawable(context, R.drawable.ic_arrow_back)
      setNavigationOnClickListener { activity?.onBackPressed() }
      inflateMenu(R.menu.favorite)

      val searchItem = menu.findItem(R.id.action_search)
      val searchView = searchItem.actionView as SearchView
      searchView.queryHint = requireView().context.getString(R.string.text_search_items)
      searchItem.setOnActionExpandListener(object : OnActionExpandListener {
        override fun onMenuItemActionExpand(p0: MenuItem?): Boolean {
          return true
        }

        override fun onMenuItemActionCollapse(p0: MenuItem?): Boolean {
          viewModel.resetSearch()
          return true
        }
      })
      setOnQueryTextChangeListener(searchView, false) {
        if (it != null) viewModel.searchItems(it)
        true
      }

      setOnMenuItemClickListener {
        when (it.itemId) {
          R.id.action_sort -> {
            MaterialDialog(requireContext()).show {
              title(R.string.sort_items)
              listItemsSingleChoice(
                R.array.favorite_sort,
                initialSelection = viewModel.getSortCode()
              ) { dialog, index, _ ->
                viewModel.reOrderItems(index)
                dialog.dismiss()
              }
            }
          }
        }
        true
      }
    }

    viewModel.items.observe(viewLifecycleOwner) { data ->
      lifecycleScope.launch { rvAdapter.submitData(data) }
    }

    //https://developer.android.com/topic/libraries/architecture/paging/v3-paged-data#kotlin
    swipeRefresh.setOnRefreshListener { rvAdapter.refresh() }
    lifecycleScope.launch {
      rvAdapter.loadStateFlow.collectLatest { state ->
        swipeRefresh.isRefreshing = state.refresh is LoadState.Loading
        cardError.isVisible = state.refresh is LoadState.Error
      }
    }

    viewModel.fetchFavoriteItems()
  }

  private fun setupList() {
    rvAdapter = FavoriteAdapter(
      coilLoader
    ) { id, type ->
      navigateToDetail(id, type)
    }

    recyclerView.adapter = rvAdapter
    recyclerView.layoutManager = LinearLayoutManager(requireContext())
  }

  private fun setupFilter() {
    filterButton.addOnButtonCheckedListener { _, checkedId, _ ->
      val param = when (checkedId) {
        R.id.btn_toggle_movie -> Types.TYPE_MOVIE
        R.id.btn_toggle_tv_show -> Types.TYPE_TV_SHOW
        else -> Types.TYPE_ALL
      }
      viewModel.updateFilter(param)
    }
  }

  private fun navigateToDetail(
    id: Long,
    type: Int
  ) {
    when (type) {
      Types.TYPE_MOVIE -> navigateToMovieDetail(id)
      Types.TYPE_TV_SHOW -> navigateToTvShowDetail(id)
      else -> throw IllegalArgumentException("Unknown type")
    }
  }

  private fun navigateToMovieDetail(id: Long) {
    findNavController().navigate(
      FavoriteFragmentDirections.actionFavoriteToMovieDetail(id)
    )
  }

  private fun navigateToTvShowDetail(id: Long) {
    findNavController().navigate(
      FavoriteFragmentDirections.actionFavoriteToTvShowDetail(id)
    )
  }
}