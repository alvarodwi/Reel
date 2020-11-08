package me.dicoding.bajp.reel.data.model.usecase

import me.dicoding.bajp.reel.utils.DatabaseConstants

data class FavoriteUseCase(
    val type : Int = DatabaseConstants.Type.TYPE_ALL,
    val sort : Int = DatabaseConstants.Sort.TITLE_ASC,
    val isGrouped : Boolean = false,
    val searchQuery : String = ""
)