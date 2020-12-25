package me.dicoding.bajp.reel.movie

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
import me.dicoding.bajp.reel.core.domain.model.Movie
import me.dicoding.bajp.reel.core.domain.repository.MovieRepository
import me.dicoding.bajp.reel.core.utils.TestFixtureHelper
import me.dicoding.bajp.reel.core.utils.TestFixtureHelper.parseStringFromJsonResource
import me.dicoding.bajp.reel.core.utils.asDomain
import me.dicoding.bajp.reel.ui.movie.detail.MovieDetailViewModel
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class MovieDetailViewModelTest : TestCase() {
  private val expectedMovieId = 1L

  @get:Rule
  var instantExecutorRule = InstantTaskExecutorRule()

  @MockK
  lateinit var repository: MovieRepository
  private lateinit var viewModel: MovieDetailViewModel

  @Before
  fun setup() {
    MockKAnnotations.init(this)
    viewModel = MovieDetailViewModel(expectedMovieId, repository)
  }

  @Test
  fun `test successful fetch of movie detail`() {
    every { repository.getMovieDetailData(expectedMovieId) } returns flow {
      NetworkResult.Success(
        provideDummyData()
      )
    }
    viewModel.fetchMovieDetail()

    verify(atLeast = 1) { repository.getMovieDetailData(expectedMovieId) }
    confirmVerified(repository)

    viewModel.movie.observeForever { value ->
      assertNotNull(value)
      assertEquals(value.id, 528085L)
      assertEquals(value.title, "2067")
      assertEquals(viewModel.errorMessage.value, "")
    }
  }

  @Test
  fun `test failed fetch of movie detail`() {
    every { repository.getMovieDetailData(expectedMovieId) } returns flow {
      NetworkResult.Error(
        Exception("foo")
      )
    }
    viewModel.fetchMovieDetail()

    verify(atLeast = 1) { repository.getMovieDetailData(expectedMovieId) }
    confirmVerified(repository)

    viewModel.movie.observeForever { value ->
      assertNull(value)
      assertEquals(viewModel.errorMessage.value, "foo")
    }
  }

  @Test
  fun `test check data assumed already in db`() {
    every { repository.isMovieInFavorites(expectedMovieId) } returns flow {
      emit(1) // room query returns 1 when data exists in db
    }
    viewModel.checkMovieInDb()

    verify(atLeast = 1) { repository.isMovieInFavorites(expectedMovieId) }
    confirmVerified(repository)

    viewModel.isFavorite.observeForever { value ->
      assertNotNull(value)
      assertEquals(value, true)
    }
  }

  @Test
  fun `test check data assumed not already in db`() {
    every { repository.isMovieInFavorites(expectedMovieId) } returns flow {
      emit(0) // room query returns 0 when data didn't exists in db
    }
    viewModel.checkMovieInDb()

    verify(atLeast = 1) { repository.isMovieInFavorites(expectedMovieId) }
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

  private fun provideDummyData(): Movie {
    return TestFixtureHelper.loadMovieData(
      parseStringFromJsonResource("/latest_movie.json")
    ).asDomain()
  }
}