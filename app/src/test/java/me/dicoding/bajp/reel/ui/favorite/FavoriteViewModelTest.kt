package me.dicoding.bajp.reel.ui.favorite

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import io.mockk.MockKAnnotations
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import junit.framework.TestCase
import kotlinx.coroutines.flow.flow
import me.dicoding.bajp.reel.data.model.entity.FavoriteEntity
import me.dicoding.bajp.reel.data.model.json.FavoriteJson
import me.dicoding.bajp.reel.data.model.json.asEntity
import me.dicoding.bajp.reel.data.model.query.FavoriteQuery
import me.dicoding.bajp.reel.data.repository.FavoriteRepository
import me.dicoding.bajp.reel.utils.DatabaseConstants.FavoriteTable.Sorts
import me.dicoding.bajp.reel.utils.DatabaseConstants.FavoriteTable.Types
import me.dicoding.bajp.reel.utils.JsonHelper
import me.dicoding.bajp.reel.utils.TestUtils
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class FavoriteViewModelTest : TestCase() {
  private val query: FavoriteQuery = FavoriteQuery(
    type = Types.TYPE_ALL,
    sort = Sorts.TITLE_ASC,
    searchQuery = ""
  )

  @get:Rule
  var instantExecutorRule = InstantTaskExecutorRule()

  @MockK
  lateinit var repository: FavoriteRepository
  private lateinit var viewModel: FavoriteViewModel

  @Before
  fun setup() {
    MockKAnnotations.init(this)
    viewModel = FavoriteViewModel(repository)
  }

  @Test
  fun `test fetch favorite items`() {
    every { repository.getFavoriteItems(query, viewModel.viewModelScope) } returns flow {
      PagingData.from(provideDummyData())
    }
    viewModel.fetchFavoriteItems()

    verify(atLeast = 1) { repository.getFavoriteItems(query, viewModel.viewModelScope) }
    confirmVerified(repository)

    viewModel.items.observeForever { result ->
      assertNotNull(result)
    }
  }

  private fun provideDummyData(): List<FavoriteEntity> =
    JsonHelper.loadFavoritesData(TestUtils.parseStringFromJsonResource("/favorites.json"))
      .map(FavoriteJson::asEntity)
}