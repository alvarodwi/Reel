package me.dicoding.bajp.reel.tvshow

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.mockk.MockKAnnotations
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.unmockkAll
import io.mockk.verify
import junit.framework.TestCase
import kotlinx.coroutines.flow.flow
import me.dicoding.bajp.reel.core.data.network.NetworkResult
import me.dicoding.bajp.reel.core.domain.model.TvShow
import me.dicoding.bajp.reel.core.domain.usecase.TvShowDetailUseCase
import me.dicoding.bajp.reel.core.utils.TestFixtureHelper
import me.dicoding.bajp.reel.core.utils.TestFixtureHelper.parseStringFromJsonResource
import me.dicoding.bajp.reel.core.utils.asDomain
import me.dicoding.bajp.reel.ui.tvshow.detail.TvShowDetailViewModel
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class TvShowDetailViewModelTest : TestCase() {
    private val expectedTvShowId = 1L

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @MockK
    lateinit var useCase: TvShowDetailUseCase
    private lateinit var viewModel: TvShowDetailViewModel

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        viewModel = TvShowDetailViewModel(expectedTvShowId, useCase)
    }

    @Test
    fun `test successful fetch of tvShow detail`() {
        every { useCase.getTvShowDetailData(expectedTvShowId) } returns flow {
            NetworkResult.Success(
                provideDummyData()
            )
        }
        viewModel.fetchTvShowDetail()

        verify(atLeast = 1) { useCase.getTvShowDetailData(expectedTvShowId) }
        confirmVerified(useCase)

        viewModel.tvShow.observeForever { value ->
            assertNotNull(value)
            assertEquals(value.id, 528085L)
            assertEquals(value.name, "Cobra Kai")
            assertEquals(viewModel.errorMessage.value, "")
        }
    }

    @Test
    fun `test failed fetch of tvShow detail`() {
        every { useCase.getTvShowDetailData(expectedTvShowId) } returns flow {
            NetworkResult.Error(
                Exception("foo")
            )
        }
        viewModel.fetchTvShowDetail()

        verify(atLeast = 1) { useCase.getTvShowDetailData(expectedTvShowId) }
        confirmVerified(useCase)

        viewModel.tvShow.observeForever { value ->
            assertNull(value)
            assertEquals(viewModel.errorMessage.value, "foo")
        }
    }

    @Test
    fun `test check data assumed already in db`() {
        every { useCase.isTvShowInFavorites(expectedTvShowId) } returns flow {
            emit(1) // room query returns 1 when data exists in db
        }
        viewModel.checkTvShowInDb()

        verify(atLeast = 1) { useCase.isTvShowInFavorites(expectedTvShowId) }
        confirmVerified(useCase)

        viewModel.isFavorite.observeForever { value ->
            assertNotNull(value)
            assertEquals(value, true)
        }
    }

    @Test
    fun `test check data assumed not already in db`() {
        every { useCase.isTvShowInFavorites(expectedTvShowId) } returns flow {
            emit(0) // room query returns 0 when data didn't exists in db
        }
        viewModel.checkTvShowInDb()

        verify(atLeast = 1) { useCase.isTvShowInFavorites(expectedTvShowId) }
        confirmVerified(useCase)

        viewModel.isFavorite.observeForever { value ->
            assertNotNull(value)
            assertEquals(value, false)
        }
    }

    @After
    fun tearUp() {
        unmockkAll()
    }

    private fun provideDummyData(): TvShow {
        return TestFixtureHelper.loadTvShowData(
            parseStringFromJsonResource("/latest_tvShow.json")
        ).asDomain()
    }
}
