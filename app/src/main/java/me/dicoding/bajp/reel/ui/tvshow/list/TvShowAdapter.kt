package me.dicoding.bajp.reel.ui.tvshow.list

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.ImageLoader
import coil.request.ImageRequest
import me.dicoding.bajp.reel.R
import me.dicoding.bajp.reel.core.domain.model.TvShow
import me.dicoding.bajp.reel.databinding.ItemTvShowBinding
import me.dicoding.bajp.reel.ext.viewBinding

class TvShowAdapter(
  private val coilLoader: ImageLoader,
  private val clickCallback: (Long) -> Unit,
) : ListAdapter<TvShow, TvShowAdapter.TvShowViewHolder>(MOVIE_DIFF) {
  companion object {
    private val MOVIE_DIFF = object : DiffUtil.ItemCallback<TvShow>() {
      override fun areItemsTheSame(
        oldItem: TvShow,
        newItem: TvShow
      ): Boolean {
        return oldItem.id == newItem.id
      }

      override fun areContentsTheSame(
        oldItem: TvShow,
        newItem: TvShow
      ): Boolean {
        return oldItem == newItem
      }
    }
  }

  inner class TvShowViewHolder(private val binding: ItemTvShowBinding) :
    RecyclerView.ViewHolder(binding.root) {
    constructor(parent: ViewGroup) : this(
      parent.viewBinding(ItemTvShowBinding::inflate)
    )

    fun bind(data: TvShow?) {
      if (data == null) return

      with(binding) {
        binding.root.setOnClickListener { clickCallback(data.id) }
        name.text = data.name
        firstAirDate.text = String.format("First aired at %s", data.firstAirDate)

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
  ): TvShowViewHolder =
    TvShowViewHolder(parent)

  override fun onBindViewHolder(
    holder: TvShowViewHolder,
    position: Int
  ) {
    val movie = getItem(position)
    holder.bind(movie)
  }
}