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
import me.dicoding.bajp.reel.data.model.json.asEntity
import me.dicoding.bajp.reel.data.network.NetworkResult
import me.dicoding.bajp.reel.data.repository.MovieRepository
import me.dicoding.bajp.reel.ui.movie.detail.MovieDetailViewModel
import me.dicoding.bajp.reel.utils.JsonHelper
import me.dicoding.bajp.reel.utils.TestUtils
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
    lateinit var viewModel: MovieDetailViewModel

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        viewModel = MovieDetailViewModel(expectedMovieId,repository)
    }

    @Test
    fun `test successful fetch of list movie`() {
        every { repository.getMovieDetailData(expectedMovieId) } returns flow { NetworkResult.Success(provideDummyData()) }
        viewModel.fetchMovieDetail()

        verify(atLeast = 1) { repository.getMovieDetailData(expectedMovieId) }

        viewModel.movie.observeForever {value ->
            assertNotNull(value)
            assertEquals(value.id, 528085L)
            assertEquals(value.title, "2067")
            assertEquals(viewModel.errorMessage.value,"")
        }
    }

    @Test
    fun `test failed fetch of list movie`() {
        every { repository.getMovieDetailData(expectedMovieId) } returns flow { NetworkResult.Error(Exception("foo")) }
        viewModel.fetchMovieDetail()

        verify(atLeast = 1) { repository.getMovieDetailData(expectedMovieId) }

        viewModel.movie.observeForever {value ->
            assertNull(value)
            assertEquals(viewModel.errorMessage.value,"foo")
        }
    }

    @After
    fun tearUp() {
        unmockkAll()
    }

    private fun provideDummyData(): MovieEntity {
        return JsonHelper.loadMovieData(
            TestUtils.parseStringFromJsonResource("/latest_movie.json")
        ).asEntity()
    }
}