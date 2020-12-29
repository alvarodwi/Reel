package me.dicoding.bajp.reel.core.data.interactor

import kotlinx.coroutines.CoroutineScope
import me.dicoding.bajp.reel.core.data.db.FavoriteQuery
import me.dicoding.bajp.reel.core.domain.repository.FavoriteRepository
import me.dicoding.bajp.reel.core.domain.usecase.FavoriteListUseCase

class FavoriteListInteractor(private val repository: FavoriteRepository) : FavoriteListUseCase {
    override fun getFavoriteItems(
        query: FavoriteQuery,
        scope: CoroutineScope
    ) = repository.getFavoriteItems(query, scope)
}
