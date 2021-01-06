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
import me.dicoding.bajp.reel.core.data.network.json.MovieJson
import me.dicoding.bajp.reel.core.domain.model.Movie
import me.dicoding.bajp.reel.core.domain.usecase.MovieListUseCase
import me.dicoding.bajp.reel.core.utils.TestFixtureHelper
import me.dicoding.bajp.reel.core.utils.TestFixtureHelper.parseStringFromJsonResource
import me.dicoding.bajp.reel.core.utils.asDomain
import me.dicoding.bajp.reel.ui.movie.list.MovieListViewModel
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class MovieListViewModelTest : TestCase() {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @MockK
    lateinit var useCase: MovieListUseCase
    private lateinit var viewModel: MovieListViewModel

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        viewModel = MovieListViewModel(useCase)
    }

    @Test
    fun `test successful fetch of list movie`() {
        every { useCase.getPopularMovie() } returns flow {
            NetworkResult.Success(
                provideDummyData()
            )
        }
        viewModel.fetchPopularMovie()

        verify(atLeast = 1) { useCase.getPopularMovie() }
        confirmVerified(useCase)

        viewModel.movies.observeForever { value ->
            assertNotNull(value)
            assertEquals(value.size, 20)
            assertEquals(viewModel.errorMessage.value, "")
        }
    }

    @Test
    fun `test failed fetch of list movie`() {
        every { useCase.getPopularMovie() } returns flow { NetworkResult.Error(Exception("foo")) }
        viewModel.fetchPopularMovie()

        verify(atLeast = 1) { useCase.getPopularMovie() }
        confirmVerified(useCase)

        viewModel.movies.observeForever { value ->
            assert(value.isEmpty())
            assertEquals(viewModel.errorMessage.value, "foo")
        }
    }

    @After
    fun tearUp() {
        unmockkAll()
    }

    private fun provideDummyData(): List<Movie> {
        return TestFixtureHelper.loadPopularMovieData(
            parseStringFromJsonResource("/popular_movies.json")
        ).results.map(MovieJson::asDomain)
    }
}
