package me.dicoding.bajp.reel.ui.movie.list

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.ImageLoader
import coil.request.ImageRequest
import me.dicoding.bajp.reel.R
import me.dicoding.bajp.reel.data.model.entity.MovieEntity
import me.dicoding.bajp.reel.databinding.ItemMovieBinding
import me.dicoding.bajp.reel.utils.ext.viewBinding

class MovieAdapter(
  private val coilLoader: ImageLoader,
  private val clickCallback: (Long) -> Unit,
) : ListAdapter<MovieEntity, MovieAdapter.MovieViewHolder>(MOVIE_DIFF) {
  companion object {
    private val MOVIE_DIFF = object : DiffUtil.ItemCallback<MovieEntity>() {
      override fun areItemsTheSame(
        oldItem: MovieEntity,
        newItem: MovieEntity
      ): Boolean {
        return oldItem.id == newItem.id
      }

      override fun areContentsTheSame(
        oldItem: MovieEntity,
        newItem: MovieEntity
      ): Boolean {
        return oldItem == newItem
      }
    }
  }

  inner class MovieViewHolder(private val binding: ItemMovieBinding) :
    RecyclerView.ViewHolder(binding.root) {
    constructor(parent: ViewGroup) : this(
      parent.viewBinding(ItemMovieBinding::inflate)
    )

    fun bind(data: MovieEntity?) {
      if (data == null) return

      with(binding) {
        binding.root.setOnClickListener { clickCallback(data.id) }
        title.text = data.title
        releaseDate.text = String.format("Released at %s", data.releaseDate)

        val posterData = ImageRequest.Builder(itemView.context)
          .data(data.posterUrl)
          .placeholder(R.drawable.ic_loading)
          .error(R.drawable.ic_error)
          .target(poster)
          .allowHardware(true)
          .build()
        coilLoader.enqueue(posterData)
      }
    }
  }

  override fun onCreateViewHolder(
    parent: ViewGroup,
    viewType: Int
  ): MovieViewHolder =
    MovieViewHolder(parent)

  override fun onBindViewHolder(
    holder: MovieViewHolder,
    position: Int
  ) {
    val movie = getItem(position)
    holder.bind(movie)
  }
}