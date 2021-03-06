package me.dicoding.bajp.reel.favorite

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import junit.framework.TestCase
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import me.dicoding.bajp.reel.core.data.db.FavoriteQuery
import me.dicoding.bajp.reel.core.data.network.json.FavoriteJson
import me.dicoding.bajp.reel.core.domain.model.Favorite
import me.dicoding.bajp.reel.core.domain.usecase.FavoriteListUseCase
import me.dicoding.bajp.reel.core.utils.DatabaseConstants.FavoriteTable.Sorts
import me.dicoding.bajp.reel.core.utils.DatabaseConstants.FavoriteTable.Types
import me.dicoding.bajp.reel.core.utils.TestFixtureHelper
import me.dicoding.bajp.reel.core.utils.TestFixtureHelper.parseStringFromJsonResource
import me.dicoding.bajp.reel.core.utils.asDomain
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
    lateinit var useCase: FavoriteListUseCase
    private lateinit var viewModel: me.dicoding.bajp.reel.favorite.ui.FavoriteViewModel

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        viewModel = me.dicoding.bajp.reel.favorite.ui.FavoriteViewModel(useCase)
    }

    @Test
    fun `test fetch favorite items`() {
        coEvery { useCase.checkFavoriteItems(query) } returns flowOf(true)
        every { useCase.getFavoriteItems(query, viewModel.viewModelScope) } returns flow {
            PagingData.from(provideDummyData())
        }
        viewModel.fetchFavoriteItems()

        coVerify(atLeast = 1) { useCase.checkFavoriteItems(query) }
        verify(atLeast = 1) { useCase.getFavoriteItems(query, viewModel.viewModelScope) }
        confirmVerified(useCase)

        viewModel.items.observeForever { result ->
            assertNotNull(result)
        }
    }

    private fun provideDummyData(): List<Favorite> =
        TestFixtureHelper.loadFavoritesData(parseStringFromJsonResource("/favorites.json"))
            .map(FavoriteJson::asDomain)
}
