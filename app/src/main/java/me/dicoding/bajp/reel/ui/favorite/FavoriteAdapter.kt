package me.dicoding.bajp.reel.ui.favorite

import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.ImageLoader
import coil.request.ImageRequest
import me.dicoding.bajp.reel.R
import me.dicoding.bajp.reel.data.model.entity.FavoriteEntity
import me.dicoding.bajp.reel.databinding.ItemFavoriteBinding
import me.dicoding.bajp.reel.ui.favorite.FavoriteAdapter.FavoriteViewHolder
import me.dicoding.bajp.reel.utils.DatabaseConstants.FavoriteTable.Types
import me.dicoding.bajp.reel.utils.ext.viewBinding
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class FavoriteAdapter(
  private val coilLoader: ImageLoader,
  private val clickCallback: (Long, Int) -> Unit,
) : PagingDataAdapter<FavoriteEntity, FavoriteViewHolder>(FAVORITE_DIFF) {
  companion object {
    private val FAVORITE_DIFF = object : DiffUtil.ItemCallback<FavoriteEntity>() {
      override fun areItemsTheSame(
        oldItem: FavoriteEntity,
        newItem: FavoriteEntity
      ): Boolean {
        return oldItem.uid == newItem.uid
      }

      override fun areContentsTheSame(
        oldItem: FavoriteEntity,
        newItem: FavoriteEntity
      ): Boolean {
        return oldItem == newItem
      }
    }
  }

  inner class FavoriteViewHolder(private val binding: ItemFavoriteBinding) :
    RecyclerView.ViewHolder(binding.root) {
    constructor(parent: ViewGroup) : this(
      parent.viewBinding(ItemFavoriteBinding::inflate)
    )

    fun bind(data: FavoriteEntity?) {
      if (data == null) return

      with(binding) {
        binding.root.setOnClickListener { clickCallback(data.tmdbId, data.type) }
        title.text = data.itemTitle
        releaseDate.text = String.format("Aired at %s", data.itemDate)
        when (data.type) {
          Types.TYPE_MOVIE -> type.text = "Movie"
          Types.TYPE_TV_SHOW -> type.text = "Tv Show"
          else -> type.isVisible = false
        }
        val addedDateTime =
          LocalDateTime.from(DateTimeFormatter.ISO_DATE_TIME.parse(data.dateAdded))
        dateAdded.text =
          String.format("Added on %s", addedDateTime.format(DateTimeFormatter.ISO_DATE))

        val posterData = ImageRequest.Builder(itemView.context)
          .data(data.itemPosterUrl)
          .placeholder(R.drawable.ic_loading)
          .error(R.drawable.ic_error)
          .target(poster)
          .allowHardware(true)
          .build()

        coilLoader.enqueue(posterData)
      }
    }
  }

  override fun onBindViewHolder(
    holder: FavoriteViewHolder,
    position: Int
  ) {
    val item = getItem(position)
    holder.bind(item)
  }

  override fun onCreateViewHolder(
    parent: ViewGroup,
    viewType: Int
  ): FavoriteViewHolder {
    return FavoriteViewHolder(parent)
  }
}