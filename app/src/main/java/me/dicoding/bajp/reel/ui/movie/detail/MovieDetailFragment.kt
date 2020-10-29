package me.dicoding.bajp.reel.ui.movie.detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import coil.ImageLoader
import coil.request.ImageRequest
import me.dicoding.bajp.reel.R
import me.dicoding.bajp.reel.data.model.entity.MovieEntity
import me.dicoding.bajp.reel.databinding.FragmentMovieDetailBinding
import me.dicoding.bajp.reel.utils.ext.toSafeUrl
import me.dicoding.bajp.reel.utils.ext.viewBinding
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class MovieDetailFragment : Fragment(R.layout.fragment_movie_detail) {
    private val binding by viewBinding { FragmentMovieDetailBinding.bind(requireView()) }

    private val toolbar get() = binding.toolbar
    private val cardError get() = binding.infoContainer
    private val errorText get() = binding.info.txtDescription

    private val movieId by lazy {
        val args = MovieDetailFragmentArgs.fromBundle(requireArguments())
        args.movieId
    }

    private val viewModel by viewModel<MovieDetailViewModel> { parametersOf(movieId) }
    private val imageLoader by inject<ImageLoader>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(toolbar) {
            navigationIcon = ContextCompat.getDrawable(context, R.drawable.ic_arrow_back)
            inflateMenu(R.menu.detail)

            setNavigationOnClickListener { activity?.onBackPressed() }
        }

        viewModel.movie.observe(viewLifecycleOwner) { data ->
            setupView(data)
        }

        viewModel.errorMessage.observe(viewLifecycleOwner) { message ->
            cardError.isVisible = message.isNotBlank()
            errorText.text = message
        }
    }

    private fun setupView(data: MovieEntity) {
        binding.toolbar.title = String.format("#%d", data.id)
        binding.toolbar.setOnMenuItemClickListener {menu ->
            when(menu.itemId){
                R.id.action_open_link -> openLink(data.tmdbUrl)
                R.id.action_share -> shareLink(data.tmdbUrl)
            }
            true
        }
        binding.title.text = data.title
        with(binding.tagLine){
            isVisible = data.tagLine.isNotBlank()
            text = data.tagLine
        }
        binding.overview.text = data.overview
        binding.releaseDate.text = data.releaseDate
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

    private fun openLink(url : String?){
        Intent(Intent.ACTION_VIEW).apply {
            data = Uri.parse(url.toSafeUrl())
        }.also {
            startActivity(it)
        }
    }

    private fun shareLink(url : String){
        Intent(Intent.ACTION_SEND).apply {
            putExtra(Intent.EXTRA_TEXT,url.toSafeUrl())
            type = "text/plain"
        }.also {
            startActivity(it)
        }
    }
}