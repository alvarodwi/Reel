package me.dicoding.bajp.reel.favorite.ui

import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.ImageLoader
import coil.request.ImageRequest
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import me.dicoding.bajp.reel.R
import me.dicoding.bajp.reel.core.domain.model.Favorite
import me.dicoding.bajp.reel.core.utils.DatabaseConstants.FavoriteTable.Types
import me.dicoding.bajp.reel.ext.viewBinding
import me.dicoding.bajp.reel.favorite.databinding.ItemFavoriteBinding
import me.dicoding.bajp.reel.favorite.ui.FavoriteAdapter.FavoriteViewHolder

class FavoriteAdapter(
    private val coilLoader: ImageLoader,
    private val clickCallback: (Long, Int) -> Unit,
) : PagingDataAdapter<Favorite, FavoriteViewHolder>(FAVORITE_DIFF) {
    companion object {
        private val FAVORITE_DIFF = object : DiffUtil.ItemCallback<Favorite>() {
            override fun areItemsTheSame(
                oldItem: Favorite,
                newItem: Favorite
            ): Boolean = oldItem.uid == newItem.uid

            override fun areContentsTheSame(
                oldItem: Favorite,
                newItem: Favorite
            ): Boolean = oldItem == newItem
        }
    }

    inner class FavoriteViewHolder(private val binding: ItemFavoriteBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Favorite?) {
            if (data == null) return

            with(binding) {
                binding.root.setOnClickListener { clickCallback(data.tmdbId, data.type) }
                title.text = data.itemTitle
                releaseDate.text = String.format("Aired at %s", data.itemDate)
                when (data.type) {
                    Types.TYPE_MOVIE -> type.text = String.format("Movie")
                    Types.TYPE_TV_SHOW -> type.text = String.format("Tv Show")
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

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FavoriteViewHolder =
        FavoriteViewHolder(parent.viewBinding(ItemFavoriteBinding::inflate))

    override fun onBindViewHolder(
        holder: FavoriteViewHolder,
        position: Int
    ) {
        val item = getItem(position)
        holder.bind(item)
    }
}
