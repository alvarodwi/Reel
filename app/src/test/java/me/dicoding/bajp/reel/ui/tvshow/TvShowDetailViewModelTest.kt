package me.dicoding.bajp.reel.ui.tvshow

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.unmockkAll
import io.mockk.verify
import junit.framework.TestCase
import kotlinx.coroutines.flow.flow
import me.dicoding.bajp.reel.data.model.entity.TvShowEntity
import me.dicoding.bajp.reel.data.model.json.asEntity
import me.dicoding.bajp.reel.data.network.NetworkResult
import me.dicoding.bajp.reel.data.repository.TvShowRepository
import me.dicoding.bajp.reel.ui.tvshow.detail.TvShowDetailViewModel
import me.dicoding.bajp.reel.utils.JsonHelper
import me.dicoding.bajp.reel.utils.TestUtils
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
    lateinit var viewModel: TvShowDetailViewModel

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        viewModel = TvShowDetailViewModel(expectedTvShowId,repository)
    }

    @Test
    fun `test successful fetch of list tvShow`() {
        every { repository.getTvShowDetailData(expectedTvShowId) } returns flow { NetworkResult.Success(provideDummyData()) }
        viewModel.fetchTvShowDetail()

        verify(atLeast = 1) { repository.getTvShowDetailData(expectedTvShowId) }

        viewModel.tvShow.observeForever {value ->
            assertNotNull(value)
            assertEquals(value.id, 528085L)
            assertEquals(value.name, "Cobra Kai")
            assertEquals(viewModel.errorMessage.value,"")
        }
    }

    @Test
    fun `test failed fetch of list tvShow`() {
        every { repository.getTvShowDetailData(expectedTvShowId) } returns flow { NetworkResult.Error(Exception("foo")) }
        viewModel.fetchTvShowDetail()

        verify(atLeast = 1) { repository.getTvShowDetailData(expectedTvShowId) }

        viewModel.tvShow.observeForever {value ->
            assertNull(value)
            assertEquals(viewModel.errorMessage.value,"foo")
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