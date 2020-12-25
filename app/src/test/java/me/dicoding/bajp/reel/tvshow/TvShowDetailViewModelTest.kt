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
import me.dicoding.bajp.reel.core.data.model.entity.TvShowEntity
import me.dicoding.bajp.reel.core.data.network.NetworkResult
import me.dicoding.bajp.reel.core.data.repository.TvShowRepository
import me.dicoding.bajp.reel.ui.tvshow.detail.TvShowDetailViewModel
import me.dicoding.bajp.reel.core.utils.JsonHelper
import me.dicoding.bajp.reel.core.utils.TestUtils
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
  lateinit var repository: TvShowRepository
  private lateinit var viewModel: TvShowDetailViewModel

  @Before
  fun setup() {
    MockKAnnotations.init(this)
    viewModel = TvShowDetailViewModel(expectedTvShowId, repository)
  }

  @Test
  fun `test successful fetch of tvShow detail`() {
    every { repository.getTvShowDetailData(expectedTvShowId) } returns flow {
      NetworkResult.Success(
        provideDummyData()
      )
    }
    viewModel.fetchTvShowDetail()

    verify(atLeast = 1) { repository.getTvShowDetailData(expectedTvShowId) }
    confirmVerified(repository)

    viewModel.tvShow.observeForever { value ->
      assertNotNull(value)
      assertEquals(value.id, 528085L)
      assertEquals(value.name, "Cobra Kai")
      assertEquals(viewModel.errorMessage.value, "")
    }
  }

  @Test
  fun `test failed fetch of tvShow detail`() {
    every { repository.getTvShowDetailData(expectedTvShowId) } returns flow {
      NetworkResult.Error(
        Exception("foo")
      )
    }
    viewModel.fetchTvShowDetail()

    verify(atLeast = 1) { repository.getTvShowDetailData(expectedTvShowId) }
    confirmVerified(repository)

    viewModel.tvShow.observeForever { value ->
      assertNull(value)
      assertEquals(viewModel.errorMessage.value, "foo")
    }
  }

  @Test
  fun `test check data assumed already in db`() {
    every { repository.isTvShowInFavorites(expectedTvShowId) } returns flow {
      emit(1) // room query returns 1 when data exists in db
    }
    viewModel.checkTvShowInDb()

    verify(atLeast = 1) { repository.isTvShowInFavorites(expectedTvShowId) }
    confirmVerified(repository)

    viewModel.isFavorite.observeForever { value ->
      assertNotNull(value)
      assertEquals(value, true)
    }
  }

  @Test
  fun `test check data assumed not already in db`() {
    every { repository.isTvShowInFavorites(expectedTvShowId) } returns flow {
      emit(0) // room query returns 0 when data didn't exists in db
    }
    viewModel.checkTvShowInDb()

    verify(atLeast = 1) { repository.isTvShowInFavorites(expectedTvShowId) }
    confirmVerified(repository)

    viewModel.isFavorite.observeForever { value ->
      assertNotNull(value)
      assertEquals(value, false)
    }
  }

  @After
  fun tearUp() {
    unmockkAll()
  }

  private fun provideDummyData(): TvShowEntity {
    return JsonHelper.loadTvShowData(
      TestUtils.parseStringFromJsonResource("/latest_tvShow.json")
    ).asEntity()
  }
}