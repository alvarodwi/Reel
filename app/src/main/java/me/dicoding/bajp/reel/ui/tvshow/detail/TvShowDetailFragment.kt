package me.dicoding.bajp.reel.ui.tvshow.detail

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import coil.ImageLoader
import coil.request.ImageRequest
import me.dicoding.bajp.reel.R
import me.dicoding.bajp.reel.data.model.entity.TvShowEntity
import me.dicoding.bajp.reel.databinding.FragmentTvShowDetailBinding
import me.dicoding.bajp.reel.ui.tvshow.detail.TvShowDetailFragmentArgs
import me.dicoding.bajp.reel.ui.tvshow.detail.TvShowDetailViewModel
import me.dicoding.bajp.reel.utils.ext.viewBinding
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class TvShowDetailFragment : Fragment(R.layout.fragment_tv_show_detail){
    private val binding by viewBinding { FragmentTvShowDetailBinding.bind(requireView()) }

    private val toolbar get() = binding.toolbar
    private val cardError get() = binding.infoContainer
    private val errorText get() = binding.info.txtDescription

    private val tvShowId by lazy {
        val args = TvShowDetailFragmentArgs.fromBundle(requireArguments())
        args.tvShowId
    }

    private val viewModel by viewModel<TvShowDetailViewModel> { parametersOf(tvShowId) }
    private val imageLoader by inject<ImageLoader>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(toolbar) {
            navigationIcon = ContextCompat.getDrawable(context, R.drawable.ic_arrow_back)
            inflateMenu(R.menu.detail)

            setNavigationOnClickListener { activity?.onBackPressed() }
        }

        viewModel.tvShow.observe(viewLifecycleOwner) { data ->
            setupView(data)
        }

        viewModel.errorMessage.observe(viewLifecycleOwner) { message ->
            cardError.isVisible = message.isNotBlank()
            errorText.text = message
        }
    }

    private fun setupView(data: TvShowEntity) {
        binding.toolbar.title = String.format("#%d", data.id)
        binding.name.text = data.name
        binding.overview.text = data.overview
        binding.firstAirDate.text = data.firstAirDate
        binding.genres.text = data.genres.joinToString(", ")
        val posterData = ImageRequest.Builder(binding.poster.context)
            .data(data.posterUrl)
            .placeholder(R.drawable.ic_loading)
            .error(R.drawable.ic_error)
            .target(binding.poster)
            .build()
        val backdropData = ImageRequest.Builder(binding.backdrop.context)
            .data(data.backdropUrl)
            .placeholder(R.drawable.ic_loading)
            .error(R.drawable.ic_error)
            .target(binding.backdrop)
            .build()
        imageLoader.enqueue(posterData)
        imageLoader.enqueue(backdropData)
    }
}