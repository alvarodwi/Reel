package me.dicoding.bajp.reel.ui.movie

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.unmockkAll
import io.mockk.verify
import junit.framework.TestCase
import kotlinx.coroutines.flow.flow
import me.dicoding.bajp.reel.data.model.entity.MovieEntity
import me.dicoding.bajp.reel.data.model.json.MovieJson
import me.dicoding.bajp.reel.data.model.json.asEntity
import me.dicoding.bajp.reel.data.network.NetworkResult
import me.dicoding.bajp.reel.data.repository.MovieRepository
import me.dicoding.bajp.reel.ui.movie.list.MovieListViewModel
import me.dicoding.bajp.reel.utils.JsonHelper
import me.dicoding.bajp.reel.utils.TestUtils
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
    lateinit var repository: MovieRepository
    lateinit var viewModel: MovieListViewModel

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        viewModel = MovieListViewModel(repository)
    }

    @Test
    fun `test successful fetch of list movie`() {
        every { repository.getPopularMovie() } returns flow { NetworkResult.Success(provideDummyData()) }
        viewModel.fetchPopularMovie()

        verify(atLeast = 1) { repository.getPopularMovie() }

        viewModel.movies.observeForever {value ->
            assertNotNull(value)
            assertEquals(value.size, 20)
            assertEquals(viewModel.errorMessage.value,"")
        }
    }

    @Test
    fun `test failed fetch of list movie`() {
        every { repository.getPopularMovie() } returns flow { NetworkResult.Error(Exception("foo")) }
        viewModel.fetchPopularMovie()

        verify(atLeast = 1) { repository.getPopularMovie() }

        viewModel.movies.observeForever {value ->
            assert(value.isEmpty())
            assertEquals(viewModel.errorMessage.value,"foo")
        }
    }

    @After
    fun tearUp() {
        unmockkAll()
    }

    private fun provideDummyData(): List<MovieEntity> {
        return JsonHelper.loadPopularMovieData(
            TestUtils.parseStringFromJsonResource("/popular_movies.json")
        ).results.map(MovieJson::asEntity)
    }
}