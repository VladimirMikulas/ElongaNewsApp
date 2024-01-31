package com.vlamik.nba

import app.cash.turbine.test
import com.vlamik.core.domain.GetPlayers
import com.vlamik.core.domain.models.Player
import com.vlamik.nba.features.list.PlayerListViewModel
import com.vlamik.nba.features.list.PlayerListViewModel.ListScreenUiState.ErrorFromAPI
import com.vlamik.nba.features.list.PlayerListViewModel.ListScreenUiState.LoadingFromAPI
import com.vlamik.nba.features.list.PlayerListViewModel.ListScreenUiState.UpdateSuccess
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers.instanceOf
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class ViewModelAndCoroutinesExampleUnitTest {

    @get:Rule
    val coroutinesTestRule = MockMainDispatcherTestRule()

    private val getPlayersMock = mockk<GetPlayers>()

    private fun buildVM(): PlayerListViewModel = PlayerListViewModel(
        getPlayersMock
    )

    @Test
    fun `Test Initial State`() = runTest(coroutinesTestRule.testDispatcher) {
        // Arrange
        val vm = buildVM()

        // Assert
        vm.state.test {
            assertThat(
                awaitItem(),
                instanceOf(LoadingFromAPI::class.java)
            )
        }
    }

    @Test
    fun `Test Refresh`() = runTest(coroutinesTestRule.testDispatcher) {
        // Arrange
        val result = Result.success(listOf(Player("1", "vlamik.com")))
        coEvery { getPlayersMock.invoke() } returns result

        val vm = buildVM()

        // Act
        vm.refresh()

        // Assert
        vm.state.test {
            assertEquals(
                awaitItem(),
                UpdateSuccess(result.getOrThrow())
            )
        }
    }

    @Test
    fun `Test Error State`() = runTest(coroutinesTestRule.testDispatcher) {
        // Arrange
        val result: Result<List<Player>> = Result.failure(Exception(""))
        coEvery { getPlayersMock.invoke() } returns result

        val vm = buildVM()

        // Act
        vm.refresh()

        // Assert
        vm.state.test {
            assertThat(
                awaitItem(),
                instanceOf(ErrorFromAPI::class.java)
            )
        }
    }
}
